package com.musiccontroller.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musiccontroller.MusicFrequencyController;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("music-frequency-controller.json");
    
    private static MusicConfig config = new MusicConfig();
    
    public static void loadConfig() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                config = GSON.fromJson(json, MusicConfig.class);
                MusicFrequencyController.LOGGER.info("Loaded music frequency config: {}x", config.musicFrequencyMultiplier);
            } catch (IOException e) {
                MusicFrequencyController.LOGGER.error("Failed to load config, using defaults", e);
                config = new MusicConfig();
            }
        } else {
            MusicFrequencyController.LOGGER.info("No config file found, creating default config");
            saveConfig();
        }
        
        // Validate config values
        if (config.musicFrequencyMultiplier < MusicConfig.MIN_FREQUENCY || 
            config.musicFrequencyMultiplier > MusicConfig.MAX_FREQUENCY) {
            MusicFrequencyController.LOGGER.warn("Invalid frequency value {}, resetting to default", config.musicFrequencyMultiplier);
            config.musicFrequencyMultiplier = 1.0f;
            saveConfig();
        }
    }
    
    public static void saveConfig() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String json = GSON.toJson(config);
            Files.writeString(CONFIG_PATH, json);
            MusicFrequencyController.LOGGER.debug("Saved music frequency config: {}x", config.musicFrequencyMultiplier);
        } catch (IOException e) {
            MusicFrequencyController.LOGGER.error("Failed to save config", e);
        }
    }
    
    public static MusicConfig getConfig() {
        return config;
    }
    
    public static float getMusicFrequencyMultiplier() {
        return config.musicFrequencyMultiplier;
    }
    
    public static void setMusicFrequencyMultiplier(float multiplier) {
        config.musicFrequencyMultiplier = Math.max(MusicConfig.MIN_FREQUENCY, 
                                                  Math.min(MusicConfig.MAX_FREQUENCY, multiplier));
        saveConfig();
    }
    
    public static void increaseMusicFrequency() {
        setMusicFrequencyMultiplier(config.musicFrequencyMultiplier + config.frequencyStep);
    }
    
    public static void decreaseMusicFrequency() {
        setMusicFrequencyMultiplier(config.musicFrequencyMultiplier - config.frequencyStep);
    }
    
    public static void resetMusicFrequency() {
        setMusicFrequencyMultiplier(1.0f);
    }
    
    public static String getMusicFrequencyDisplay() {
        float freq = config.musicFrequencyMultiplier;
        if (freq <= 0.11f) {
            return "Vanilla (Slowest)";
        } else if (freq >= 9.9f) {
            return "Continuous (Fastest)";
        } else {
            return String.format("%.1fx", freq);
        }
    }
    
    public static float getDelayMultiplier() {
        return 1.0f / config.musicFrequencyMultiplier;
    }
}