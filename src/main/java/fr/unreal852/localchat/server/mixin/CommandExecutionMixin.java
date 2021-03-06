package fr.unreal852.localchat.server.mixin;

import fr.unreal852.localchat.LocalChat;
import fr.unreal852.localchat.config.ModConfig;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class CommandExecutionMixin
{
    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "onCommandExecution", cancellable = true)
    private void onCommandExecution(CommandExecutionC2SPacket packet, CallbackInfo ci)
    {
        // Todo: The sender should always see his message, right now we just cancel the command and the sender doesn't see anything.
        String command = packet.command();
        if (command.startsWith("tell") || command.startsWith("w") || command.startsWith("msg"))
        {
            if (player.getServer() == null)
                return;
            String[] split = command.split(" ");
            if (split.length < 2 || split[1].isEmpty())
                return;
            ServerPlayerEntity target = player.getServer().getPlayerManager().getPlayer(split[1]);
            if (target == null)
            {
                ci.cancel(); // Should not be needed.
                return;
            }
            ModConfig config = LocalChat.getConfig();
            double distance = Math.sqrt(target.squaredDistanceTo(player));
            if (distance > config.whisperRange && !player.hasPermissionLevel(config.rangeByPassPermissionLevel))
                ci.cancel();
        }
    }
}