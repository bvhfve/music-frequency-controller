package com.musiccontroller.config;

import org.lwjgl.glfw.GLFW;

public class MusicConfig {
    // Configuration version for future compatibility
    public int configVersion = 1;
    
    // Music frequency multiplier - 1.0 is default vanilla frequency
    public float musicFrequencyMultiplier = 1.0f;
    
    // Step size for frequency adjustments
    public float frequencyStep = 0.1f;
    
    // Enable/disable key bindings
    public boolean enableKeyBindings = true;
    
    // Enable/disable on-screen messages
    public boolean showFrequencyMessages = true;
    
    // Enable/disable debug logging
    public boolean debugMode = false;
    
    // Customizable key bindings (GLFW key codes) - Simplified for config access only
    public int openConfigKey = GLFW.GLFW_KEY_M;                 // M key (Music)
    
    // Constants for frequency control
    public static final float MIN_FREQUENCY = 0.1f;  // Slowest (default vanilla)
    public static final float MAX_FREQUENCY = 10.0f; // Fastest (nearly continuous)
    
    // Default constructor for GSON
    public MusicConfig() {}
    
    // Validation method
    public void validate() {
        if (musicFrequencyMultiplier < MIN_FREQUENCY) {
            musicFrequencyMultiplier = MIN_FREQUENCY;
        }
        if (musicFrequencyMultiplier > MAX_FREQUENCY) {
            musicFrequencyMultiplier = MAX_FREQUENCY;
        }
        if (frequencyStep <= 0 || frequencyStep > 1.0f) {
            frequencyStep = 0.1f;
        }
        
        // Validate key bindings (ensure they're valid GLFW key codes)
        if (openConfigKey < 0) openConfigKey = GLFW.GLFW_KEY_M;
    }
    
    // Helper methods for key binding management
    public void resetKeyBindingsToDefaults() {
        openConfigKey = GLFW.GLFW_KEY_M;                 // M key
    }
    
    private boolean isValidKeyCode(int keyCode) {
        // Check if it's a valid GLFW key code
        return keyCode >= GLFW.GLFW_KEY_SPACE && keyCode <= GLFW.GLFW_KEY_LAST;
    }
    
    // Helper method to get key name for display
    public static String getKeyName(int keyCode) {
        switch (keyCode) {
            case GLFW.GLFW_KEY_KP_ADD: return "Numpad +";
            case GLFW.GLFW_KEY_KP_SUBTRACT: return "Numpad -";
            case GLFW.GLFW_KEY_KP_0: return "Numpad 0";
            case GLFW.GLFW_KEY_KP_1: return "Numpad 1";
            case GLFW.GLFW_KEY_KP_2: return "Numpad 2";
            case GLFW.GLFW_KEY_KP_3: return "Numpad 3";
            case GLFW.GLFW_KEY_KP_4: return "Numpad 4";
            case GLFW.GLFW_KEY_KP_5: return "Numpad 5";
            case GLFW.GLFW_KEY_KP_6: return "Numpad 6";
            case GLFW.GLFW_KEY_KP_7: return "Numpad 7";
            case GLFW.GLFW_KEY_KP_8: return "Numpad 8";
            case GLFW.GLFW_KEY_KP_9: return "Numpad 9";
            case GLFW.GLFW_KEY_KP_ENTER: return "Numpad Enter";
            case GLFW.GLFW_KEY_KP_MULTIPLY: return "Numpad *";
            case GLFW.GLFW_KEY_KP_DIVIDE: return "Numpad /";
            case GLFW.GLFW_KEY_KP_DECIMAL: return "Numpad .";
            case GLFW.GLFW_KEY_EQUAL: return "= (Plus)";
            case GLFW.GLFW_KEY_MINUS: return "- (Minus)";
            case GLFW.GLFW_KEY_LEFT_BRACKET: return "[";
            case GLFW.GLFW_KEY_RIGHT_BRACKET: return "]";
            case GLFW.GLFW_KEY_SEMICOLON: return ";";
            case GLFW.GLFW_KEY_APOSTROPHE: return "'";
            case GLFW.GLFW_KEY_COMMA: return ",";
            case GLFW.GLFW_KEY_PERIOD: return ".";
            case GLFW.GLFW_KEY_SLASH: return "/";
            case GLFW.GLFW_KEY_BACKSLASH: return "\\";
            case GLFW.GLFW_KEY_GRAVE_ACCENT: return "`";
            case GLFW.GLFW_KEY_ENTER: return "Enter";
            case GLFW.GLFW_KEY_SPACE: return "Space";
            case GLFW.GLFW_KEY_TAB: return "Tab";
            case GLFW.GLFW_KEY_BACKSPACE: return "Backspace";
            case GLFW.GLFW_KEY_DELETE: return "Delete";
            case GLFW.GLFW_KEY_INSERT: return "Insert";
            case GLFW.GLFW_KEY_HOME: return "Home";
            case GLFW.GLFW_KEY_END: return "End";
            case GLFW.GLFW_KEY_PAGE_UP: return "Page Up";
            case GLFW.GLFW_KEY_PAGE_DOWN: return "Page Down";
            case GLFW.GLFW_KEY_UP: return "Up Arrow";
            case GLFW.GLFW_KEY_DOWN: return "Down Arrow";
            case GLFW.GLFW_KEY_LEFT: return "Left Arrow";
            case GLFW.GLFW_KEY_RIGHT: return "Right Arrow";
            default:
                // Handle F keys
                if (keyCode >= GLFW.GLFW_KEY_F1 && keyCode <= GLFW.GLFW_KEY_F25) {
                    return "F" + (keyCode - GLFW.GLFW_KEY_F1 + 1);
                }
                // Handle letter keys
                if (keyCode >= GLFW.GLFW_KEY_A && keyCode <= GLFW.GLFW_KEY_Z) {
                    return String.valueOf((char) keyCode);
                }
                // Handle number keys
                if (keyCode >= GLFW.GLFW_KEY_0 && keyCode <= GLFW.GLFW_KEY_9) {
                    return String.valueOf((char) keyCode);
                }
                return "Key " + keyCode;
        }
    }
}