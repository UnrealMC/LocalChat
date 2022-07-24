package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.server.ChatManager;
import fr.unreal852.localchat.server.ChatTextColor;
import net.minecraft.server.command.ServerCommandSource;

public class EnableDistanceCommand implements Command<ServerCommandSource>
{
    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(LocalChat.getConfig().enableDisableDistanceCommandPermissionLevel))
                return 0;
            if (!ChatManager.GlobalChatEnabled)
            {
                ChatManager.sendFeedback(source, "Global chat is already disabled !", ChatTextColor.Orange, false);
                return 0;
            }
            ChatManager.GlobalChatEnabled = false;
            ChatManager.broadCastMessage(source.getServer(), source.getChatMessageSender(), "Global chat disabled !", ChatTextColor.Red);
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}