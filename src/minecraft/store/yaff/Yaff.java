package store.yaff;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import store.yaff.command.CommandManager;
import store.yaff.engine.DiscordRP;
import store.yaff.feature.FeatureManager;
import store.yaff.file.FileManager;
import store.yaff.file.impl.Configuration;
import store.yaff.file.impl.Friend;
import store.yaff.friend.FriendController;
import store.yaff.helper.StringGenerator;
import store.yaff.helper.TPS;
import store.yaff.macro.MacroController;
import store.yaff.ui.changelog.Changelog;

public class Yaff {
    public static final Logger LOGGER = LogManager.getLogger();
    public static Yaff of = new Yaff();
    public Minecraft mc;
    public String name = "Yaff", build = "210522";
    public StringGenerator stringGenerator;
    public FriendController friendController;
    public MacroController macroController;
    public FeatureManager featureManager;
    public FileManager fileManager;
    public CommandManager commandManager;
    public TPS tpsController;
    public Changelog changelogManager;

    public static void safeShutdown() {
        try {
            Display.destroy();
            Minecraft.getMinecraft().shutdown();
            Runtime.getRuntime().exit(0);
        } catch (Exception ignored) {
        }
    }

    public static void instantShutdown() {
        try {
            Display.destroy();
            Minecraft.getMinecraft().shutdown();
            Runtime.getRuntime().halt(0);
        } catch (Exception ignored) {
        }
    }

    public float getDeltaTime() {
        return Minecraft.getDebugFPS() > 0 ? 1f / Minecraft.getDebugFPS() : 1f;
    }

    public void onInit() {
        mc = Minecraft.getMinecraft();
        stringGenerator = new StringGenerator(true, true, true, false);
        friendController = new FriendController();
        macroController = new MacroController();
        fileManager = new FileManager();
        featureManager = new FeatureManager();
        commandManager = new CommandManager();
        tpsController = new TPS();
        changelogManager = new Changelog();
        //LanguageController.loadLocaleData();
        Friend.loadFriendList();
        Configuration.loadConfiguration();
        mc.gameSettings.ofFastRender = false;
        DiscordRP.init();
    }

}
