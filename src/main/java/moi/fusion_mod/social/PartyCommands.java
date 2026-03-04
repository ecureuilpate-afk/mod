package moi.fusion_mod.social;

import net.minecraft.client.Minecraft;

public class PartyCommands {

    public static void handleMessage(String messageText) {
        // Hypixel party chat regex check. E.g "Party > [MVP+] Notch: !warp"
        if (!messageText.contains("Party >") || !messageText.contains(": "))
            return;

        // Split off the chat payload exclusively
        String content = messageText.substring(messageText.indexOf(": ") + 2).trim();

        // Ensure triggers contain standard prefixes (like SkyHanni commandPrefixes set)
        if (content.isEmpty() || (!content.startsWith("!") && !content.startsWith(".") && !content.startsWith("?"))) {
            return;
        }

        String commandBody = content.substring(1).split(" ")[0].toLowerCase();
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;

        // Perform automated command hooks ported from
        // at.hannibal2.skyhanni.features.commands.PartyChatCommands
        switch (commandBody) {
            case "help":
                mc.player.connection.sendCommand("pc Available Commands: !warp, !pt, !allinvite, !ping");
                break;
            case "warp":
            case "pw":
            case "warpus":
                mc.player.connection.sendCommand("p warp");
                break;
            case "transfer":
            case "pt":
            case "ptme":
                // Parse speaker name bridging over ranks.
                String[] split = messageText.split("Party > ")[1].split(": ");
                String authorRaw = split[0].replaceAll("§.", "").trim(); // Strip formatting just in case
                String[] nameParts = authorRaw.split(" ");
                String targetName = nameParts[nameParts.length - 1];

                mc.player.connection.sendCommand("p transfer " + targetName);
                break;
            case "allinv":
            case "allinvite":
                mc.player.connection.sendCommand("p settings allinvite");
                break;
            case "ping":
                mc.player.connection.sendCommand("pc Current Ping: Active");
                break;
            case "tps":
                mc.player.connection.sendCommand("pc TPS is responding globally.");
                break;
        }
    }
}
