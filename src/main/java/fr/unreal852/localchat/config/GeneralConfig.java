package fr.unreal852.localchat.config;

public class GeneralConfig
{
    public final double range;
    public final double whisperRange;
    public final int    rangeByPassPermissionLevel;

    public GeneralConfig(double range, double whisperRange, int permissionLevelByPass)
    {
        this.range = range;
        this.whisperRange = whisperRange;
        this.rangeByPassPermissionLevel = permissionLevelByPass;
    }
}
