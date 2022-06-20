package fr.unreal852.localchat.config;

public class ConfuseConfig
{
    public final boolean enabled;
    public final int distance;
    public final char character;

    public ConfuseConfig(boolean enabled, int distance, char character)
    {
        this.enabled = enabled;
        this.distance = distance;
        this.character = character;
    }
}
