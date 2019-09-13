package net.lightshard.interfaceframework.impl.spigot_1_8;

import net.lightshard.interfaceframework.VersionHandler;
import net.lightshard.interfaceframework.session.Session;
import net.lightshard.interfaceframework.session.SessionMeta;
import net.lightshard.interfaceframework.ui.content.Button;
import net.lightshard.interfaceframework.ui.InterfaceDelegate;
import net.lightshard.interfaceframework.ui.InterfacePreset;
import net.lightshard.interfaceframework.ui.UserInterface;
import net.lightshard.interfaceframework.ui.content.ContentItem;
import net.lightshard.interfaceframework.ui.page.PagedButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.Collection;

/**
 * The delegate that manages ItemStacks & Inventory creation.
 */
public class InterfaceDelegate_1_8 extends InterfaceDelegate
{

    public InterfaceDelegate_1_8(VersionHandler versionHandler, UserInterface delegator)
    {
        super(versionHandler, delegator);
    }

    @Override
    public Inventory build(Player player)
    {
        InterfacePreset preset = getDelegator().getPreset(player, getMeta(player));

        String title = preset.getTitle();
        if(title.length() > 32)
            title = title.substring(0, 31);

        Inventory inventory = null;
        switch (preset.getType().getInventoryType())
        {
            case CHEST:
            default:
                inventory = Bukkit.createInventory(null, preset.getType().getSize(), title);
                break;
        }

        return inventory;
    }

    @Override
    public void populate(Inventory inventory, Player player, Session session)
    {
        for (int i = 0; i < inventory.getSize(); i ++)
            inventory.setItem(i, null);

        Collection<ContentItem> content = getDelegator().getContent(player, getMeta(player));
        for (ContentItem item : content)
        {
            // Inject the Inventory so if a ContentItem wishes to modify itself in the future,
            // this is possible
            item.injectInventory(inventory);

            ss: switch (item.getType())
            {
                case BUTTON:
                    Button button = (Button) item;

                    // PagedButton's have a slot of -1 since we have to inject the slot into them
                    int slot = (button instanceof PagedButton)
                                    ? ((PagedButton) button).getInjectedSlot()
                                    : button.getSlot();

                    inventory.setItem(slot, button.getContent());
                    break ss;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getType());
            }
        }

        session.setOpenContent(content);
    }

    @Override
    public void refresh(Player player)
    {
        if(hasInventoryOpen(player))
        {
            Inventory inventory = getInterfaceFromPlayer(player);
            InterfacePreset preset = getDelegator().getPreset(player, getMeta(player));

            if(preset.getType().getSize() == inventory.getSize())
            {
                populate(getInterfaceFromPlayer(player), player,
                         getVersionHandler().getSessionManager().getSession(player));
            }
            else
            {
                // Preset has changed, size is different, need to re-open
                getVersionHandler().getSessionManager().openInterface(getDelegator(), player);
            }
        }
    }

    @Override
    public boolean hasInventoryOpen(Player player)
    {
        Session session = getVersionHandler().getSessionManager().getSession(player);
        if(session != null && session.getOpenInterface() != null) return true;

        Inventory topInventory = player.getOpenInventory().getTopInventory();
        return topInventory != null
                && topInventory.getType() != null
                && isNonPlayerInventoryType(topInventory.getType());
    }

    @Override
    public Inventory getInterfaceFromPlayer(Player player)
    {
        return player.getOpenInventory().getTopInventory();
    }

    private SessionMeta getMeta(Player player)
    {
        return getVersionHandler().getSessionManager().getSession(player).getMeta();
    }

    private boolean isNonPlayerInventoryType(InventoryType type)
    {
        switch(type)
        {
            case ANVIL:
            case BEACON:
            case BREWING:
            case CHEST:
            case DISPENSER:
            case DROPPER:
            case ENCHANTING:
            case ENDER_CHEST:
            case FURNACE:
            case HOPPER:
            case MERCHANT:
            case WORKBENCH:
            default:
                return true;
            case PLAYER:
            case CREATIVE:
            case CRAFTING:
                return false;
        }
    }

}
