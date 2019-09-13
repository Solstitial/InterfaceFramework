package net.lightshard.interfaceframework.ui;

import net.lightshard.interfaceframework.session.SessionMeta;
import net.lightshard.interfaceframework.ui.content.Button;
import net.lightshard.interfaceframework.VersionHandler;
import net.lightshard.interfaceframework.ui.content.ContentItem;
import org.bukkit.entity.Player;

import java.util.Collection;

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

    /**
     * The preset's needed to build an empty version of this user-interface.
     *
     * @param player the Player whom to create it for
     * @param meta   the key-value storage object for persisting data throughout GUIs for this Player
     * @return       the preset data
     */
    public abstract InterfacePreset getPreset(Player player, SessionMeta meta);

    /**
     * Get the content at the current time.
     *
     * @param player the player to generate the content for
     * @param meta the key-value storage object for persisting data throughout GUIs for this Player
     * @return the content to display
     */
    public abstract Collection<ContentItem> getContent(Player player, SessionMeta meta);

    /**
     * Called when the Inventory is closed, whether this be switching to a new one,
     * or closed for good.
     *
     * @param player the player who closed it
     * @param meta   the key-value storage object for persisting data throughout GUIs for this Player
     */
    public abstract void onInventoryClosed(Player player, SessionMeta meta);

    public void refresh(Player player)
    {
        delegate.refresh(player);
    }

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

    public InterfaceDelegate getDelegate()
    {
        return delegate;
    }

}
