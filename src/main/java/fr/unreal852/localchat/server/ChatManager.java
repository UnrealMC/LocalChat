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
    public static void sendMessage(ServerPlayerEntity target, ServerPlayerEntity sender, MessageSender messageSender, SignedMessage message, RegistryKey<MessageType> messageType)
    {
        if (target == sender)
        {
            target.sendChatMessage(message, messageSender, messageType);
            return;
        }
        double distance = Math.sqrt(target.squaredDistanceTo(sender));
        if (distance > LocalChat.CONFIG.general.chatRange && !target.hasPermissionLevel(LocalChat.CONFIG.general.rangeByPassPermissionLevel))
            return;
        if (!LocalChat.CONFIG.customMessagesFormat.enabled && !LocalChat.CONFIG.confuseMessages.enabled)
            target.sendChatMessage(message, messageSender, messageType);
        else
        {
            String messageContent = message.signedContent().getString();
            String senderName = sender.getDisplayName().getString();
            if (LocalChat.CONFIG.confuseMessages.enabled)
            {
                double maxDistancePercent = distance * 100 / LocalChat.CONFIG.general.chatRange;
                if (maxDistancePercent >= LocalChat.CONFIG.confuseMessages.distance && !target.hasPermissionLevel(LocalChat.CONFIG.general.rangeByPassPermissionLevel))
                    messageContent = StringConfuser.Confuse(messageContent, (int) maxDistancePercent);
            }
            if (LocalChat.CONFIG.customMessagesFormat.enabled)
            {
                Map<String, Object> map = StringSubstitutor.createNewMap();
                map.put("distance", (int) distance);
                map.put("sender", senderName);
                map.put("message", messageContent);
                messageContent = StringSubstitutor.replace(LocalChat.CONFIG.customMessagesFormat.format, map);
            }
            else
                messageContent = "<" + senderName + "> " + messageContent;
            ClickEvent messageClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + senderName + " ");
            Text messageText = Text.literal(messageContent).setStyle(Style.EMPTY.withClickEvent(messageClickEvent));
            //MessageSignature messageSignature = new MessageSignature(sender.getUuid(), Instant.now(), MessageSignature.none().saltSignature());
            //SignedMessage signedMessage = new SignedMessage(messageText, messageSignature, Optional.empty());
            target.sendMessage(messageText);
        }
    }
}
