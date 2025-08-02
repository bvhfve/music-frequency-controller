# Music Frequency Controller

A simple Minecraft Fabric mod that lets you control how often background music plays. Adjust from vanilla timing to nearly continuous music with an easy-to-use visual interface.

## ✨ Features

- **🎵 Music Frequency Control**: Adjust from 0.1x (vanilla) to 10.0x (continuous)
- **💾 Persistent Settings**: Configuration saves automatically across game restarts
- **🎮 Visual Interface**: Intuitive sliders and controls in-game
- **🌐 Multi-Version Support**: Works on Minecraft 1.20.1 - 1.21.8
- **⚡ Zero Dependencies**: Only requires Fabric API
- **🔧 Customizable**: Adjust frequency, step size, and toggle features

## 🎮 How to Use

### Simple Controls
- **Press M** → Opens configuration screen
- **Drag frequency slider** → Adjust music frequency (0.1x to 10.0x)
- **Click Done** → Settings save automatically

### Frequency Guide
- **0.1x**: Vanilla timing (rare music)
- **1.0x**: Normal vanilla frequency
- **3.0x**: Music plays 3x more often
- **5.0x**: Very frequent music
- **10.0x**: Nearly continuous music

## 📋 Configuration

### In-Game Settings
- **Music Frequency Slider**: Visual control from 0.1x to 10.0x
- **Adjustment Step**: Control precision of changes
- **Key Bindings**: Toggle M key on/off
- **Messages**: Toggle on-screen frequency notifications
- **Debug Mode**: Enable detailed logging

### Config File
Settings are saved to `.minecraft/config/music-frequency-controller.json`:
```json
{
  "musicFrequencyMultiplier": 3.0,
  "frequencyStep": 0.1,
  "enableKeyBindings": true,
  "showFrequencyMessages": true,
  "debugMode": false,
  "openConfigKey": 77
}
```

## 📦 Installation

1. **Install Prerequisites**:
   - [Fabric Loader](https://fabricmc.net/use/installer/) (0.15.0+)
   - [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
   - Java 21+

2. **Install Mod**:
   - Download the mod jar file
   - Place in your `.minecraft/mods` folder
   - Launch Minecraft with Fabric profile

## 🌐 Compatibility

- **Minecraft**: 1.20.1, 1.21, 1.21.4, 1.21.5, 1.21.7, 1.21.8
- **Mod Loader**: Fabric
- **Java**: 21 or newer
- **Dependencies**: Fabric API only

## 🔧 Technical Details

Uses Mixin to modify the `MusicTracker` class for music timing control. Single jar works across all supported Minecraft versions without requiring separate builds.

## 📄 License

MIT License - see [LICENSE](LICENSE) file for details.
