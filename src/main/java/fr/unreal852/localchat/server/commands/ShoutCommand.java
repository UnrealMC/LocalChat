package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.localchat.LocalChat;
import net.minecraft.network.message.MessageSignature;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.time.Instant;
import java.util.Optional;

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
            if (!source.hasPermissionLevel(LocalChat.getConfig().commandShoutPermissionLevel))
                return 0;
            String message = StringArgumentType.getString(context, "message");
            Text messageText = Text.literal(message);
            MessageSignature messageSignature = new MessageSignature(source.getChatMessageSender().uuid(), Instant.now(), MessageSignature.none().saltSignature());
            SignedMessage signedMessage = new SignedMessage(messageText, messageSignature, Optional.empty());
            source.getServer().getPlayerManager().broadcast(signedMessage, source.getChatMessageSender(), MessageType.CHAT);
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}
