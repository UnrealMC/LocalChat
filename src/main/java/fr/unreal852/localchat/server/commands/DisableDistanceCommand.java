package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.server.ChatManager;
import net.minecraft.network.message.MessageSignature;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.time.Instant;
import java.util.Optional;

public class DisableDistanceCommand implements Command<ServerCommandSource>
{
    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(LocalChat.CONFIG.enableDisableDistanceCommandPermissionLevel))
                return 0;
            ChatManager.GlobalChatEnabled = true;
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}