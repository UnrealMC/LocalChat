package fr.unreal852.localchat.config;

import fr.unreal852.simpleconfig.attributes.Comment;
import fr.unreal852.simpleconfig.attributes.Ignore;
import fr.unreal852.simpleconfig.attributes.Name;

public class ModConfig
{
    @Ignore
    public static final int CURRENT_VERSION = 1;

    @Name(group = "General", value = "ChatRange")
    @Comment("The range in blocks a player can talk and be heard. | Type: Integer | Default: 50")
    public int         chatRange                                   = 50;
    @Name(group = "General", value = "WhisperRange")
    @Comment("The range in blocks a player can whisper and be whispered. | Type: Integer | Default: 5")
    public int         whisperRange                                = 5;
    @Name(group = "General", value = "RangeBypassPermissionLevel")
    @Comment("The required permission level to see messages from any distance. | Type: Integer | Default: 4")
    public int         rangeByPassPermissionLevel                  = 4;
    @Name(group = "Confuse", value = "Enabled")
    @Comment("Enable or disable message confusion. | Type: Boolean | Default: true")
    public boolean     confuseEnabled                              = true;
    @Name(group = "Confuse", value = "Range")
    @Comment("The range in blocks when a message start being confused. | Type: Integer | Default: 30")
    public int         confuseRange                                = 30;
    @Name(group = "Confuse", value = "Mode")
    @Comment("The confuser mode. | Type: Enum(DeleteCharacters, ReplaceCharacters, ShuffleCharacters) | Default: DeleteCharacters")
    public ConfuseMode confuseMode                                 = ConfuseMode.DeleteCharacters;
    @Name(group = "Confuse", value = "Character")
    @Comment("The characters to use when confusing messages with mode ReplaceChars. | Type: String | Default: @#$")
    public String      confuseCharacters                           = "@#$";
    @Name(group = "Format", value = "Enabled")
    @Comment("Enable or disable custom message format. | Type: Boolean | Default: false")
    public boolean     customFormatEnabled                         = false;
    @Name(group = "Format", value = "Format")
    @Comment("The message format to use when displaying messages. | Type: String | Default: [{distance}B] {sender} > {message}")
    public String      customFormatFormat                          = "[{distance}B] {sender} > {message}";
    @Name(group = "Commands", value = "ReloadConfigPermissionLevel")
    @Comment("The permission level required to use the reload config command. | Type: Integer | Default: 4")
    public int         commandReloadConfigPermissionLevel          = 4;
    @Name(group = "Commands", value = "ShoutPermissionLevel")
    @Comment("The permission level required to use the shout command. | Type: Integer | Default: 4")
    public int         commandShoutPermissionLevel                 = 4;
    @Name(group = "Commands", value = "EnableDisableDistanceCommand")
    @Comment("The permission level required to use the enable/disable distance command. | Type: Integer | Default: 4")
    public int         enableDisableDistanceCommandPermissionLevel = 4;

    @Name("Version")
    @Comment("DO NOT TOUCH!")
    public int configVersion = CURRENT_VERSION;

    public ModConfig()
    {
    }
}
