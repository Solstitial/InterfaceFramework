package net.lightshard.interfaceframework.ui.page;

import net.lightshard.interfaceframework.ui.content.Button;
import org.bukkit.inventory.ItemStack;

public class PagedButton extends Button
{
    private int injectedSlot;

    public PagedButton(ItemStack itemStack)
    {
        super(-1, itemStack);
    }

    public int getInjectedSlot()
    {
        return injectedSlot;
    }

    public void injectSlot(int injectedSlot)
    {
        this.injectedSlot = injectedSlot;
    }

}
