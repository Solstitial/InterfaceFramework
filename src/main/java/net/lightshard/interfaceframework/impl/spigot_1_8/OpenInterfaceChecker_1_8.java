package net.lightshard.interfaceframework.impl.spigot_1_8;

import net.lightshard.interfaceframework.session.Session;
import net.lightshard.interfaceframework.ui.OpenInterfaceChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class OpenInterfaceChecker_1_8 implements OpenInterfaceChecker
{

    @Override
    public boolean isOpen(Player player, Session session)
    {
        if(session.getOpenInterface() != null) return true;

        Inventory topInventory = player.getOpenInventory().getTopInventory();
        return topInventory != null
                && topInventory.getType() != null
                && isNonPlayerInventoryType(topInventory.getType());
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
