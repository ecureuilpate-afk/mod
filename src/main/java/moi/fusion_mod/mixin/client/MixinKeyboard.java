package moi.fusion_mod.mixin.client;

import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class MixinKeyboard {
    @Inject(method = "keyPress", at = @At("HEAD"), cancellable = true)
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        // Intercept key presses for HUD elements
    }
}
