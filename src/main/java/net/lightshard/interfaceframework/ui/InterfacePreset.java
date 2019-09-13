package net.lightshard.interfaceframework.ui;

public class InterfacePreset
{
    private final String title;
    private final InterfaceType type;
    private final boolean cancelClicksByDefault;

    public InterfacePreset(String title, InterfaceType type, boolean cancelClicksByDefault)
    {
        this.title = title;
        this.type = type;
        this.cancelClicksByDefault = cancelClicksByDefault;
    }

    public String getTitle()
    {
        return title;
    }

    public InterfaceType getType()
    {
        return type;
    }

    public boolean isCancelClicksByDefault()
    {
        return cancelClicksByDefault;
    }

}
