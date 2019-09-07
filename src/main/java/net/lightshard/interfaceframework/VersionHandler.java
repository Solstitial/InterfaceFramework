package net.lightshard.interfaceframework;

import java.lang.reflect.Constructor;

public class VersionHandler
{
    private final Version version;

    public VersionHandler(Version version)
    {
        this.version = version;
    }

    protected InterfaceDelegate newDelegate()
    {
        Class<? extends InterfaceDelegate> clazz = version.getDelegateClazz();
        try
        {
            Constructor<? extends InterfaceDelegate> con = clazz.getDeclaredConstructor(VersionHandler.class);
            con.setAccessible(true);
            return con.newInstance(new Object[]{this});
        }
        catch (Exception ignored)
        {
            return null;
        }
    }

}
