package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.server.ChatManager;
import fr.unreal852.localchat.server.mixin.ServerWorldMixin;
import fr.unreal852.localchat.server.runnables.IWorldTickSchedulerAccess;
import fr.unreal852.localchat.server.runnables.SchedulerRunnable;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class DisableDistanceCommand implements Command<ServerCommandSource>
{
    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(LocalChat.getConfig().enableDisableDistanceCommandPermissionLevel))
                return 0;
            if (ChatManager.GlobalChatEnabled)
            {
                source.sendFeedback(Text.literal("Global chat is already enabled !"), false);
                return 0;
            }
            ServerWorld serverWorld = source.getWorld();
            if (!(serverWorld instanceof IWorldTickSchedulerAccess schedulerAccess))
                return 0;
            ChatManager.GlobalChatEnabled = true;
            ChatManager.broadCastMessage("Global chat enabled !", source.getChatMessageSender(), source.getServer());
            schedulerAccess.registerScheduler(new SchedulerRunnable(10)
            {
                @Override
                public void run()
                {
                    if (!ChatManager.GlobalChatEnabled)
                        return;
                    ChatManager.GlobalChatEnabled = false;
                    ChatManager.broadCastMessage("Global chat disabled !", source.getChatMessageSender(), source.getServer());
                }
            });
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}