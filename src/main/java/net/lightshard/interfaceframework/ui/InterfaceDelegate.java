package net.lightshard.interfaceframework.ui;

public abstract class InterfaceDelegate
{
    private UserInterface delegator;

    public InterfaceDelegate(UserInterface delegator)
    {
        this.delegator = delegator;
    }

}
