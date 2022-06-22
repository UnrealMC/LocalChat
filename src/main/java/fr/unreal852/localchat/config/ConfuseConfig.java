package fr.unreal852.localchat.config;

public class ConfuseConfig
{
    public final boolean enabled;
    public final double     range;
    public final char    character;

    public ConfuseConfig(boolean enabled, double range, char character)
    {
        this.enabled = enabled;
        this.range = range;
        this.character = character;
    }
}
