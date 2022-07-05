package fr.unreal852.localchat;

import fr.unreal852.localchat.config.ModConfig;
import fr.unreal852.localchat.utils.ConfigUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class LocalChat implements ModInitializer
{
    public static final  Logger    LOGGER      = LogManager.getLogger();
    private static final String    CONFIG_FILE = "localchat.json";
    public static        ModConfig CONFIG;

    /**
     * Load the mod configuration. Can also be used to reload config.
     */
    public static void LoadConfig()
    {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE);
        CONFIG = ConfigUtils.loadConfig(ModConfig.class, configFile);
    }

    @Override
    public void onInitialize()
    {
        LoadConfig();
    }
}
