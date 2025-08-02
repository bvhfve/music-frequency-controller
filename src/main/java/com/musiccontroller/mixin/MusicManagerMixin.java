package com.musiccontroller.mixin;

import com.musiccontroller.MusicFrequencyController;
import com.musiccontroller.config.ConfigManager;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class MusicManagerMixin {
    
    @Shadow
    private int timeUntilNextSong;
    
    @Shadow
    private SoundInstance current;
    
    // Inject into the tick method to modify music timing
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        // Apply frequency multiplier to the timeUntilNextSong field
        if (timeUntilNextSong > 1) {
            float originalDelay = timeUntilNextSong;
            float modifiedDelay = originalDelay * ConfigManager.getDelayMultiplier();
            timeUntilNextSong = Math.max(1, (int) modifiedDelay);
            
            if (ConfigManager.getConfig().debugMode && originalDelay != timeUntilNextSong) {
                MusicFrequencyController.LOGGER.debug("Modified music delay: {} -> {} (multiplier: {}x)", 
                    originalDelay, timeUntilNextSong, ConfigManager.getMusicFrequencyMultiplier());
            }
        }
    }
}