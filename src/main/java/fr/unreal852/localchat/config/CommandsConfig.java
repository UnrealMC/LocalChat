package fr.unreal852.localchat.config;

public class CommandsConfig
{
    public final int reloadConfigPermissionLevel;
    public final int shoutPermissionLevel;

    public CommandsConfig(int reloadConfigPermissionLevel, int shoutPermissionLevel)
    {
        this.reloadConfigPermissionLevel = reloadConfigPermissionLevel;
        this.shoutPermissionLevel = shoutPermissionLevel;
    }
}
