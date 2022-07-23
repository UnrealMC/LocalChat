package fr.unreal852.localchat.config;

import fr.unreal852.simpleconfig.attributes.ConfigComment;
import fr.unreal852.simpleconfig.attributes.ConfigIgnore;
import fr.unreal852.simpleconfig.attributes.ConfigValue;
import fr.unreal852.simpleconfig.attributes.ConfigValueNamingPolicy;
import fr.unreal852.simpleconfig.naming.NamingPolicy;

@ConfigValueNamingPolicy(NamingPolicy.UpperCamelCase)
public class ModConfig
{
    @ConfigIgnore
    public static final int CURRENT_VERSION = 1;

    @ConfigValue(group = "General")
    @ConfigComment({"The range in blocks a player can talk and be heard.", "Type: Integer", "Default: 50"})
    public int         chatRange                                   = 50;
    @ConfigValue(group = "General")
    @ConfigComment({"The range in blocks a player can whisper and be whispered.", "Type: Integer", "Default: 5"})
    public int         whisperRange                                = 5;
    @ConfigValue(group = "General")
    @ConfigComment({"The required permission level to see messages from any distance.", "Type: Integer", "Default: 4"})
    public int         rangeByPassPermissionLevel                  = 4;
    @ConfigValue(group = "Confuse", value = "Enabled")
    @ConfigComment({"Enable or disable message confusion.", "Type: Boolean", "Default: true"})
    public boolean     confuseEnabled                              = true;
    @ConfigValue(group = "Confuse", value = "Range")
    @ConfigComment({"The range in blocks when a message start being confused.", "Type: Integer", "Default: 30"})
    public int         confuseRange                                = 30;
    @ConfigValue(group = "Confuse", value = "Mode")
    @ConfigComment({"The confuser mode.", "Types: DeleteCharacters, ReplaceCharacters, ShuffleCharacters", "Default: DeleteCharacters"})
    public ConfuseMode confuseMode                                 = ConfuseMode.DeleteCharacters;
    @ConfigValue(group = "Confuse", value = "Character")
    @ConfigComment({"The characters to use when confusing messages with mode ReplaceChars.", "Type: String", "Default: @#$"})
    public String      confuseCharacters                           = "@#$";
    @ConfigValue(group = "Format", value = "Enabled")
    @ConfigComment({"Enable or disable custom message format.", "Type: Boolean", "Default: false"})
    public boolean     customFormatEnabled                         = false;
    @ConfigValue(group = "Format", value = "Format")
    @ConfigComment({"The message format to use when displaying messages.", "Type: String", "Default: [{distance}B] {sender} > {message}"})
    public String      customFormatFormat                          = "[{distance}B] {sender} > {message}";
    @ConfigValue(group = "Commands", value = "ReloadConfigPermissionLevel")
    @ConfigComment({"The permission level required to use the reload config command.,", "Type: Integer", "Default: 4"})
    public int         commandReloadConfigPermissionLevel          = 4;
    @ConfigValue(group = "Commands", value = "ShoutPermissionLevel")
    @ConfigComment({"The permission level required to use the shout command.", "Type: Integer", "Default: 4"})
    public int         commandShoutPermissionLevel                 = 4;
    @ConfigValue(group = "Commands", value = "DisableDistancePermissionLevel")
    @ConfigComment({"The permission level required to use the enable/disable distance command.", "Type: Integer", "Default: 4"})
    public int         enableDisableDistanceCommandPermissionLevel = 4;

    @ConfigValue("Version")
    @ConfigComment("DO NOT TOUCH")
    public int configVersion = CURRENT_VERSION;

    public ModConfig()
    {
    }
}
