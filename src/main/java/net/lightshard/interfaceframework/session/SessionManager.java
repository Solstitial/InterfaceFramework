package net.lightshard.interfaceframework.session;

import net.lightshard.interfaceframework.VersionHandler;
import net.lightshard.interfaceframework.ui.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class SessionManager
{
    private final VersionHandler versionHandler;

    public SessionManager(VersionHandler versionHandler)
    {
        this.versionHandler = versionHandler;
    }

    public abstract Session getSession(Player player);

    public abstract void openInterface(UserInterface userInterface, Player player);

    public abstract void tick();

    public abstract void onInventoryClick(InventoryClickEvent event);

    public abstract void onInventoryClose(InventoryCloseEvent event);

    public abstract void onPlayerQuit(PlayerQuitEvent event);

    public abstract void onPlayerKick(PlayerKickEvent event);

    public VersionHandler getVersionHandler()
    {
        return versionHandler;
    }
}
