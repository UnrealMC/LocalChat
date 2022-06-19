package fr.unreal852.localchat.config;

public class LocalChatGeneralConfig
{
    public final double chatRange;
    public final double whisperRange;
    public final int    rangeByPassPermissionLevel;

    public LocalChatGeneralConfig(double chatRange, double whisperRange, int permissionLevelByPass)
    {
        this.chatRange = chatRange;
        this.whisperRange = whisperRange;
        this.rangeByPassPermissionLevel = permissionLevelByPass;
    }
}
