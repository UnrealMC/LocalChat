package fr.unreal852.proximitytextchat.server;

import com.mojang.brigadier.arguments.StringArgumentType;
import fr.unreal852.proximitytextchat.server.commands.DisableDistanceCommand;
import fr.unreal852.proximitytextchat.server.commands.EnableDistanceCommand;
import fr.unreal852.proximitytextchat.server.commands.ReloadConfigCommand;
import fr.unreal852.proximitytextchat.server.commands.ShoutCommand;
import fr.unreal852.proximitytextchat.server.listeners.ChatMessageListener;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.server.command.CommandManager;

@Environment(EnvType.SERVER)
public class ProximityTextChatServer implements DedicatedServerModInitializer
{
    @Override
    public void onInitializeServer()
    {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) ->
        {
            dispatcher.register(CommandManager.literal("shout").then(CommandManager.argument("message", StringArgumentType.greedyString()).executes(new ShoutCommand())));
            dispatcher.register(CommandManager.literal("lchat")
                    .then(CommandManager.literal("config")
                            .then(CommandManager.literal("reload").executes(new ReloadConfigCommand()))));
            dispatcher.register(CommandManager.literal("lchat").then(CommandManager.literal("enable").executes(new EnableDistanceCommand())));
            dispatcher.register(CommandManager.literal("lchat").then(CommandManager.literal("disable").executes(new DisableDistanceCommand())));
        }));

        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(new ChatMessageListener());
    }
}
