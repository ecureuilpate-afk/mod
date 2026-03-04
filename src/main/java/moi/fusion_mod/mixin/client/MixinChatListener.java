package moi.fusion_mod.mixin.client;

import moi.fusion_mod.social.ChatFilter;
import moi.fusion_mod.social.PartyCommands;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatComponent.class)
public class MixinChatListener {

    @Inject(method = "addMessage(Lnet/minecraft/network/chat/Component;)V", at = @At("HEAD"), cancellable = true)
    public void onAddMessage(Component message, CallbackInfo ci) {
        if (!ChatFilter.shouldAllowGame(message, false)) {
            ci.cancel();
            return;
        }
        PartyCommands.handleMessage(message.getString());
    }
}
