package net.lightshard.interfaceframework;

import net.lightshard.interfaceframework.impl.spigot_1_8.InterfaceDelegate_1_8;
import net.lightshard.interfaceframework.impl.spigot_1_8.ListenerDelegate_1_8;
import net.lightshard.interfaceframework.impl.spigot_1_8.SessionManager_1_8;
import net.lightshard.interfaceframework.session.SessionManager;
import net.lightshard.interfaceframework.ui.InterfaceDelegate;
import net.lightshard.interfaceframework.ui.ListenerDelegate;
import org.bukkit.Bukkit;

public enum Version
{
    /**
     * Bountiful Update
     */
    VERSION_1_8("1.8", SessionManager_1_8.class, ListenerDelegate_1_8.class, InterfaceDelegate_1_8.class),
//    /**
//     * Combat Update (Pitiful Update?)
//     */
//    VERSION_1_9,
//    /**
//     * Aquatic Update
//     */
//    VERSION_1_13,
//    /**
//     * Village Pillage Update
//     */
//    VERSION_1_14,
    UNKNOWN("unknown", Version.VERSION_1_8);

    //////////////////////////////////////////////////
    /// STATIC MEMBERS
    private static Version serverVersion;

    //////////////////////////////////////////////////
    /// PER-INTERFACEMANAGER OBJECTS
    private final String versionNumber;
    private final Class<? extends SessionManager> sessionManagerClazz;
    private final Class<? extends ListenerDelegate> listenerDelegateClazz;

    //////////////////////////////////////////////////
    /// PER-USERINTERFACE OBJECTS
    private final Class<? extends InterfaceDelegate> delegateClazz;

    //////////////////////////////////////////////////
    /// CONSTRUCTORS

    Version(String versionNumber,
            Class<? extends SessionManager> sessionManagerClazz,
            Class<? extends ListenerDelegate> listenerDelegateClazz,
            Class<? extends InterfaceDelegate> delegateClazz)
    {
        this.versionNumber = versionNumber;
        this.sessionManagerClazz = sessionManagerClazz;
        this.listenerDelegateClazz = listenerDelegateClazz;
        this.delegateClazz = delegateClazz;
    }

    Version(String versionNumber, Version toCopy)
    {
        this.versionNumber = versionNumber;
        this.sessionManagerClazz = toCopy.sessionManagerClazz;
        this.listenerDelegateClazz = toCopy.listenerDelegateClazz;
        this.delegateClazz = toCopy.delegateClazz;
    }

    //////////////////////////////////////////////////
    /// GETTERS & SETTERS

    public static Version getServerVersion()
    {
        if (serverVersion == null)
        {
            String bukkitVersion = Bukkit.getVersion().toLowerCase();
            serverVersion = UNKNOWN;
            for (Version version : values())
            {
                if (bukkitVersion.contains(version.versionNumber))
                {
                    serverVersion = version;
                    break;
                }
            }
        }
        return serverVersion;
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
