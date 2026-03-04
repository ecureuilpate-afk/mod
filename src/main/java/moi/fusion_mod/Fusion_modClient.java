package moi.fusion_mod;

import moi.fusion_mod.config.FusionConfig;
import moi.fusion_mod.economy.ItemTooltipListener;
import moi.fusion_mod.hollows.ChestEspRenderer;
import moi.fusion_mod.ui.layout.JarvisGuiManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class Fusion_modClient implements ClientModInitializer {
    public static KeyMapping configKeybind;

    @Override
    public void onInitializeClient() {
        FusionConfig.init();

        configKeybind = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.fusion_mod.config", 
            GLFW.GLFW_KEY_G, 
            "category.fusion_mod.general"
        ));

        JarvisGuiManager.initializeHuds();

        registerEvents();
    }

    private void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeybind.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(FusionConfig.createDisplayScreen(client.screen));
                }
            }
        });

        ItemTooltipCallback.EVENT.register(new ItemTooltipListener());

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (FusionConfig.isChestEspEnabled()) {
                ChestEspRenderer.render(context);
            }
        });
    }
}
