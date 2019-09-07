package net.lightshard.interfaceframework;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class InterfaceManager implements Listener, Runnable
{
    private final JavaPlugin plugin;
    private final Set<UserInterface> userInterfaces;

    private boolean initialised = false;

    public InterfaceManager(JavaPlugin plugin)
    {
        this.plugin = plugin;
        userInterfaces = new HashSet<UserInterface>();
    }

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
        for (UserInterface userInterface : userInterfaces)
        {
             // TODO - Get all sessions & act on them
        }
    }

    public void show(UserInterface userInterface, Player player)
    {
        userInterface.getDelegate(); // TODO
    }
}
