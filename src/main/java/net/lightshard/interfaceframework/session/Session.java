package net.lightshard.interfaceframework.session;

import net.lightshard.interfaceframework.ui.InterfacePreset;
import net.lightshard.interfaceframework.ui.UserInterface;
import net.lightshard.interfaceframework.ui.content.ContentItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Stack;
import java.util.UUID;

public class Session
{
    //////////////////////////////////////////////////
    /// DATA

    private final UUID uniqueId;
    private WeakReference<Player> playerReference;

    private final SessionMeta meta;

    private final Stack<UserInterface> previousOpenInterfaces;
    private UserInterface openInterface;
    private Collection<ContentItem> openContent; // The content they're currently viewing
    private boolean isSwitching;

    //////////////////////////////////////////////////
    /// CONSTRUCTORS

    public Session(Player player)
    {
        this.uniqueId = player.getUniqueId();
        this.playerReference = new WeakReference<Player>(player);

        this.meta = new SessionMeta();

        this.previousOpenInterfaces = new Stack<UserInterface>();
        this.isSwitching = false;
    }

    public boolean hasUserInterfaceOpen()
    {
        return openInterface != null;
    }

    //////////////////////////////////////////////////
    /// GETTERS & SETTERS

    public UUID getUniqueId()
    {
        return uniqueId;
    }

    public WeakReference<Player> getPlayerReference()
    {
        return playerReference;
    }

    public SessionMeta getMeta()
    {
        return meta;
    }

    public Stack<UserInterface> getPreviousOpenInterfaces()
    {
        return previousOpenInterfaces;
    }

    public UserInterface getOpenInterface()
    {
        return openInterface;
    }

    public void setOpenInterface(UserInterface openInterface)
    {
        this.openInterface = openInterface;
    }

    public boolean isSwitching()
    {
        return isSwitching;
    }

    public void setSwitching(boolean switching)
    {
        isSwitching = switching;
    }

    public Collection<ContentItem> getOpenContent()
    {
        return openContent;
    }

    public void setOpenContent(Collection<ContentItem> openContent)
    {
        this.openContent = openContent;
    }
}
