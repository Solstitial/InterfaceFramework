package net.lightshard.interfaceframework.session;

import net.lightshard.interfaceframework.ui.OpenInterfaceChecker;
import net.lightshard.interfaceframework.ui.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class SessionManager
{
    private final OpenInterfaceChecker openInterfaceChecker;

    public SessionManager(OpenInterfaceChecker openInterfaceChecker)
    {
        this.openInterfaceChecker = openInterfaceChecker;
    }

    public abstract void openInterface(UserInterface userInterface, Player player);

    public abstract void switchInterface(UserInterface userInterface, Player player);

    public abstract void closeInterface(UserInterface userInterface, Player player);

    public abstract void onPlayerQuit(PlayerQuitEvent event);

    public abstract void onPlayerKick(PlayerKickEvent event);

    public OpenInterfaceChecker getOpenInterfaceChecker()
    {
        return openInterfaceChecker;
    }
}
