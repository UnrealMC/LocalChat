package fr.unreal852.proximitytextchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.proximitytextchat.ProximityTextChat;
import fr.unreal852.proximitytextchat.server.ChatManager;
import fr.unreal852.proximitytextchat.server.ChatTextColor;
import net.minecraft.server.command.ServerCommandSource;

/**
 * Represents the reload config command.
 */
public class ReloadConfigCommand implements Command<ServerCommandSource>
{

    @Override
    public int run(CommandContext context)
    {
        if (context.getSource() instanceof ServerCommandSource source)
        {
            if (!source.hasPermissionLevel(ProximityTextChat.getConfig().commandReloadConfigPermissionLevel))
                return 0;
            ProximityTextChat.LoadConfig();
            ChatManager.sendFeedback(source, "Configuration reloaded !", ChatTextColor.Green, false);
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}
