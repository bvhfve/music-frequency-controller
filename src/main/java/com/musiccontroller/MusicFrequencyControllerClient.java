package com.musiccontroller;

import com.musiccontroller.config.ConfigManager;
import com.musiccontroller.gui.ConfigScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class MusicFrequencyControllerClient implements ClientModInitializer {
    
    private static KeyBinding openConfigKey;
    
    @Override
    public void onInitializeClient() {
        // Load config first to get custom key bindings
        ConfigManager.loadConfig();
        
        // Register key binding for config access only
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.musicfrequencycontroller.config",
            InputUtil.Type.KEYSYM,
            ConfigManager.getConfig().openConfigKey,
            "category.musicfrequencycontroller.keys"
        ));
        
        // Register tick event for key handling
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            // Check if key bindings are enabled
            if (!ConfigManager.getConfig().enableKeyBindings) return;
            
            while (openConfigKey.wasPressed()) {
                client.setScreen(new ConfigScreen(client.currentScreen));
            }
        });
        
        MusicFrequencyController.LOGGER.info("Music Frequency Controller Client initialized!");
    }
    
    // Method to update key binding when config changes
    public static void updateKeyBindings() {
        if (openConfigKey != null) {
            openConfigKey.setBoundKey(InputUtil.fromKeyCode(ConfigManager.getConfig().openConfigKey, 0));
        }
    }
}