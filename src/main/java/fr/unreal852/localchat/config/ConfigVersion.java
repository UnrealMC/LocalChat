package fr.unreal852.localchat.config;

public class ConfigVersion
{
    protected static final int CURRENT_CONFIG_VERSION = 1;

    public final String __comment;
    public       int    version;

    public ConfigVersion()
    {
        this("DO NOT TOUCH", CURRENT_CONFIG_VERSION);
    }

    public ConfigVersion(String __comment, int version)
    {
        this.__comment = __comment;
        this.version = version;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }
}
