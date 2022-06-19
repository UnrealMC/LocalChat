package fr.unreal852.localchat.server.listeners;

import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.server.ChatManager;
import fr.unreal852.localchat.utils.StringConfuser;
import fr.unreal852.localchat.utils.StringSubstitutor;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageSignature;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChatMessageListener implements ServerMessageEvents.AllowChatMessage
{
    @Override
    public boolean allowChatMessage(FilteredMessage<SignedMessage> message, ServerPlayerEntity sender, RegistryKey<MessageType> typeKey)
    {
        MinecraftServer server = sender.getServer();
        if (server == null)
            return false;
        List<ServerPlayerEntity> players = sender.getWorld().getPlayers();
        MessageSender messageSender = new MessageSender(sender.getUuid(), sender.getDisplayName());
        SignedMessage signedMessage = message.filteredOrElse(message.raw());

        server.sendMessage(signedMessage.signedContent());

        for (ServerPlayerEntity player : players)
            ChatManager.sendMessage(player, sender, messageSender, signedMessage, typeKey);

        return false;
    }
}
