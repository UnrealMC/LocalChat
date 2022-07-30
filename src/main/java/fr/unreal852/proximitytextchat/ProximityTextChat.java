package fr.unreal852.proximitytextchat;

import fr.unreal852.proximitytextchat.config.ModConfig;
import fr.unreal852.simpleconfig.configuration.Configomator;
import fr.unreal852.simpleconfig.configuration.impl.ObjectConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class ProximityTextChat implements ModInitializer
{
    public static final  Logger                  LOGGER        = LogManager.getLogger();
    private static final String                  CONFIG_FILE   = "localchat.toml";
    private static final ObjectConfig<ModConfig> OBJECT_CONFIG = new ObjectConfig<>(ModConfig.class);

    /**
     * Load the mod configuration. Can also be used to reload config.
     */
    public static void LoadConfig()
    {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE);
        if (!Configomator.loadConfigFromFile(OBJECT_CONFIG, configFile, true))
        {
            LOGGER.error("Failed to load config");
        }
    }

    public static ModConfig getConfig()
    {
        return OBJECT_CONFIG.getInstance();
    }

    @Override
    public void onInitialize()
    {
        LoadConfig();
    }
}
