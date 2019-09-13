package net.lightshard.interfaceframework.util;

public class StrongReference<T>
{
    private T value;

    public StrongReference(T value)
    {
        this.value = value;
    }

    public T get()
    {
        return value;
    }

    public void set(T value)
    {
        this.value = value;
    }
}
