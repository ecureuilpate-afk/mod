package moi.fusion_mod.progression;

import moi.fusion_mod.ui.layout.JarvisGuiManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExperimentSolver {

    private static final Map<Item, Item> TERRACOTTA_TO_GLASS = Map.of(
            Items.RED_TERRACOTTA, Items.RED_STAINED_GLASS,
            Items.ORANGE_TERRACOTTA, Items.ORANGE_STAINED_GLASS,
            Items.YELLOW_TERRACOTTA, Items.YELLOW_STAINED_GLASS,
            Items.LIME_TERRACOTTA, Items.LIME_STAINED_GLASS,
            Items.GREEN_TERRACOTTA, Items.GREEN_STAINED_GLASS,
            Items.CYAN_TERRACOTTA, Items.CYAN_STAINED_GLASS,
            Items.LIGHT_BLUE_TERRACOTTA, Items.LIGHT_BLUE_STAINED_GLASS,
            Items.BLUE_TERRACOTTA, Items.BLUE_STAINED_GLASS,
            Items.PURPLE_TERRACOTTA, Items.PURPLE_STAINED_GLASS,
            Items.PINK_TERRACOTTA, Items.PINK_STAINED_GLASS);

    private static final List<Item> chronomatronSlots = new ArrayList<>();
    private static int memoryPhaseLength;
    private static int currentGlowingSlot;
    private static int clickIndex;
    private static boolean isSolving;

    public static void onScreenRender(AbstractContainerScreen<?> screen, GuiGraphics context) {
        if (screen == null || screen.getTitle() == null)
            return;
        String title = screen.getTitle().getString();

        if (title.contains("Chronomatron")) {
            handleChronomatron(screen, context);
        } else if (title.contains("Ultrasequencer")) {
            // Placeholder for Ultrasequencer logic
            // Add full sequence memorization here as ported from Skyblocker
            // UltrasequencerSolver
        }
    }

    private static void handleChronomatron(AbstractContainerScreen<?> screen, GuiGraphics context) {
        boolean isSingleRow = titleMatchesSingleRow(screen.getTitle().getString());
        int maxIndex = isSingleRow ? 25 : 34;

        Slot timerSlot = screen.getMenu().slots.get(49);
        boolean isTimer = timerSlot != null && timerSlot.getItem().getHoverName().getString().startsWith("Timer:");

        if (!isTimer) {
            // We are in "Remember the pattern!" phase
            isSolving = false;

            // Loop through center slots
            for (int i = 17; i <= maxIndex; i++) {
                Slot slot = screen.getMenu().slots.get(i);
                if (slot != null && slot.hasItem()) {
                    ItemStack stack = slot.getItem();
                    // Detect shiny block
                    if (stack.hasFoil()) {
                        if (currentGlowingSlot == 0) {
                            if (chronomatronSlots.size() <= memoryPhaseLength) {
                                chronomatronSlots
                                        .add(TERRACOTTA_TO_GLASS.getOrDefault(stack.getItem(), stack.getItem()));
                            } else {
                                memoryPhaseLength++;
                            }
                            currentGlowingSlot = i;
                        }
                    } else if (currentGlowingSlot == i && !stack.hasFoil()) {
                        currentGlowingSlot = 0; // Finished glowing
                    }
                }
            }
        } else {
            // We are in the "Your turn!" clicking phase
            isSolving = true;
            if (chronomatronSlots.size() > clickIndex) {
                Item target = chronomatronSlots.get(clickIndex);

                // Highlight correct slot green
                for (int i = 17; i <= maxIndex; i++) {
                    Slot slot = screen.getMenu().slots.get(i);
                    if (slot != null && slot.hasItem()) {
                        ItemStack stack = slot.getItem();
                        if (stack.is(target) || TERRACOTTA_TO_GLASS.get(stack.getItem()) == target) {
                            context.fill(slot.x, slot.y, slot.x + 16, slot.y + 16, 0x8000FF00);
                        }
                    }
                }
            }
        }
    }

    private static boolean titleMatchesSingleRow(String title) {
        return title.endsWith("(High)") || title.endsWith("(Grand)") || title.endsWith("(Supreme)");
    }

    public static void onClickSlot(Slot slot, ItemStack stack) {
        if (isSolving && chronomatronSlots.size() > clickIndex) {
            Item target = chronomatronSlots.get(clickIndex);
            if (stack.is(target) || TERRACOTTA_TO_GLASS.get(stack.getItem()) == target) {
                clickIndex++;
            }
        }
    }

    public static void resetMemory() {
        chronomatronSlots.clear();
        memoryPhaseLength = 0;
        clickIndex = 0;
        currentGlowingSlot = 0;
        isSolving = false;
    }
}
