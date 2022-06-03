package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

public class ScaledResolution {
    private static double scaledWidthD;
    private static double scaledHeightD;
    private static int scaledWidth;
    private static int scaledHeight;
    private static int scaleFactor;

    public ScaledResolution(Minecraft minecraftClient) {
        scaledWidth = minecraftClient.displayWidth;
        scaledHeight = minecraftClient.displayHeight;
        scaleFactor = 1;
        boolean flag = minecraftClient.isUnicode();
        int i = minecraftClient.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (scaleFactor < i && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (flag && scaleFactor % 2 != 0 && scaleFactor != 1) {
            --scaleFactor;
        }
        scaledWidthD = (double) scaledWidth / (double) scaleFactor;
        scaledHeightD = (double) scaledHeight / (double) scaleFactor;
        scaledWidth = MathHelper.ceil(scaledWidthD);
        scaledHeight = MathHelper.ceil(scaledHeightD);
    }

    public static int getScaleFactor() {
        return scaleFactor;
    }

    public int getScaledWidth() {
        return scaledWidth;
    }

    public int getScaledHeight() {
        return scaledHeight;
    }

    public double getScaledWidth_double() {
        return scaledWidthD;
    }

    public double getScaledHeight_double() {
        return scaledHeightD;
    }

}
