package net.lightshard.interfaceframework.ui;

import net.lightshard.interfaceframework.VersionHandler;
import net.lightshard.interfaceframework.session.Session;
import net.lightshard.interfaceframework.session.SessionManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class InterfaceDelegate
{
    private final VersionHandler versionHandler;
    private final UserInterface delegator;

    public InterfaceDelegate(VersionHandler versionHandler, UserInterface delegator)
    {
        this.versionHandler = versionHandler;
        this.delegator = delegator;
    }

    public abstract Inventory build(Player player);

    /**
     *
     * @param inventory
     * @param player
     * @param session the session to set the content of
     */
    public abstract void populate(Inventory inventory, Player player, Session session);

    public abstract void refresh(Player player);

    public abstract boolean hasInventoryOpen(Player player);

    public abstract Inventory getInterfaceFromPlayer(Player player);

    public VersionHandler getVersionHandler()
    {
        return versionHandler;
    }

    public UserInterface getDelegator()
    {
        return delegator;
    }
}
