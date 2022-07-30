package fr.unreal852.proximitytextchat.server.listeners;

import fr.unreal852.proximitytextchat.server.ChatManager;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

public class ChatMessageListener implements ServerMessageEvents.AllowChatMessage
{

    @Override
    public boolean allowChatMessage(SignedMessage message, ServerPlayerEntity sender, MessageType.Parameters params)
    {
        if (ChatManager.GlobalChatEnabled)
            return true;
        MinecraftServer server = sender.getServer();
        if (server == null)
            return false;

        List<ServerPlayerEntity> players = sender.getWorld().getPlayers();
        server.sendMessage(Text.literal("<" + sender.getDisplayName().getString() + "> ").append(message.getContent()));
        for (ServerPlayerEntity player : players)
            ChatManager.sendMessage(message, sender, player, params);
        return false;
    }
}
