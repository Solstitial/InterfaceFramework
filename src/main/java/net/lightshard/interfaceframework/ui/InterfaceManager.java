package net.lightshard.interfaceframework.ui;

import net.lightshard.interfaceframework.Version;
import net.lightshard.interfaceframework.VersionHandler;
import net.lightshard.interfaceframework.session.SessionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class InterfaceManager implements Listener, Runnable
{
    private final JavaPlugin plugin;
    private final VersionHandler versionHandler;
    private final SessionManager sessionManager;

    private boolean initialised = false;

    public InterfaceManager(JavaPlugin plugin)
    {
        this.plugin = plugin;
        versionHandler = new VersionHandler(Version.getVersion());
        sessionManager = versionHandler.getSessionManager();
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
    }

    public void show(UserInterface userInterface, Player player)
    {
        // TODO
    }

    public VersionHandler getVersionHandler()
    {
        return versionHandler;
    }

}
