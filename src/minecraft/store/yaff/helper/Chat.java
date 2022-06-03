package store.yaff.helper;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.StringUtils;
import store.yaff.Yaff;

public class Chat {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static String space = " ";

    public static void addChatMessage(String message) {
        mc.player.addChatMessage(new TextComponentString(ChatFormatting.BLUE + "[" + Yaff.of.name + "] " + ChatFormatting.RESET + ">> " + message));
    }

    public static void sendChatMessage(String message) {
        mc.player.sendChatMessage(message);
    }

    public static String parseMessage(byte startArg, String... args) {
        StringBuilder message = new StringBuilder();
        for (byte b = startArg; b < args.length; b++) {
            message.append(args[b]).append(" ");
        }
        return net.minecraft.util.StringUtils.stripControlCodes(StringUtils.normalizeSpace(message.toString()));
    }

}
