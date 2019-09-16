package net.lightshard.interfaceframework;

import net.lightshard.interfaceframework.impl.spigot_1_8.InterfaceDelegate_1_8;
import net.lightshard.interfaceframework.impl.spigot_1_8.ListenerDelegate_1_8;
import net.lightshard.interfaceframework.impl.spigot_1_8.SessionManager_1_8;
import net.lightshard.interfaceframework.session.SessionManager;
import net.lightshard.interfaceframework.ui.InterfaceDelegate;
import net.lightshard.interfaceframework.ui.ListenerDelegate;
import net.lightshard.versionframework.Version;

public enum DelegationVersion
{
    VERSION_1_8(Version.VERSION_1_8, SessionManager_1_8.class, ListenerDelegate_1_8.class, InterfaceDelegate_1_8.class),
    UNKNOWN(Version.UNKNOWN, DelegationVersion.VERSION_1_8);

    //////////////////////////////////////////////////
    /// STATIC MEMBERS
    private static DelegationVersion delegateVersion;

    //////////////////////////////////////////////////
    /// PER-INTERFACEMANAGER OBJECTS
    private final Version version;
    private final Class<? extends SessionManager> sessionManagerClazz;
    private final Class<? extends ListenerDelegate> listenerDelegateClazz;

    //////////////////////////////////////////////////
    /// PER-USERINTERFACE OBJECTS
    private final Class<? extends InterfaceDelegate> delegateClazz;

    //////////////////////////////////////////////////
    /// CONSTRUCTORS

    DelegationVersion(Version version,
                      Class<? extends SessionManager> sessionManagerClazz,
                      Class<? extends ListenerDelegate> listenerDelegateClazz,
                      Class<? extends InterfaceDelegate> delegateClazz)
    {
        this.version = version;
        this.sessionManagerClazz = sessionManagerClazz;
        this.listenerDelegateClazz = listenerDelegateClazz;
        this.delegateClazz = delegateClazz;
    }

    DelegationVersion(Version version, DelegationVersion toCopy)
    {
        this.version = version;
        this.sessionManagerClazz = toCopy.sessionManagerClazz;
        this.listenerDelegateClazz = toCopy.listenerDelegateClazz;
        this.delegateClazz = toCopy.delegateClazz;
    }

    //////////////////////////////////////////////////
    /// GETTERS & SETTERS

    public static DelegationVersion getDelegateVersion()
    {
        if (delegateVersion == null)
        {
            Version version = Version.getServerVersion();
            delegateVersion = UNKNOWN;
            for (DelegationVersion delegationVersion : values())
            {
                if (delegationVersion.version.equals(version))
                {
                    delegateVersion = delegationVersion;
                    break;
                }
            }
        }
        return delegateVersion;
    }

    public Class<? extends SessionManager> getSessionManagerClazz()
    {
        return sessionManagerClazz;
    }

    public Class<? extends ListenerDelegate> getListenerDelegateClazz()
    {
        return listenerDelegateClazz;
    }

    public Class<? extends InterfaceDelegate> getDelegateClazz()
    {
        return delegateClazz;
    }
}
