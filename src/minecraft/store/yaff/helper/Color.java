package store.yaff.helper;

import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class Color {
    public static int r(int color) {
        return color >> 16 & 0xFF;
    }

    public static int g(int color) {
        return color >> 8 & 0xFF;
    }

    public static int b(int color) {
        return color & 0xFF;
    }

    public static int a(int color) {
        return color >> 24 & 0xFF;
    }

    public static int rgb(int red, int green, int blue) {
        return 255 << 24 | red << 16 | green << 8 | blue;
    }

    public static int rgba(int red, int green, int blue, int alpha) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public static int hex(int hex) {
        return rgba((hex >> 16) & 255, (hex >> 8) & 255, (hex) & 255, (hex >> 24) & 255);
    }

    public static int hex(int hex, int alpha) {
        return rgba((hex >> 16) & 255, (hex >> 8) & 255, (hex) & 255, alpha);
    }

    public static void glColor(int color) {
        GL11.glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
    }

    public static int colorMix(int firstColor, int secondColor, double swapSpeed) {
        float speedValue = MathHelper.clamp(((float) java.lang.Math.sin((java.lang.Math.PI * 6f) * (swapSpeed / 4 % 1)) / 2f + 0.5f), 0, 1);
        return new java.awt.Color(MathHelper.lerp(r(firstColor) / 255f, r(secondColor) / 255f, speedValue), MathHelper.lerp(g(firstColor) / 255f, g(secondColor) / 255f, speedValue), MathHelper.lerp(b(firstColor) / 255f, b(secondColor) / 255f, speedValue)).getRGB();
    }

}
