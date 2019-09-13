package net.lightshard.interfaceframework.impl.spigot_1_8;

import net.lightshard.interfaceframework.VersionHandler;
import net.lightshard.interfaceframework.session.SessionMeta;
import net.lightshard.interfaceframework.session.SessionMetaObject;
import net.lightshard.interfaceframework.ui.ListenerDelegate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * The delegate that calls appropriate event methods.
 */
public class ListenerDelegate_1_8 extends ListenerDelegate
{
    private static final String LAST_CLICKED_META_KEY = "INTERNALS_LAST_CLICKED";
    private static final long CLICK_DELAY = 20; // 20ms click delay

    public ListenerDelegate_1_8(VersionHandler versionHandler)
    {
        super(versionHandler);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event)
    {
        /**
         * In 1.8, clicks registered as a double click so let's make sure we add a timer
         */
        Player player = (Player) event.getWhoClicked();
        SessionMeta meta = getVersionHandler().getSessionManager().getSession(player).getMeta();

        SessionMetaObject<Long> metaObject;
        if(meta.exists(LAST_CLICKED_META_KEY))
            metaObject = meta.get(LAST_CLICKED_META_KEY);
        else
        {
            metaObject = new SessionMetaObject<Long>(0L);
            meta.set(LAST_CLICKED_META_KEY, metaObject);
        }

        long now = System.currentTimeMillis();
        if(System.currentTimeMillis() - metaObject.asType() < CLICK_DELAY)
        {
            Bukkit.broadcastMessage("CLICK REJECTED");
            return;
        }

        Bukkit.broadcastMessage("CLICK WENT THROUGH");
        metaObject.set(now);
        getVersionHandler().getSessionManager().onInventoryClick(event);
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event)
    {
        getVersionHandler().getSessionManager().onInventoryClose(event);
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        getVersionHandler().getSessionManager().onPlayerQuit(event);
    }

    @Override
    public void onPlayerKick(PlayerKickEvent event)
    {
        getVersionHandler().getSessionManager().onPlayerKick(event);
    }

}
