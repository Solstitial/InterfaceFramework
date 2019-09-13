package net.lightshard.interfaceframework.ui.content;

import org.bukkit.Bukkit;

public class TaskStatus
{
    private static final long TICK_COUNTER_DEFAULT = 0;

    private final long period;
    private long tickCounter;

    public TaskStatus(long period)
    {
        this.period = period;
        tickCounter = TICK_COUNTER_DEFAULT;
    }

    public void increment()
    {
        tickCounter ++;
    }

    public boolean isReady()
    {
        return tickCounter >= period;
    }

    public void reset()
    {
        tickCounter = TICK_COUNTER_DEFAULT;
    }

}
