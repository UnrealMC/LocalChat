package fr.unreal852.localchat.server.mixin;

import fr.unreal852.localchat.server.runnables.IWorldTickSchedulerAccess;
import fr.unreal852.localchat.server.runnables.SchedulerRunnable;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Iterator;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements IWorldTickSchedulerAccess
{
    @Unique
    private final ArrayList<SchedulerRunnable> _schedulers = new ArrayList<>();

    @Inject(at = @At("TAIL"), method = "tick")
    private void onTick(CallbackInfo ci)
    {
        if (_schedulers.size() == 0)
            return;
        Iterator<SchedulerRunnable> iterator = _schedulers.iterator();
        while (iterator.hasNext())
        {
            SchedulerRunnable runnable = iterator.next();
            if (--runnable._ticks <= 0)
            {
                runnable.run();
                iterator.remove();
            }
        }
    }

    @Override
    public void registerScheduler(SchedulerRunnable schedulerRunnable)
    {
        _schedulers.add(schedulerRunnable);
    }
}
