package net.lightshard.interfaceframework.impl.spigot_1_8;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemComparer_1_8
{
    private static Material skullItem;

    static
    {
        try
        {
            skullItem = Material.valueOf("SKULL_ITEM"); // in 1.8, they were called SKULL_ITEM
        }
        catch (Exception ignored) {}
    }

    private ItemComparer_1_8() {}

    public static boolean matches(ItemStack itemA, ItemStack itemB)
    {
        // Check if the skullItem is null because we use 1.8 as the default fallback version, and if they're
        // running a revision that is the latest, it may have changed
        if(skullItem != null)
        {
            //Skulls don't work with #isSimilar(ItemStack)
            if(itemB.getType().equals(skullItem) && itemA.getType().equals(skullItem))
            {
                ItemStack clone1 = itemA.clone();
                ItemStack clone2 = itemB.clone();
                if(clone1.getDurability() == clone2.getDurability())
                {
                    SkullMeta clone1Meta = (SkullMeta) clone1.getItemMeta();
                    clone1Meta.setOwner(null);
                    clone1.setItemMeta(clone1Meta);
                    SkullMeta clone2Meta = (SkullMeta) clone2.getItemMeta();
                    clone2Meta.setOwner(null);
                    clone2.setItemMeta(clone2Meta);
                    return clone1.isSimilar(clone2);
                }
            }
        }
        return itemB.isSimilar(itemA);
    }

}
