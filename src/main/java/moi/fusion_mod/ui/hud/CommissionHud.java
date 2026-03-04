package moi.fusion_mod.ui.hud;

import moi.fusion_mod.ui.layout.JarvisGuiManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommissionHud implements JarvisGuiManager.JarvisHud {
    // Extracted EXACTLY from
    // doc/skyblocker/src/main/java/de/hysky/skyblocker/skyblock/tabhud/widget/CommsWidget.java
    public static final Pattern COMM_PATTERN = Pattern.compile("(?<name>.*): (?<progress>.*)%?");

    // Hardcoded position for 2D UI for now
    private final Vector2i position = new Vector2i(10, 10);
    private List<String> currentCommissions = new ArrayList<>();

    @Override
    public boolean isEnabled() {
        // Assume enabled true for the module currently
        return true;
    }

    @Override
    public int getUnscaledWidth() {
        return 120;
    }

    @Override
    public int getUnscaledHeight() {
        return 10 + (currentCommissions.size() * 10);
    }

    @Override
    public Vector2ic getPosition() {
        return position;
    }

    @Override
    public void render(GuiGraphics graphics, float tickDelta) {
        updateCommissions();

        Minecraft mc = Minecraft.getInstance();
        if (mc.font == null)
            return;

        // Comm list title header
        graphics.drawString(mc.font, "§3§lCommissions", 0, 0, 0xFFFFFF);

        // Render commission lines
        int y = 10;
        for (String line : currentCommissions) {
            graphics.drawString(mc.font, line, 0, y, 0xFFFFFF);
            y += 10;
        }
    }

    private void updateCommissions() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getConnection() == null)
            return;

        boolean foundCommissions = false;
        List<String> newCommissions = new ArrayList<>();

        // Extracted EXACTLY from
        // doc/skyblocker/src/main/java/de/hysky/skyblocker/skyblock/dwarven/CommissionLabels.java
        for (PlayerInfo entry : mc.getConnection().getListedPlayerInfos()) {
            Component displayName = entry.getTabListDisplayName();
            if (displayName == null)
                continue;

            String string = displayName.getString();
            if (foundCommissions) {
                if (!string.startsWith(" "))
                    break; // Comm blocks end when padding stops
                string = string.substring(1);
                Matcher matcher = COMM_PATTERN.matcher(string);
                if (matcher.matches()) {
                    String name = matcher.group("name");
                    String progress = matcher.group("progress");

                    if ("DONE".equals(progress)) {
                        newCommissions.add("§b" + name + ": §a" + progress);
                    } else {
                        newCommissions.add("§b" + name + ": §e" + progress + "%");
                    }
                }
            } else if (string.startsWith("Commissions")) {
                foundCommissions = true;
            }
        }

        this.currentCommissions = newCommissions;
    }
}
