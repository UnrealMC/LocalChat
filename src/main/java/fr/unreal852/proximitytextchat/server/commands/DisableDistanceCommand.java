package fr.unreal852.proximitytextchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.proximitytextchat.ProximityTextChat;
import fr.unreal852.proximitytextchat.server.ChatManager;
import fr.unreal852.proximitytextchat.server.ChatTextColor;
import fr.unreal852.proximitytextchat.server.runnables.IWorldTickSchedulerAccess;
import fr.unreal852.proximitytextchat.server.runnables.SchedulerRunnable;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

public class DisableDistanceCommand implements Command<ServerCommandSource>
{
    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(ProximityTextChat.getConfig().enableDisableDistanceCommandPermissionLevel))
                return 0;
            if (ChatManager.GlobalChatEnabled)
            {
                ChatManager.sendFeedback(source, "Global chat is already enabled !", ChatTextColor.Orange, false);
                return 0;
            }
            ServerWorld serverWorld = source.getWorld();
            if (!(serverWorld instanceof IWorldTickSchedulerAccess schedulerAccess))
                return 0;
            ChatManager.GlobalChatEnabled = true;
            ChatManager.broadCastMessage(source.getServer(), source, "Global chat enabled !", ChatTextColor.Green);
            schedulerAccess.registerScheduler(new SchedulerRunnable(10)
            {
                @Override
                public void run()
                {
                    if (!ChatManager.GlobalChatEnabled)
                        return;
                    ChatManager.GlobalChatEnabled = false;
                    ChatManager.broadCastMessage(source.getServer(), source, "Global chat disabled !", ChatTextColor.Red);
                }
            });
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}