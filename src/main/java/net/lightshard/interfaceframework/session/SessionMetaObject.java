package net.lightshard.interfaceframework.session;

import org.bukkit.inventory.ItemStack;

public class SessionMetaObject<T>
{
    private T value;

    public SessionMetaObject(T value)
    {
        this.value = value;
    }

    public T asType()
    {
        return value;
    }

    public String asString()
    {
        return (String) value;
    }

    public ItemStack asItemStack()
    {
        return (ItemStack) value;
    }

    public Byte asByte()
    {
        return (Byte) value;
    }

    public Short asShort()
    {
        return (Short) value;
    }

    public Integer asInteger()
    {
        return (Integer) value;
    }

    public Float asFloat()
    {
        return (Float) value;
    }

    public Double asDouble()
    {
        return (Double) value;
    }

    public Long asLong()
    {
        return (Long) value;
    }

    public Boolean asBoolean()
    {
        return (Boolean) value;
    }

    public void set(T value)
    {
        this.value = value;
    }
}
