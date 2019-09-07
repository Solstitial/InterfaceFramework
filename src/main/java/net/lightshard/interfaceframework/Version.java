package net.lightshard.interfaceframework;

import net.lightshard.interfaceframework.spigot_1_8.InterfaceDelegate_1_8;
import org.bukkit.Bukkit;

public enum Version
{
    SPIGOT_1_8("v1_8", InterfaceDelegate_1_8.class),
    UNKNOWN("unknown", InterfaceDelegate_1_8.class);

    private static Version serverVersion;

    private String versionNumber;
    private Class<? extends InterfaceDelegate> delegateClazz;

    Version(String versionNumber, Class<? extends InterfaceDelegate> delegateClazz)
    {
        this.versionNumber = versionNumber;
        this.delegateClazz = delegateClazz;
    }

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

    public Class<? extends InterfaceDelegate> getDelegateClazz()
    {
        return delegateClazz;
    }

}
