package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.server.ChatManager;
import fr.unreal852.localchat.server.runnables.IWorldTickSchedulerAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

public class DisableDistanceCommand implements Command<ServerCommandSource>
{
    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(LocalChat.CONFIG.enableDisableDistanceCommandPermissionLevel))
                return 0;
            ServerWorld serverWorld = source.getWorld();
            if (!(serverWorld instanceof IWorldTickSchedulerAccess schedulerAccess))
                return 0;
            ChatManager.GlobalChatEnabled = true;
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}