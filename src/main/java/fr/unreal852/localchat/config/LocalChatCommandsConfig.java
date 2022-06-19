package fr.unreal852.localchat.config;

public class LocalChatCommandsConfig
{
    public final int reloadConfigPermissionLevel;
    public final int shoutPermissionLevel;

    public LocalChatCommandsConfig(int reloadConfigPermissionLevel, int shoutPermissionLevel)
    {
        this.reloadConfigPermissionLevel = reloadConfigPermissionLevel;
        this.shoutPermissionLevel = shoutPermissionLevel;
    }
}
