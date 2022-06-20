package fr.unreal852.localchat.server.listeners;

import fr.unreal852.localchat.server.ChatManager;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.filter.FilteredMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;

import java.util.List;

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
        
        server.sendMessage(Text.literal("<" + messageSender.name().getString() + "> ").append(signedMessage.signedContent()));

        for (ServerPlayerEntity player : players)
            ChatManager.sendMessage(player, sender, messageSender, signedMessage, typeKey);

        return false;
    }
}
