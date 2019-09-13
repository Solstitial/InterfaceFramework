package net.lightshard.interfaceframework.ui.content;

import net.lightshard.interfaceframework.impl.spigot_1_8.ItemComparer_1_8;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Button extends ContentItem
{
    private final int slot;
    private ItemStack itemStack;

    private final Set<ButtonListener> listeners;

    public Button(int slot, ItemStack itemStack)
    {
        super(ContentType.BUTTON);

        this.slot = slot;
        this.itemStack = itemStack;

        listeners = new HashSet<ButtonListener>();
    }

    @Override
    public boolean hasClicked(InventoryClickEvent event)
    {
        if(event.getCurrentItem() == null) return false;

        return event.getSlot() == slot
                && ItemComparer_1_8.matches(event.getCurrentItem(), itemStack);
    }

    public void setContent(ItemStack itemStack)
    {
        getInventory().setItem(slot, this.itemStack = itemStack);
    }

    public int getSlot()
    {
        return slot;
    }

    public ItemStack getContent()
    {
        return itemStack;
    }

    public Set<ButtonListener> getListeners()
    {
        return listeners;
    }

    public void addListener(ButtonListener listener)
    {
        listeners.add(listener);
    }


}
