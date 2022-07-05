package fr.unreal852.localchat.server;

import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.utils.StringConfuser;
import fr.unreal852.localchat.utils.StringSubstitutor;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;

import java.util.Map;

public final class ChatManager
{
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
        double distance = Math.sqrt(player.squaredDistanceTo(sender));
        if (distance > LocalChat.CONFIG.chatRange && !player.hasPermissionLevel(LocalChat.CONFIG.rangeByPassPermissionLevel))
            return;
        if (!LocalChat.CONFIG.customFormatEnabled && !LocalChat.CONFIG.confuseEnabled)
            player.sendChatMessage(message, messageSender, messageType);
        else
        {
            String messageContent = message.signedContent().getString();
            String senderName = sender.getDisplayName().getString();
            if (LocalChat.CONFIG.confuseEnabled)
            {
                if (distance >= LocalChat.CONFIG.confuseRange && !player.hasPermissionLevel(LocalChat.CONFIG.rangeByPassPermissionLevel))
                {
                    double confuseRange = LocalChat.CONFIG.chatRange - LocalChat.CONFIG.confuseRange;
                    double confuseDistancePercent = (distance - LocalChat.CONFIG.confuseRange) * 100 / confuseRange;
                    switch (LocalChat.CONFIG.confuseMode)
                    {
                        case DeleteCharacters ->
                                messageContent = StringConfuser.ConfuseRemove(messageContent, (int) confuseDistancePercent);
                        case ReplaceCharacters ->
                                messageContent = StringConfuser.ConfuseReplace(messageContent, (int) confuseDistancePercent, LocalChat.CONFIG.confuseCharacters);
                        case ShuffleCharacters ->
                                messageContent = StringConfuser.ConfuseShuffle(messageContent, (int) confuseDistancePercent);
                    }
                }
            }
            if (LocalChat.CONFIG.customFormatEnabled)
            {
                Map<String, Object> map = StringSubstitutor.createNewMap();
                map.put("distance", (int) distance);
                map.put("sender", senderName);
                map.put("message", messageContent);
                messageContent = StringSubstitutor.replace(LocalChat.CONFIG.customFormatFormat, map);
            }
            else
                messageContent = "<" + senderName + "> " + messageContent; // If we don't use custom format, we mimic the default minecraft format.
            ClickEvent messageClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + senderName + " "); // Players should be able to click on name to whisper someone.
            Text messageText = Text.literal(messageContent).setStyle(Style.EMPTY.withClickEvent(messageClickEvent));
            player.sendMessage(messageText);
        }
    }
}
