package store.yaff.engine;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;
import store.yaff.Yaff;

import java.util.Objects;

public class DiscordRP {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void init() {
        club.minnced.discord.rpc.DiscordRPC lib = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        DiscordRichPresence discordRichPresence = new DiscordRichPresence();
        String applicationId = "894140465702637588";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        lib.Discord_Initialize(applicationId, handlers, true, null);
        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000;
        discordRichPresence.largeImageKey = "yaff";
        discordRichPresence.smallImageKey = "yaff";
        discordRichPresence.state = "ξ(｡◕ˇ◊ˇ◕｡)ξ";
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_UpdatePresence(discordRichPresence);
                discordRichPresence.largeImageText = "Enabled features " + Objects.requireNonNull(Yaff.of.featureManager.getFeatures(true)).size() + "/" + Objects.requireNonNull(Yaff.of.featureManager.getFeatures()).size();
                if (mc.world != null) {
                    if (mc.isSingleplayer()) {
                        discordRichPresence.smallImageText = "In Singleplayer";
                    } else {
                        discordRichPresence.smallImageText = (Objects.requireNonNull(mc.getCurrentServerData())).serverIP;
                    }
                } else {
                    if (mc.currentScreen instanceof GuiMultiplayer) {
                        discordRichPresence.smallImageText = "Choosing Server";
                    } else if (mc.currentScreen instanceof GuiWorldSelection) {
                        discordRichPresence.smallImageText = "Choosing World";
                    } else {
                        discordRichPresence.smallImageText = "In Main Menu";
                    }
                }
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    lib.Discord_Shutdown();
                    break;
                }
            }
        }, "RPC-Callback-Handler").start();
    }

}
