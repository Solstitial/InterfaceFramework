package net.lightshard.interfaceframework.impl.spigot_1_8;

import net.lightshard.interfaceframework.VersionHandler;
import net.lightshard.interfaceframework.session.Session;
import net.lightshard.interfaceframework.session.SessionManager;
import net.lightshard.interfaceframework.ui.UserInterface;
import net.lightshard.interfaceframework.ui.content.Button;
import net.lightshard.interfaceframework.ui.content.ButtonListener;
import net.lightshard.interfaceframework.ui.content.ContentItem;
import net.lightshard.interfaceframework.ui.content.TaskStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The delegate that manages Sessions & the logical side of UserInterfaces, rather than the
 * item/in-game side of them.
 */
public class SessionManager_1_8 extends SessionManager
{
    private final Map<UUID, Session> sessions;

    public SessionManager_1_8(VersionHandler versionHandler)
    {
        super(versionHandler);

        sessions = new HashMap<UUID, Session>();
    }

    private boolean hasSession(Player player)
    {
        return sessions.containsKey(player.getUniqueId());
    }

    private void createSession(Player player)
    {
        sessions.put(player.getUniqueId(), new Session(player));
    }

    @Override
    public Session getSession(Player player)
    {
        if(!hasSession(player)) createSession(player);

        return sessions.get(player.getUniqueId());
    }

    @Override
    public void openInterface(final UserInterface userInterface, final Player player)
    {
        final Runnable opener = new Runnable()
        {
            @Override
            public void run()
            {
                Session session = getSession(player);
                Inventory inventory = userInterface.getDelegate().build(player);
                userInterface.getDelegate().populate(inventory, player, session);
                player.openInventory(inventory);

                if(session.getOpenInterface() != null
                        && !session.getOpenInterface().equals(userInterface))
                {
                    session.getPreviousOpenInterfaces().add(session.getOpenInterface());
                }
                session.setOpenInterface(userInterface);
                getVersionHandler().getSessionManager().getSession(player).setSwitching(false);
            }
        };

        if(userInterface.getDelegate().hasInventoryOpen(player))
        {
            getVersionHandler().getSessionManager().getSession(player).setSwitching(true);
            final JavaPlugin plugin = getVersionHandler().getInterfaceManager().getPlugin();
            player.closeInventory();
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
            {
                @Override
                public void run()
                {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, opener, 1L);
                }
            }, 1);
        }
        else
        {
            opener.run();
        }
    }

    @Override
    public void tick()
    {
        for (Session session : sessions.values())
        {
            if (session.getOpenInterface() == null) continue;

            for (ContentItem contentItem : session.getOpenContent())
            {
                for (Map.Entry<Runnable, TaskStatus> entry : contentItem.getTasks())
                {
                    TaskStatus status = entry.getValue();
                    status.increment();
                    if(status.isReady())
                    {
                        status.reset();

                        // Don't trust the API user
                        try
                        {
                            entry.getKey().run();
                        }
                        catch (Exception exception)
                        {
                            Bukkit.getConsoleSender().sendMessage("Invalid content task scheduled, continuing");
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Session session = getSession(player);

        if (session.getOpenInterface() != null)
        {
            UserInterface open = session.getOpenInterface();
            if (open.getPreset(player, session.getMeta()).isCancelClicksByDefault())
                event.setCancelled(true);

            for (ContentItem contentItem : session.getOpenContent())
            {
                if (contentItem.hasClicked(event))
                {
                    switch (contentItem.getType())
                    {
                        case BUTTON:
                            for(ButtonListener listener : ((Button) contentItem).getListeners())
                            {
                                listener.onClicked(event);
                            }
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();
        Session session = getSession(player);

        // Let the programmer do something if they've closed an inventory
        if(session.getOpenInterface() != null)
            session.getOpenInterface().onInventoryClosed(player, getSession(player).getMeta());

        // Ensure we don't clear the stack of previously open interfaces if they're switching UI
        if(!session.isSwitching())
        {
            session.getPreviousOpenInterfaces().clear(); // Clear the stack of previously open interfaces
            session.setOpenInterface(null);
        }
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        onPlayerLeave(event);
    }

    @Override
    public void onPlayerKick(PlayerKickEvent event)
    {
        onPlayerLeave(event);
    }

    private void onPlayerLeave(PlayerEvent event)
    {
        sessions.remove(event.getPlayer());
    }

}
