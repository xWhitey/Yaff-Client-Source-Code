package store.yaff.ui.menu;

import net.minecraft.client.gui.GuiScreen;
import store.yaff.helper.Color;
import store.yaff.helper.Screen;
import store.yaff.shader.GLSLShader;
import store.yaff.ui.button.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiContainer extends GuiScreen {
    public static final long initTime = System.currentTimeMillis();
    public static final ArrayList<Button> buttonList = new ArrayList<>();
    public static final Button singleplayerButton, multiplayerButton, settingsButton, langButton, altButton;
    public static GLSLShader backgroundShader;

    static {
        try {
            backgroundShader = new GLSLShader("/shaders/screen.fsh");
        } catch (IOException ignored) {
        }
        singleplayerButton = new Button(1, Screen.sr.getScaledWidth() - 100, 240, 50, 50, Color.hex(0xFF3b3b40, 242), "C");
        multiplayerButton = new Button(2, Screen.sr.getScaledWidth() - 100, 240, 50, 50, Color.hex(0xFF3b3b40, 242), "D");
        settingsButton = new Button(3, Screen.sr.getScaledWidth() - 100, 240, 50, 50, Color.hex(0xFF3b3b40, 242), "o");
        langButton = new Button(4, Screen.sr.getScaledWidth() - 100, 240, 50, 50, Color.hex(0xFF3b3b40, 242), "E");
        altButton = new Button(6, Screen.sr.getScaledWidth() - 100, 240, 50, 50, Color.hex(0xFF3b3b40, 242), "Q");
        buttonList.clear();
        buttonList.addAll(Arrays.asList(GuiContainer.singleplayerButton, GuiContainer.multiplayerButton, GuiContainer.settingsButton, GuiContainer.langButton, GuiContainer.altButton));
        System.gc();
    }
}
