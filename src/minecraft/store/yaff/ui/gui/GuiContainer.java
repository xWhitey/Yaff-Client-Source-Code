package store.yaff.ui.gui;

import net.minecraft.client.gui.GuiScreen;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Screen;
import store.yaff.shader.Desaturation;

public class GuiContainer extends GuiScreen {
    public static final Desaturation desaturation;
    public static float width = 394, height = 260;
    public static float windowX = Screen.sr.getScaledWidth() / 2f - width / 2f, windowY = Screen.sr.getScaledHeight() / 2f - height / 2f;
    public static Category currentCategory = Category.COMBAT;
    public static AbstractFeature currentFeature;

    static {
        desaturation = new Desaturation();
        System.gc();
    }
}
