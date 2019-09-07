package net.lightshard.interfaceframework.util;

import java.lang.reflect.Constructor;

public class ReflectionUtil
{

    private ReflectionUtil() {}

    public static <T> T instantiateObject(Class<? extends T> clazz, Object[] params)
    {
        Class<?>[] paramTypes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i ++)
            paramTypes[i] = params[i].getClass();

        try
        {
            Constructor<? extends T> con = clazz.getDeclaredConstructor(paramTypes);
            con.setAccessible(true);
            return con.newInstance(params);
        }
        catch (Exception ignored)
        {
            return null;
        }
    }

}
