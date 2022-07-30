package fr.unreal852.proximitytextchat.server;

import fr.unreal852.proximitytextchat.ProximityTextChat;
import fr.unreal852.proximitytextchat.config.ModConfig;
import fr.unreal852.proximitytextchat.utils.StringConfuser;
import fr.unreal852.proximitytextchat.utils.StringSubstitutor;
import net.minecraft.network.message.DecoratedContents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.registry.Registry;

import java.util.Map;

public final class ChatManager
{
    public static boolean GlobalChatEnabled = false;

    /**
     * Send a chat message to the specified player only if they are within the configured distance.
     *
     * @param sender  The player who sent the message.
     * @param message The message.
     */

    public static void sendMessage(SignedMessage message, ServerPlayerEntity sender, ServerPlayerEntity receiver, MessageType.Parameters params)
    {
        if (receiver == sender)
        {
            receiver.sendChatMessage(SentMessage.of(message), false, params);
            ProximityTextChat.LOGGER.info("dqzdqzddz");
            return;
        }
        ModConfig config = ProximityTextChat.getConfig();
        double distance = Math.sqrt(receiver.squaredDistanceTo(sender));
        if (distance > config.chatRange && !receiver.hasPermissionLevel(config.rangeByPassPermissionLevel))
            return;
        if (!config.customFormatEnabled && !config.confuseEnabled)
            receiver.sendChatMessage(SentMessage.of(message), false, params);
        else
        {

            String messageContent = message.getContent().getString();
            String senderName = sender.getDisplayName().getString();
            if (config.confuseEnabled)
            {
                if (distance >= config.confuseRange && !receiver.hasPermissionLevel(config.rangeByPassPermissionLevel))
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
            receiver.sendMessage(messageText);
        }
    }

    public static void broadCastMessage(MinecraftServer server, ServerPlayerEntity sender, String message)
    {
        broadCastMessage(server, sender.getCommandSource(), message, ChatTextColor.White.getColor());
    }

    public static void broadCastMessage(MinecraftServer server, ServerPlayerEntity sender, String message, ChatTextColor messageColor)
    {
        broadCastMessage(server, sender.getCommandSource(), message, messageColor.getColor());
    }

    public static void broadCastMessage(MinecraftServer server, ServerPlayerEntity sender, String message, TextColor messageColor)
    {
        broadCastMessage(server, sender.getCommandSource(), message, messageColor);
    }

    public static void broadCastMessage(MinecraftServer server, ServerCommandSource sender, String message)
    {
        broadCastMessage(server, sender, message, ChatTextColor.White.getColor());
    }

    public static void broadCastMessage(MinecraftServer server, ServerCommandSource sender, String message, ChatTextColor messageColor)
    {
        broadCastMessage(server, sender, message, messageColor.getColor());
    }

    public static void broadCastMessage(MinecraftServer server, ServerCommandSource sender, String message, TextColor messageColor)
    {
        MutableText messageText = Text.literal(message);
        Style style = messageText.getStyle().withColor(messageColor);
        messageText.fillStyle(style);
        SignedMessage signedMessage = SignedMessage.ofUnsigned(new DecoratedContents(message, messageText));
        Registry<MessageType> registryKey = server.getRegistryManager().get(Registry.MESSAGE_TYPE_KEY);
        MessageType.Parameters params = new MessageType.Parameters(registryKey.get(MessageType.CHAT.getRegistry()), messageText, null);
        server.getPlayerManager().broadcast(signedMessage, sender, params);
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
