package fr.unreal852.proximitytextchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.proximitytextchat.ProximityTextChat;
import fr.unreal852.proximitytextchat.server.ChatManager;
import fr.unreal852.proximitytextchat.server.ChatTextColor;
import net.minecraft.server.command.ServerCommandSource;

public class EnableDistanceCommand implements Command<ServerCommandSource>
{
    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(ProximityTextChat.getConfig().enableDisableDistanceCommandPermissionLevel))
                return 0;
            if (!ChatManager.GlobalChatEnabled)
            {
                ChatManager.sendFeedback(source, "Global chat is already disabled !", ChatTextColor.Orange, false);
                return 0;
            }
            ChatManager.GlobalChatEnabled = false;
            ChatManager.broadCastMessage(source.getServer(), source, "Global chat disabled !", ChatTextColor.Red);
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}