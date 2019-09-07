package net.lightshard.interfaceframework.impl.spigot_1_8;

import net.lightshard.interfaceframework.session.Session;
import net.lightshard.interfaceframework.session.SessionManager;
import net.lightshard.interfaceframework.ui.OpenInterfaceChecker;
import net.lightshard.interfaceframework.ui.UserInterface;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager_1_8 extends SessionManager
{
    private Map<UUID, Session> sessions;

    public SessionManager_1_8(OpenInterfaceChecker openInterfaceChecker)
    {
        super(openInterfaceChecker);

        sessions = new HashMap<UUID, Session>();
    }

    private boolean hasSession(Player player)
    {
        return sessions.containsKey(player.getUniqueId());
    }

    private void createSession(Player player)
    {
        sessions.put(player.getUniqueId(), new Session(player));
    }

    private Session getSession(Player player)
    {
        return sessions.get(player.getUniqueId());
    }

    @Override
    public void openInterface(UserInterface userInterface, Player player)
    {
        if (!hasSession((player))) createSession(player);

        Session session = getSession(player);
        if(getOpenInterfaceChecker().isOpen(player, session))
        {
            switchInterface(userInterface, player);
        }
        else
        {
            // TODO - Show
        }
    }

    @Override
    public void switchInterface(UserInterface userInterface, Player player)
    {
        if (!hasSession((player))) createSession(player);


        Session session = getSession(player);
        if(!getOpenInterfaceChecker().isOpen(player, session))
        {
            openInterface(userInterface, player);
        }
        else
        {
            session.getPreviousOpenInterfaces().add(session.getOpenInterface());

        }
    }

    @Override
    public void closeInterface(UserInterface userInterface, Player player)
    {
        if (!hasSession((player))) createSession(player);

        Session session = getSession(player);
        session.getPreviousOpenInterfaces().clear();
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        onPlayerLeave();
    }

    @Override
    public void onPlayerKick(PlayerKickEvent event)
    {
        onPlayerLeave();
    }

    private void onPlayerLeave()
    {

    }

}
