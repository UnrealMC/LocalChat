package fr.unreal852.localchat.server.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.unreal852.localchat.LocalChat;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

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
            if (!source.hasPermissionLevel(LocalChat.CONFIG.commandReloadConfigPermissionLevel))
                return 0;
            LocalChat.LoadConfig();
            if (source.getPlayer() != null)
                source.getPlayer().sendMessage(Text.literal("Config Reloaded"), false);
            return Command.SINGLE_SUCCESS;
        }
        return 0;
    }
}
