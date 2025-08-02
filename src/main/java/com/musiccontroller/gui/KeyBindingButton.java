package com.musiccontroller.gui;

import com.musiccontroller.config.MusicConfig;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyBindingButton extends ButtonWidget {
    private int keyCode;
    private boolean isListening = false;
    private final String baseText;
    
    public KeyBindingButton(int x, int y, int width, int height, String baseText, int initialKeyCode, PressAction onPress) {
        super(x, y, width, height, Text.literal(baseText + ": " + MusicConfig.getKeyName(initialKeyCode)), onPress, DEFAULT_NARRATION_SUPPLIER);
        this.baseText = baseText;
        this.keyCode = initialKeyCode;
    }
    
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        this.isListening = false;
        updateMessage();
    }
    
    public int getKeyCode() {
        return keyCode;
    }
    
    public void startListening() {
        this.isListening = true;
        updateMessage();
    }
    
    public boolean isListening() {
        return isListening;
    }
    
    public void stopListening() {
        this.isListening = false;
        updateMessage();
    }
    
    public boolean onKeyPressed(int keyCode, int scanCode, int modifiers) {
        if (isListening) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                // Cancel key binding change
                stopListening();
                return true;
            } else {
                // Set new key binding
                setKeyCode(keyCode);
                return true;
            }
        }
        return false;
    }
    
    private void updateMessage() {
        if (isListening) {
            setMessage(Text.literal(baseText + ": Press a key... (ESC to cancel)"));
        } else {
            setMessage(Text.literal(baseText + ": " + MusicConfig.getKeyName(keyCode)));
        }
    }
}