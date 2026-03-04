package moi.fusion_mod.social;

import net.minecraft.network.chat.Component;

public class ChatFilter {
    public static boolean shouldAllowChat(Component message, java.util.UUID sender) {
        String text = message.getString();
        // Remove "Party Translator" spam
        return !text.contains("Party Translator:");
    }

    public static boolean shouldAllowGame(Component message, boolean overlay) {
        String text = message.getString();
        return !text.contains("Party Translator:");
    }
}
