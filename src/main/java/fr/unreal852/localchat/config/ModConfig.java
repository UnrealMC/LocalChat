package fr.unreal852.localchat.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.unreal852.localchat.LocalChat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

// Todo: Use a config library or make my own using gson.

public class ModConfig
{
    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

    public final String         __Comment;
    public final GeneralConfig  general;
    public final ConfuseConfig  confuseMessages;
    public final FormatConfig   customMessagesFormat;
    public final CommandsConfig commands;
    public final ConfigVersion  version;

    public ModConfig()
    {
        this(
                new GeneralConfig(50, 5, 1),
                new ConfuseConfig(true, 40, '@'),
                new FormatConfig(false, "[{distance}B] {sender} > {message}"),
                new CommandsConfig(1, 1),
                new ConfigVersion());
    }

    public ModConfig(GeneralConfig general,
                     ConfuseConfig confuseMessages,
                     FormatConfig customMessagesFormat,
                     CommandsConfig commands,
                     ConfigVersion version)
    {
        __Comment = "This is the config file for the LocalChat mod.";
        this.general = general;
        this.confuseMessages = confuseMessages;
        this.customMessagesFormat = customMessagesFormat;
        this.commands = commands;
        this.version = version;
    }

    /**
     * Read config from file.
     * If the file does not exist, a new config file is created and returned.
     * If the file exist but can not be read, a new config file is created and returned.
     * If the file exist but has a different version, the config file is re-written with the new format but the original changes are kept.
     *
     * @param file The config file to read.
     * @return The config.
     */
    public static ModConfig readConfig(Path file)
    {
        try
        {
            if (!Files.exists(file))
                return writeConfigAndReturn(file, new ModConfig());
            ModConfig config = GSON.fromJson(Files.readString(file), ModConfig.class);
            if (config == null)
                return writeConfigAndReturn(file, new ModConfig());
            if (config.version.getVersion() == 1)
                return config;
            return writeConfigAndReturn(file, config);
        }
        catch (IOException ex)
        {
            LocalChat.LOGGER.error("Failed to read config (" + file + ")", ex);
            return new ModConfig();
        }
    }

    /**
     * Write config to file.
     *
     * @param file   The file to write the config to.
     * @param config The config to write to file.
     */
    public static void writeConfig(Path file, ModConfig config)
    {
        if (config == null)
        {
            LocalChat.LOGGER.error("The provided LocalChatConfig is null");
            return;
        }
        try
        {
            if (!Files.exists(file) && file.getParent() != null)
                Files.createDirectories(file.getParent());
            config.version.setVersion(ConfigVersion.CURRENT_CONFIG_VERSION);
            Files.writeString(file, GSON.toJson(config));
        }
        catch (IOException ex)
        {
            LocalChat.LOGGER.error("Failed to write config", ex);
        }
    }

    /**
     * Write config to file and return it.
     *
     * @param file   The file to write the config to.
     * @param config The config to write to file.
     * @return The config.
     */
    public static ModConfig writeConfigAndReturn(Path file, ModConfig config)
    {
        writeConfig(file, config);
        return config;
    }
}
