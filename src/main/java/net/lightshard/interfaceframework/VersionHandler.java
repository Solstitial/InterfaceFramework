package net.lightshard.interfaceframework;

import net.lightshard.interfaceframework.session.SessionManager;
import net.lightshard.interfaceframework.ui.InterfaceDelegate;
import net.lightshard.interfaceframework.ui.InterfaceManager;
import net.lightshard.interfaceframework.ui.ListenerDelegate;
import net.lightshard.interfaceframework.ui.UserInterface;

import java.lang.reflect.Constructor;

public class VersionHandler
{
    //////////////////////////////////////////////////
    /// MEMBERS
    private final InterfaceManager interfaceManager;
    private final DelegationVersion version;

    //////////////////////////////////////////////////
    /// PER-INTERFACEMANAGER OBJECTS
    private SessionManager sessionManager;
    private ListenerDelegate listenerDelegate;

    //////////////////////////////////////////////////
    /// CONSTRUCTORS

    public VersionHandler(InterfaceManager interfaceManager, DelegationVersion version)
    {
        this.interfaceManager = interfaceManager;
        this.version = version;
    }

    //////////////////////////////////////////////////
    /// REFLECTION FUNCTION

    public <T> T instantiateObject(Class<T> clazz, Class<?>[] types, Object[] values)
    {
        try
        {
            Constructor<T> con = clazz.getDeclaredConstructor(types);
            con.setAccessible(true);
            return con.newInstance(values);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    //////////////////////////////////////////////////
    /// PER-USERINTERFACEMANAGER GETTERS

    public InterfaceManager getInterfaceManager()
    {
        return interfaceManager;
    }

    public SessionManager getSessionManager()
    {
        if (sessionManager == null)
        {
            Class<?> clazz = version.getSessionManagerClazz();
            Class<?>[] types = new Class<?>[]
                    {
                            VersionHandler.class,
                    };
            Object[] values = new Object[]
                    {
                            this
                    };
            sessionManager = (SessionManager) instantiateObject(clazz, types, values);
        }
        return sessionManager;
    }

    public ListenerDelegate getListenerDelegate()
    {
        if (listenerDelegate == null)
        {
            Class<?> clazz = version.getListenerDelegateClazz();
            Class<?>[] types = new Class<?>[]
                    {
                            VersionHandler.class,
                    };
            Object[] values = new Object[]
                    {
                            this
                    };
            listenerDelegate = (ListenerDelegate) instantiateObject(clazz, types, values);
        }
        return listenerDelegate;
    }

    //////////////////////////////////////////////////
    /// PER-USERINTERFACE OBJECT CREATORS

    public InterfaceDelegate newDelegate(UserInterface userInterface)
    {
        Class<?> clazz = version.getDelegateClazz();
        Class<?>[] types = new Class<?>[]
                {
                        VersionHandler.class,
                        UserInterface.class
                };
        Object[] values = new Object[]
                {
                        this,
                        userInterface
                };
        return (InterfaceDelegate) instantiateObject(clazz, types, values);
    }
}
