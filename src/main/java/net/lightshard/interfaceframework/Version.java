package net.lightshard.interfaceframework;

import net.lightshard.interfaceframework.impl.spigot_1_8.InterfaceDelegate_1_8;
import net.lightshard.interfaceframework.impl.spigot_1_8.OpenInterfaceChecker_1_8;
import net.lightshard.interfaceframework.impl.spigot_1_8.SessionManager_1_8;
import net.lightshard.interfaceframework.session.SessionManager;
import net.lightshard.interfaceframework.ui.InterfaceDelegate;
import net.lightshard.interfaceframework.ui.OpenInterfaceChecker;
import org.bukkit.Bukkit;

public enum Version
{
    SPIGOT_1_8("v1_8", SessionManager_1_8.class, OpenInterfaceChecker_1_8.class, InterfaceDelegate_1_8.class),
    UNKNOWN("unknown", SessionManager_1_8.class, OpenInterfaceChecker_1_8.class, InterfaceDelegate_1_8.class);

    //////////////////////////////////////////////////
    /// STATIC MEMBERS
    private static Version serverVersion;

    //////////////////////////////////////////////////
    /// PER-INTERFACEMANAGER OBJECTS
    private final String versionNumber;
    private final Class<? extends SessionManager> sessionManagerClazz;
    private final Class<? extends OpenInterfaceChecker> openInterfaceCheckerClazz;

    //////////////////////////////////////////////////
    /// PER-USERINTERFACE OBJECTS
    private final Class<? extends InterfaceDelegate> delegateClazz;

    //////////////////////////////////////////////////
    /// CONSTRUCTORS

    Version(String versionNumber,
            Class<? extends SessionManager> sessionManagerClazz,
            Class<? extends OpenInterfaceChecker> openInterfaceCheckerClazz,
            Class<? extends InterfaceDelegate> delegateClazz)
    {
        this.versionNumber = versionNumber;
        this.sessionManagerClazz = sessionManagerClazz;
        this.openInterfaceCheckerClazz = openInterfaceCheckerClazz;
        this.delegateClazz = delegateClazz;
    }

    //////////////////////////////////////////////////
    /// GETTERS & SETTERS

    public static Version getVersion()
    {
        if (serverVersion == null)
        {
            String bukkitVersion = Bukkit.getVersion().toLowerCase();
            for (Version version : values())
            {
                if (bukkitVersion.contains(version.versionNumber))
                {
                    serverVersion = version;
                    break;
                }
            }
            serverVersion = UNKNOWN;
        }
        return serverVersion;
    }

    public Class<? extends SessionManager> getSessionManagerClazz()
    {
        return sessionManagerClazz;
    }

    public Class<? extends OpenInterfaceChecker> getOpenInterfaceCheckerClazz()
    {
        return openInterfaceCheckerClazz;
    }

    public Class<? extends InterfaceDelegate> getDelegateClazz()
    {
        return delegateClazz;
    }
}
