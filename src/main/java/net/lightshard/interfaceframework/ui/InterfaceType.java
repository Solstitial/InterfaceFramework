package net.lightshard.interfaceframework.ui;

import org.bukkit.event.inventory.InventoryType;

public enum InterfaceType
{
    CHEST_9(9),
    CHEST_18(18),
    CHEST_27(27),
    CHEST_36(36),
    CHEST_45(45),
    CHEST_54(54),
    CHEST_63(63),
    CHEST_72(72),
    CHEST_81(81),
    CHEST_90(90);

    private final InventoryType inventoryType;
    private final int size;

    InterfaceType(InventoryType inventoryType, int size)
    {
        this.inventoryType = inventoryType;
        this.size = size;
    }

    InterfaceType(int size)
    {
        this(InventoryType.CHEST, size);
    }

    public InventoryType getInventoryType()
    {
        return inventoryType;
    }

    public int getSize()
    {
        return size;
    }
}