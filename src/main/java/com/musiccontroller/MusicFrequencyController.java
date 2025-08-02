package com.musiccontroller;

import com.musiccontroller.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MusicFrequencyController implements ModInitializer {
	public static final String MOD_ID = "musicfrequencycontroller";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Music Frequency Controller initializing...");
		
		// Load configuration
		ConfigManager.loadConfig();
		
		LOGGER.info("Music Frequency Controller initialized! Current frequency: {}x", 
			ConfigManager.getMusicFrequencyMultiplier());
	}
}