package fr.unreal852.proximitytextchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.proximitytextchat.ProximityTextChat;
import fr.unreal852.proximitytextchat.server.ChatManager;
import net.minecraft.server.command.ServerCommandSource;

/**
 * Represent the shout command.
 * The shout command broadcast a message to the whole server, bypassing the distance.
 */
public class ShoutCommand implements Command<ServerCommandSource>
{
    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(ProximityTextChat.getConfig().commandShoutPermissionLevel))
                return 0;
            String stringMessage = StringArgumentType.getString(context, "message");
            ChatManager.broadCastMessage(source.getServer(), source, stringMessage);
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}
