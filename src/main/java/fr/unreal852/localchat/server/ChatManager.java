package fr.unreal852.localchat.server;

import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.config.ModConfig;
import fr.unreal852.localchat.utils.StringConfuser;
import fr.unreal852.localchat.utils.StringSubstitutor;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageSignature;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.registry.RegistryKey;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public final class ChatManager
{
    public static boolean GlobalChatEnabled = false;

    /**
     * Send a chat message to the specified player only if they are within the configured distance.
     *
     * @param player        The player to send the message to.
     * @param sender        The player who sent the message.
     * @param messageSender The message sender infos.
     * @param message       The message.
     * @param messageType   The message type.
     */

    public static void sendMessage(ServerPlayerEntity player, ServerPlayerEntity sender, MessageSender messageSender, SignedMessage message, RegistryKey<MessageType> messageType)
    {
        if (player == sender)
        {
            player.sendChatMessage(message, messageSender, messageType);
            return;
        }
        ModConfig config = LocalChat.getConfig();
        double distance = Math.sqrt(player.squaredDistanceTo(sender));
        if (distance > config.chatRange && !player.hasPermissionLevel(config.rangeByPassPermissionLevel))
            return;
        if (!config.customFormatEnabled && !config.confuseEnabled)
            player.sendChatMessage(message, messageSender, messageType);
        else
        {
            String messageContent = message.signedContent().getString();
            String senderName = sender.getDisplayName().getString();
            if (config.confuseEnabled)
            {
                if (distance >= config.confuseRange && !player.hasPermissionLevel(config.rangeByPassPermissionLevel))
                {
                    double confuseRange = config.chatRange - config.confuseRange;
                    double confuseDistancePercent = (distance - config.confuseRange) * 100 / confuseRange;
                    switch (config.confuseMode)
                    {
                        case DeleteCharacters ->
                                messageContent = StringConfuser.ConfuseRemove(messageContent, (int) confuseDistancePercent);
                        case ReplaceCharacters ->
                                messageContent = StringConfuser.ConfuseReplace(messageContent, (int) confuseDistancePercent, config.confuseCharacters);
                        case ShuffleCharacters ->
                                messageContent = StringConfuser.ConfuseShuffle(messageContent, (int) confuseDistancePercent);
                    }
                }
            }
            if (config.customFormatEnabled)
            {
                Map<String, Object> map = StringSubstitutor.createNewMap();
                map.put("distance", (int) distance);
                map.put("sender", senderName);
                map.put("message", messageContent);
                messageContent = StringSubstitutor.replace(config.customFormatFormat, map);
            }
            else
                messageContent = "<" + senderName + "> " + messageContent; // If we don't use custom format, we mimic the default minecraft format.
            ClickEvent messageClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + senderName + " "); // Players should be able to click on name to whisper someone.
            Text messageText = Text.literal(messageContent).setStyle(Style.EMPTY.withClickEvent(messageClickEvent));
            player.sendMessage(messageText);
        }
    }

    public static void broadCastMessage(MinecraftServer server, MessageSender messageSender, String message)
    {
        broadCastMessage(server, messageSender, message, ChatTextColor.White.getColor());
    }

    public static void broadCastMessage(MinecraftServer server, MessageSender messageSender, String message, ChatTextColor messageColor)
    {
        broadCastMessage(server, messageSender, message, messageColor.getColor());
    }

    public static void broadCastMessage(MinecraftServer server, MessageSender messageSender, String message, TextColor messageColor)
    {
        MutableText messageText = Text.literal(message);
        Style style = messageText.getStyle().withColor(messageColor);
        messageText.fillStyle(style);
        MessageSignature messageSignature = new MessageSignature(messageSender.uuid(), Instant.now(), MessageSignature.none().saltSignature());
        SignedMessage signedMessage = new SignedMessage(messageText, messageSignature, Optional.empty());
        server.getPlayerManager().broadcast(signedMessage, messageSender, MessageType.CHAT);
    }

    public static void sendFeedback(ServerCommandSource serverCommandSource, String message, ChatTextColor messageColor, boolean broadcastToOps)
    {
        sendFeedback(serverCommandSource, message, messageColor.getColor(), broadcastToOps);
    }

    public static void sendFeedback(ServerCommandSource serverCommandSource, String message, TextColor messageColor, boolean broadcastToOps)
    {
        MutableText messageText = Text.literal(message);
        Style style = messageText.getStyle().withColor(messageColor);
        messageText.fillStyle(style);
        serverCommandSource.sendFeedback(messageText, broadcastToOps);
    }
}
