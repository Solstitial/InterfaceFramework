package net.lightshard.interfaceframework.ui;

import net.lightshard.interfaceframework.DelegationVersion;
import net.lightshard.interfaceframework.VersionHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class InterfaceManager implements Listener, Runnable
{
    //////////////////////////////////////////////////
    /// MEMBERS
    private final JavaPlugin plugin;
    private final VersionHandler versionHandler;

    /**
     * The set of interfaces that the UIManager has seen by the {@link #show(UserInterface, Player)}
     * method.
     *
     * This is primarily used to track the active interfaces so that they can have
     * the scheduled tasks of the ContentItems for each viewer performed.
     */
    private final Set<UserInterface> interfaces;

    private boolean initialised = false;

    //////////////////////////////////////////////////
    /// CONSTRUCTORS

    public InterfaceManager(JavaPlugin plugin)
    {
        this.plugin = plugin;
        versionHandler = new VersionHandler(this, DelegationVersion.getDelegateVersion());

        interfaces = new HashSet<UserInterface>();
    }

    //////////////////////////////////////////////////
    /// METHODS

    public void initialise()
    {
        if (initialised) return;

        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 1, 1);
        initialised = true;
    }

    @Override
    public void run()
    {
        getVersionHandler().getSessionManager().tick();
    }

    public void show(UserInterface userInterface, Player player)
    {
        interfaces.add(userInterface);
        versionHandler.getSessionManager().openInterface(userInterface, player);
    }

    //////////////////////////////////////////////////
    /// EVENTS

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onInventoryClick(InventoryClickEvent event)
    {
        versionHandler.getListenerDelegate().onInventoryClick(event);
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onInventoryClose(InventoryCloseEvent event)
    {
        versionHandler.getListenerDelegate().onInventoryClose(event);
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onPlayerQuit(PlayerQuitEvent event)
    {
        versionHandler.getListenerDelegate().onPlayerQuit(event);
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onPlayerKick(PlayerKickEvent event)
    {
        versionHandler.getListenerDelegate().onPlayerKick(event);
    }

    //////////////////////////////////////////////////
    /// GETTERS & SETTERS

    public JavaPlugin getPlugin()
    {
        return plugin;
    }

    public VersionHandler getVersionHandler()
    {
        return versionHandler;
    }

}
