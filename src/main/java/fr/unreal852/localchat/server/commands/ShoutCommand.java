package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.network.message.MessageSignature;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.time.Instant;
import java.util.Optional;

public class ShoutCommand implements Command<ServerCommandSource>
{

    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(1))
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
