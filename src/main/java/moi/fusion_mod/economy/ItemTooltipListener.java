package moi.fusion_mod.economy;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;

import java.util.List;

public class ItemTooltipListener implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag type, List<Component> lines) {
        // Mock economy data addition
        lines.add(Component.literal("§eBazaar: §6100 coins"));
        lines.add(Component.literal("§eAH: §6150 coins"));
        lines.add(Component.literal("§eNPC Sell: §610 coins"));
    }
}
