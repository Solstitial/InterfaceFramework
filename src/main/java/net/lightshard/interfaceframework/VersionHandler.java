package net.lightshard.interfaceframework;

import net.lightshard.interfaceframework.session.SessionManager;
import net.lightshard.interfaceframework.ui.InterfaceDelegate;
import net.lightshard.interfaceframework.ui.OpenInterfaceChecker;
import net.lightshard.interfaceframework.ui.UserInterface;

import static net.lightshard.interfaceframework.util.ReflectionUtil.instantiateObject;

public class VersionHandler
{
    //////////////////////////////////////////////////
    /// MEMBERS
    private final Version version;

    //////////////////////////////////////////////////
    /// PER-INTERFACEMANAGER OBJECTS
    private SessionManager sessionManager;
    private OpenInterfaceChecker openInterfaceChecker;

    //////////////////////////////////////////////////
    /// CONSTRUCTORS

    public VersionHandler(Version version)
    {
        this.version = version;
    }

    //////////////////////////////////////////////////
    /// PER-USERINTERFACE GETTERS

    public SessionManager getSessionManager()
    {
        if (sessionManager == null)
            sessionManager = instantiateObject(version.getSessionManagerClazz(),
                                               new Object[]{getOpenInterfaceChecker()});
        return sessionManager;
    }

    public OpenInterfaceChecker getOpenInterfaceChecker()
    {
        if(openInterfaceChecker == null)
            openInterfaceChecker = instantiateObject(version.getOpenInterfaceCheckerClazz(),
                                                     new Object[]{});
        return openInterfaceChecker;
    }

    //////////////////////////////////////////////////
    /// PER-USERINTERFACE OBJECT CREATORS

    public InterfaceDelegate newDelegate(UserInterface userInterface)
    {
        return instantiateObject(version.getDelegateClazz(),
                                 new Object[]{userInterface});
    }

}
