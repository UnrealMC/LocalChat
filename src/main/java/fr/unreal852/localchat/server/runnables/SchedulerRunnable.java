package fr.unreal852.localchat.server.runnables;

public abstract class SchedulerRunnable implements Runnable
{
    public Long _ticks;

    public SchedulerRunnable(int seconds)
    {
        _ticks = 20L * seconds;
    }
}
