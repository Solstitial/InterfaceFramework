package net.lightshard.interfaceframework.ui;

import net.lightshard.interfaceframework.VersionHandler;
import org.bukkit.entity.Player;

public abstract class UserInterface
{
    private final InterfaceManager manager;
    private final VersionHandler versionHandler;
    private final InterfaceDelegate delegate;

    public UserInterface(InterfaceManager manager)
    {
        this.manager = manager;
        this.versionHandler = manager.getVersionHandler();
        delegate = versionHandler.newDelegate(this);
    }

    public abstract void getButtons(Player player);

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof UserInterface
                && ((UserInterface) obj).hashCode() == hashCode();
    }

}
