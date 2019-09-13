package net.lightshard.interfaceframework.ui;

import net.lightshard.interfaceframework.VersionHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class ListenerDelegate implements Listener
{
    private VersionHandler versionHandler;

    public ListenerDelegate(VersionHandler versionHandler)
    {
        this.versionHandler = versionHandler;
    }

    public abstract void onInventoryClick(InventoryClickEvent event);

    public abstract void onInventoryClose(InventoryCloseEvent event);

    public abstract void onPlayerQuit(PlayerQuitEvent event);

    public abstract void onPlayerKick(PlayerKickEvent event);

    public VersionHandler getVersionHandler()
    {
        return versionHandler;
    }

}
