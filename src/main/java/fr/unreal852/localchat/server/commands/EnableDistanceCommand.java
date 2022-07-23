package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.server.ChatManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

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
                source.sendFeedback(Text.literal("Global chat is already disabled !"), false);
                return 0;
            }
            ChatManager.GlobalChatEnabled = false;
            ChatManager.broadCastMessage("Global chat disabled !", source.getChatMessageSender(), source.getServer());
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}