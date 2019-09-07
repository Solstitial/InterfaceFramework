package net.lightshard.interfaceframework;

public class UserInterface
{
    private final VersionHandler versionHandler;
    private final InterfaceDelegate delegate;

    public UserInterface()
    {
        versionHandler = new VersionHandler(Version.getVersion());
        delegate = versionHandler.newDelegate();
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof UserInterface
                && ((UserInterface) obj).hashCode() == hashCode();
    }

    protected InterfaceDelegate getDelegate()
    {
        return delegate;
    }

}
