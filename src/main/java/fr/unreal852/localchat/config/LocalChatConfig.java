package fr.unreal852.localchat.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.unreal852.localchat.LocalChat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalChatConfig
{
    private static final Gson GSON = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .create();

    public final String                  __Comment;
    public final LocalChatGeneralConfig  general;
    public final LocalChatConfuseConfig  confuseMessages;
    public final LocalChatFormatConfig   customMessagesFormat;
    public final LocalChatCommandsConfig commands;

    public LocalChatConfig()
    {
        this(
                new LocalChatGeneralConfig(50, 5, 1),
                new LocalChatConfuseConfig(true, 40, '@'),
                new LocalChatFormatConfig(false, "[{distance}B] {sender} > {message}"),
                new LocalChatCommandsConfig(1, 1));
    }

    public LocalChatConfig(LocalChatGeneralConfig general, LocalChatConfuseConfig confuseMessages, LocalChatFormatConfig customMessagesFormat, LocalChatCommandsConfig commands)
    {
        __Comment = "This config file may change at anytime and you may loose your settings. Please read patch notes before updating.";
        this.general = general;
        this.confuseMessages = confuseMessages;
        this.customMessagesFormat = customMessagesFormat;
        this.commands = commands;
    }

    public static LocalChatConfig readConfig(Path file)
    {
        if (!Files.exists(file))
        {
            LocalChatConfig config = new LocalChatConfig();
            writeConfig(file, config);
            return config;
        }
        try
        {
            return GSON.fromJson(Files.readString(file), LocalChatConfig.class);
        }
        catch (IOException ex)
        {
            LocalChat.LOGGER.error("Failed to read config (" + file + ")", ex);
        }
        return new LocalChatConfig();
    }

    public static void writeConfig(Path file, LocalChatConfig chatConfig)
    {
        if (chatConfig == null)
        {
            LocalChat.LOGGER.error("The provided LocalChatConfig is null");
            return;
        }
        try
        {
            if (!Files.exists(file))
                Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(chatConfig));
        }
        catch (IOException ex)
        {
            LocalChat.LOGGER.error("Failed to write config", ex);
        }
    }
}
