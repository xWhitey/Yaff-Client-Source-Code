package store.yaff.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class Screen extends GuiScreen {
    public static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    public static int scaleFactor = 0;
    public static int scaleWidth = 0;
    public static int scaleHeight = 0;

    public static void updateScaledResolution(ScaledResolution scaledresolution) {
        sr = scaledresolution;
        scaleFactor = ScaledResolution.getScaleFactor();
        scaleWidth = scaledresolution.getScaledWidth();
        scaleHeight = scaledresolution.getScaledHeight();
    }

    public static boolean isSizeChanged() {
        boolean b = ScaledResolution.getScaleFactor() != scaleFactor || sr.getScaledWidth() != scaleWidth || sr.getScaledHeight() != scaleHeight;
        if (b) {
            Screen.updateScaledResolution(new ScaledResolution(Minecraft.getMinecraft()));
        }
        return b;
    }

}
