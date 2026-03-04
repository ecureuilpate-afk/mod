package moi.fusion_mod.config;

import net.minecraft.client.gui.screens.Screen;
// Simplified configuration system representation
public class FusionConfig {
    private static boolean chestEspEnabled = false;

    public static void init() {
        // Load configuration from file
    }

    public static Screen createDisplayScreen(Screen parent) {
        // Return a mock screen for the configuration menu
        return new ConfigScreen(parent);
    }

    public static boolean isChestEspEnabled() {
        return chestEspEnabled;
    }

    public static void setChestEspEnabled(boolean enabled) {
        chestEspEnabled = enabled;
    }
}
