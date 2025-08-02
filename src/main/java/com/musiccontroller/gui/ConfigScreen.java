package com.musiccontroller.gui;

import com.musiccontroller.config.ConfigManager;
import com.musiccontroller.config.MusicConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private SliderWidget frequencySlider;
    private SliderWidget stepSlider;
    private CheckboxWidget keyBindingsCheckbox;
    private CheckboxWidget messagesCheckbox;
    private CheckboxWidget debugCheckbox;
    
    // Key binding button
    private KeyBindingButton configKeyButton;
    
    public ConfigScreen(Screen parent) {
        super(Text.literal("Music Frequency Controller Config"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        super.init();
        
        MusicConfig config = ConfigManager.getConfig();
        
        // Title
        int centerX = this.width / 2;
        int startY = 50;
        
        // Music Frequency Slider
        this.frequencySlider = new FrequencySlider(
            centerX - 150, startY, 300, 20,
            Text.literal("Music Frequency: "), 
            (config.musicFrequencyMultiplier - MusicConfig.MIN_FREQUENCY) / 
            (MusicConfig.MAX_FREQUENCY - MusicConfig.MIN_FREQUENCY)
        );
        this.addDrawableChild(frequencySlider);
        
        // Frequency Step Slider
        this.stepSlider = new StepSlider(
            centerX - 150, startY + 35, 300, 20,
            Text.literal("Adjustment Step: "),
            (config.frequencyStep - 0.01f) / 0.99f // 0.01 to 1.0 range
        );
        this.addDrawableChild(stepSlider);
        
        // Checkboxes (with more spacing)
        this.keyBindingsCheckbox = CheckboxWidget.builder(Text.literal("Enable Key Bindings"), this.textRenderer)
            .pos(centerX - 150, startY + 80)
            .checked(config.enableKeyBindings)
            .build();
        this.addDrawableChild(keyBindingsCheckbox);
        
        this.messagesCheckbox = CheckboxWidget.builder(Text.literal("Show Frequency Messages"), this.textRenderer)
            .pos(centerX - 150, startY + 100)
            .checked(config.showFrequencyMessages)
            .build();
        this.addDrawableChild(messagesCheckbox);
        
        this.debugCheckbox = CheckboxWidget.builder(Text.literal("Debug Mode"), this.textRenderer)
            .pos(centerX - 150, startY + 120)
            .checked(config.debugMode)
            .build();
        this.addDrawableChild(debugCheckbox);
        
        // Key binding button for config access
        this.configKeyButton = new KeyBindingButton(
            centerX - 100, startY + 155, 200, 20,
            "Open Config Key", config.openConfigKey,
            button -> ((KeyBindingButton) button).startListening()
        );
        this.addDrawableChild(configKeyButton);
        
        // Buttons
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Reset All Settings"), button -> {
            ConfigManager.resetMusicFrequency();
            MusicConfig newConfig = new MusicConfig();
            ConfigManager.getConfig().frequencyStep = newConfig.frequencyStep;
            ConfigManager.getConfig().enableKeyBindings = newConfig.enableKeyBindings;
            ConfigManager.getConfig().showFrequencyMessages = newConfig.showFrequencyMessages;
            ConfigManager.getConfig().debugMode = newConfig.debugMode;
            ConfigManager.getConfig().resetKeyBindingsToDefaults();
            ConfigManager.saveConfig();
            this.init(this.client, this.width, this.height);
        }).dimensions(centerX - 100, startY + 185, 200, 20).build());
        
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> {
            this.saveAndClose();
        }).dimensions(centerX - 50, startY + 215, 100, 20).build());
    }
    
    private void saveAndClose() {
        MusicConfig config = ConfigManager.getConfig();
        
        // Save slider values using public getter methods
        float freqValue = ((FrequencySlider)frequencySlider).getFloatValue();
        float newFreq = MusicConfig.MIN_FREQUENCY + (freqValue * (MusicConfig.MAX_FREQUENCY - MusicConfig.MIN_FREQUENCY));
        ConfigManager.setMusicFrequencyMultiplier(newFreq);
        
        float stepValue = ((StepSlider)stepSlider).getFloatValue();
        config.frequencyStep = 0.01f + (stepValue * 0.99f);
        config.enableKeyBindings = keyBindingsCheckbox.isChecked();
        config.showFrequencyMessages = messagesCheckbox.isChecked();
        config.debugMode = debugCheckbox.isChecked();
        
        // Save key binding
        config.openConfigKey = configKeyButton.getKeyCode();
        
        ConfigManager.saveConfig();
        
        // Update the actual key bindings in the client
        try {
            Class<?> clientClass = Class.forName("com.musiccontroller.MusicFrequencyControllerClient");
            java.lang.reflect.Method updateMethod = clientClass.getDeclaredMethod("updateKeyBindings");
            updateMethod.invoke(null);
        } catch (Exception e) {
            // Fallback - key bindings will update on next game restart
        }
        
        this.client.setScreen(parent);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        
        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // Draw section headers and info (positioned to not overlap)
        context.drawTextWithShadow(this.textRenderer, "Music Settings", this.width / 2 - 150, 35, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "Options", this.width / 2 - 150, 65, 0xFFFFFF);
        context.drawTextWithShadow(this.textRenderer, "Key Binding", this.width / 2 - 150, 140, 0xFFFFFF);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Handle key binding input
        if (configKeyButton.isListening() && configKeyButton.onKeyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public void close() {
        this.saveAndClose();
    }
    
    private static class FrequencySlider extends SliderWidget {
        public FrequencySlider(int x, int y, int width, int height, Text text, double value) {
            super(x, y, width, height, text, value);
            this.updateMessage();
        }
        
        @Override
        protected void updateMessage() {
            float freq = MusicConfig.MIN_FREQUENCY + 
                (float)(this.value * (MusicConfig.MAX_FREQUENCY - MusicConfig.MIN_FREQUENCY));
            this.setMessage(Text.literal(String.format("Music Frequency: %.1fx", freq)));
        }
        
        @Override
        protected void applyValue() {
            // Value is applied when screen is closed
        }
        
        public double getValue() {
            return this.value;
        }
        
        public float getFloatValue() {
            return (float)this.value;
        }
    }
    
    private static class StepSlider extends SliderWidget {
        public StepSlider(int x, int y, int width, int height, Text text, double value) {
            super(x, y, width, height, text, value);
            this.updateMessage();
        }
        
        @Override
        protected void updateMessage() {
            float step = 0.01f + (float)(this.value * 0.99f);
            this.setMessage(Text.literal(String.format("Adjustment Step: %.2f", step)));
        }
        
        @Override
        protected void applyValue() {
            // Value is applied when screen is closed
        }
        
        public double getValue() {
            return this.value;
        }
        
        public float getFloatValue() {
            return (float)this.value;
        }
    }
}