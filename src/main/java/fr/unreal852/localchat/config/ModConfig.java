package fr.unreal852.localchat.config;

import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.conversion.SpecIntInRange;
import fr.unreal852.localchat.utils.ConfigUtils.Comment;
import fr.unreal852.localchat.utils.ConfigUtils.Ignore;

public class ModConfig
{
    @Ignore
    public static final int CURRENT_VERSION = 1;

    @Path("General.ChatRange")
    @Comment("The range in blocks a player can talk and be heard. | Type: Integer | Default: 50")
    @SpecIntInRange(min = 0, max = 1000000)
    public final int         chatRange                          = 50;
    @Path("General.WhisperRange")
    @Comment("The range in blocks a player can whisper and be whispered. | Type: Integer | Default: 5")
    @SpecIntInRange(min = 0, max = 1000000)
    public final int         whisperRange                       = 5;
    @Path("General.RangeBypassPermissionLevel")
    @Comment("The required permission level to see messages from any distance. | Type: Integer | Default: 4")
    public final int         rangeByPassPermissionLevel         = 4;
    @Path("Confuse.Enabled")
    @Comment("Enable or disable message confusion. | Type: Boolean | Default: true")
    public final boolean     confuseEnabled                     = true;
    @Path("Confuse.Range")
    @Comment("The range in blocks when a message start being confused. | Type: Integer | Default: 30")
    public final int         confuseRange                       = 30;
    @Path("Confuse.Mode")
    @Comment("The confuser mode. | Type: Enum(ReplaceChars,RemoveChars) | Default: RemoveChars")
    public final ConfuseMode confuseMode;
    @Path("Confuse.Character")
    @Comment("The character to use when confusing messages with mode ReplaceChars. | Type: String | Default: @")
    public final String      confuseChar                        = "@";
    @Path("CustomFormat.Enabled")
    @Comment("Enable or disable custom message format. | Type: Boolean | Default: false")
    public final boolean     customFormatEnabled                = false;
    @Path("CustomFormat.Format")
    @Comment("The message format to use when displaying messages. | Type: String | Default: [{distance}B] {sender} > {message}")
    public final String      customFormatFormat                 = "[{distance}B] {sender} > {message}";
    @Path("Commands.ReloadConfigPermissionLevel")
    @Comment("The permission level required to use the reload config command. | Type: Integer | Default: 4")
    public final int         commandReloadConfigPermissionLevel = 4;
    @Path("Commands.ShoutPermissionLevel")
    @Comment("The permission level required to use the shout command. | Type: Integer | Default: 4")
    public final int         commandShoutPermissionLevel        = 4;
    @Path("Version")
    @Comment("DO NOT TOUCH!")
    public final int         configVersion                      = CURRENT_VERSION;

    public ModConfig()
    {
        this.confuseMode = ConfuseMode.RemoveChars;
    }
}
