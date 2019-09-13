package net.lightshard.interfaceframework.ui.content;

import net.lightshard.interfaceframework.session.SessionMeta;
import net.lightshard.interfaceframework.ui.UserInterface;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class ContentItem
{
    private final ContentType type;
    private final Map<Runnable, TaskStatus> scheduledTasks;

    /**
     * Injected after {@link UserInterface#getContent(Player, SessionMeta)} so that
     * if a ContentItem wishes to change itself within the Inventory, this is
     * possible.
     */
    private Inventory inventory;

    public ContentItem(ContentType type)
    {
        this.type = type;
        scheduledTasks = new LinkedHashMap<Runnable, TaskStatus>();
    }

    public abstract boolean hasClicked(InventoryClickEvent event);

    public void scheduleTask(Runnable task, long delay)
    {
        scheduledTasks.put(task, new TaskStatus(delay));
    }

    public Set<Map.Entry<Runnable, TaskStatus>> getTasks()
    {
        return scheduledTasks.entrySet();
    }

    public void stopTask(Runnable runnable)
    {
        scheduledTasks.remove(runnable);
    }

    public ContentType getType()
    {
        return type;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void injectInventory(Inventory interfaceInventory)
    {
        this.inventory = interfaceInventory;
    }
}
