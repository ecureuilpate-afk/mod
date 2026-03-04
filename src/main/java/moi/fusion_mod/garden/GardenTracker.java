package moi.fusion_mod.garden;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GardenTracker {

    private static final List<String> currentVisitors = new ArrayList<>();

    // Unifies SkyHanni and Skyblocker logic arrays
    public static void onChatReceived(String text) {
        // Suppress double intercepts
        String stripped = text.replaceAll("§.", "");

        // Example: "[NPC] Space-Farmer: Greetings!" -> Track visitor arrival
        if (stripped.contains("[NPC]") && stripped.contains("has arrived at your Garden")) {
            String name = stripped.split("] ")[1].split(" has arrived")[0];
            if (!currentVisitors.contains(name)) { // De-duplicate overlapping alerts
                currentVisitors.add(name);
            }
        }

        if (stripped.contains("has departed from your Garden") || stripped.contains("Offer Accepted")) {
            currentVisitors.clear(); // Simplify clearing on departures
        }
    }

    public static void renderGuide(GuiGraphics context, ItemStack hoverStack, int x, int y) {
        // SkyHanni visitor price calculator overlay hook point.
        // E.g. render net profit margins on accepted trades.
    }
}
