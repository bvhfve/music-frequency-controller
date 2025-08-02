package com.musiccontroller;

import com.musiccontroller.config.ConfigManager;

/**
 * @deprecated Use ConfigManager instead. This class is kept for backward compatibility.
 */
@Deprecated
public class MusicFrequencyConfig {
    
    public static float getMusicFrequencyMultiplier() {
        return ConfigManager.getMusicFrequencyMultiplier();
    }
    
    public static void setMusicFrequencyMultiplier(float multiplier) {
        ConfigManager.setMusicFrequencyMultiplier(multiplier);
    }
    
    public static void increaseMusicFrequency() {
        ConfigManager.increaseMusicFrequency();
    }
    
    public static void decreaseMusicFrequency() {
        ConfigManager.decreaseMusicFrequency();
    }
    
    public static void resetMusicFrequency() {
        ConfigManager.resetMusicFrequency();
    }
    
    public static String getMusicFrequencyDisplay() {
        return ConfigManager.getMusicFrequencyDisplay();
    }
    
    public static float getDelayMultiplier() {
        return ConfigManager.getDelayMultiplier();
    }
}