package fr.unreal852.localchat.utils;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.conversion.Path;
import com.electronwill.nightconfig.core.file.FileNotFoundAction;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

public class ConfigUtils
{
    private static final ObjectConverter OBJECT_CONVERTER = new ObjectConverter();
    private static final Logger          LOGGER           = LogManager.getLogger();

    static
    {
        Config.setInsertionOrderPreserved(true);
    }

    public static void saveConfig(java.nio.file.Path path, Object obj) throws IllegalArgumentException
    {
        saveConfig(path, toConfig(obj));
    }

    public static void saveConfig(java.nio.file.Path path, CommentedConfig config)
    {
        if (path == null)
        {
            LOGGER.error("Failed to save config, the specified path is null.");
            return;
        }
        if (config == null)
        {
            LOGGER.error("Failed to save config, the specified config is null.");
            return;
        }

        try
        {
            final TomlWriter writer = new TomlWriter();
            writer.write(config.unmodifiable(), path, WritingMode.REPLACE);
        }
        catch (Exception ex)
        {
            LOGGER.error("Failed to save config.", ex);
        }
    }

    public static <T> T loadConfig(Class<T> tClass, final java.nio.file.Path path)
    {
        try
        {
            if (!Files.exists(path))
            {
                LOGGER.warn("The config file doesn't exists. Generating one...");
                final T instance = tClass.getDeclaredConstructor().newInstance();
                saveConfig(path, instance);
                return instance;
            }
            TomlParser parser = new TomlParser();
            return ConfigUtils.toObject(tClass, parser.parse(path, FileNotFoundAction.THROW_ERROR));

        }
        catch (Exception ex)
        {
            LOGGER.error("Failed to load config.", ex);
            return null;
        }
    }

    public static CommentedConfig toConfig(Object obj)
    {
        if (obj == null)
        {
            LOGGER.error("The specified object is null.");
            return TomlFormat.newConfig();
        }
        CommentedConfig config = TomlFormat.newConfig();
        OBJECT_CONVERTER.toConfig(obj, config);
        config.remove("CURRENT_VERSION");
        applyComments(config, obj);
        return config;
    }

    public static <T> T toObject(Class<T> tClass, CommentedConfig config) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        T instance = tClass.getDeclaredConstructor().newInstance();
        if (config == null)
        {
            LOGGER.error("The specified config is null.");
            return instance;
        }
        OBJECT_CONVERTER.toObject(config, instance);
        return instance;
    }

    private static void applyComments(final CommentedConfig config, final Object object)
    {
        for (final Field field : object.getClass().getDeclaredFields())
        {
            if (field.isAnnotationPresent(Path.class) && field.isAnnotationPresent(Comment.class))
                config.setComment(field.getAnnotation(Path.class).value(), field.getAnnotation(Comment.class).value());
        }
    }

    private static void removeIgnored(final CommentedConfig config, final Object object)
    {
        for (final Field field : object.getClass().getDeclaredFields())
        {
            if (field.isAnnotationPresent(Path.class) && field.isAnnotationPresent(Ignore.class))
                config.remove(field.getAnnotation(Path.class).value());
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Comment
    {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Ignore
    {

    }
}
