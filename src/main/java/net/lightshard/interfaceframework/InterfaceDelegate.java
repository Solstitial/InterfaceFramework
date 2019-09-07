package net.lightshard.interfaceframework;

public abstract class InterfaceDelegate
{
    private final VersionHandler versionHandler;

    public InterfaceDelegate(VersionHandler handler)
    {
        this.versionHandler = handler;
    }

    public VersionHandler getVersionHandler()
    {
        return versionHandler;
    }

}
