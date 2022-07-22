package fr.unreal852.localchat.server.runnables;

public interface IWorldTickSchedulerAccess
{
    void registerScheduler(SchedulerRunnable schedulerRunnable);
}
