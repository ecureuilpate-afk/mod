package moi.fusion_mod.social;

import net.minecraft.network.chat.Component;

public class ChatFilter {

    /**
     * Examines a chat message and determines whether to allow rendering to the
     * client log.
     * Logic extracted based on SkyHanni concept blocks.
     * 
     * @param message   The message component from chat
     * @param isOverlay True if actionbar overlay
     * @return false to cancel the event, true to proceed
     */
    public static boolean shouldAllowGame(Component message, boolean isOverlay) {
        String rawText = message.getString();

        // Filter specified Party Translator spam
        if (rawText.contains("Party Translator")) {
            return false;
        }

        return true;
    }
}
