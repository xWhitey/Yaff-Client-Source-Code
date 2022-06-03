package store.yaff.helper.tenacity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import store.yaff.helper.Color;

public class Gradient {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final Shader gradientShader = new Shader("gradient");
    public static final Shader gradientMaskShader = new Shader("gradientMask");

    public static void drawGradient(float x, float y, float width, float height, float alpha, int bottomLeft, int topLeft, int bottomRight, int topRight) {
        GlStateManager.color(1, 1, 1, 1);
        GL.setup2DRendering(() -> {
            gradientShader.init();
            gradientShader.setUniformf("location", x * ScaledResolution.getScaleFactor(), (Minecraft.getMinecraft().displayHeight - (height * ScaledResolution.getScaleFactor())) - (y * ScaledResolution.getScaleFactor()));
            gradientShader.setUniformf("rectSize", width * ScaledResolution.getScaleFactor(), height * ScaledResolution.getScaleFactor());
            gradientShader.setUniformf("alpha", alpha);
            // Bottom Left
            gradientMaskShader.setUniformf("color1", Color.r(bottomLeft) / 255f, Color.g(bottomLeft) / 255f, Color.b(bottomLeft) / 255f);
            //Top left
            gradientMaskShader.setUniformf("color2", Color.r(topLeft) / 255f, Color.g(topLeft) / 255f, Color.b(topLeft) / 255f);
            //Bottom Right
            gradientMaskShader.setUniformf("color3", Color.r(bottomRight) / 255f, Color.g(bottomRight) / 255f, Color.b(bottomRight) / 255f);
            //Top Right
            gradientMaskShader.setUniformf("color4", Color.r(topRight) / 255f, Color.g(topRight) / 255f, Color.b(topRight) / 255f);
            Shader.drawQuads(x, y, width, height);
            gradientShader.unload();
        });
    }

    public static void drawGradientLR(float x, float y, float width, float height, float alpha, int left, int right) {
        drawGradient(x, y, width, height, alpha, left, left, right, right);
    }

    public static void drawGradientTB(float x, float y, float width, float height, float alpha, int top, int bottom) {
        drawGradient(x, y, width, height, alpha, bottom, top, bottom, top);
    }

    public static void applyGradientHorizontal(float x, float y, float width, float height, float alpha, int left, int right, Runnable content) {
        applyGradient(x, y, width, height, alpha, left, left, right, right, content);
    }

    public static void applyGradientVertical(float x, float y, float width, float height, float alpha, int top, int bottom, Runnable content) {
        applyGradient(x, y, width, height, alpha, bottom, top, bottom, top, content);
    }

    public static void applyGradient(float x, float y, float width, float height, float alpha, int bottomLeft, int topLeft, int bottomRight, int topRight, Runnable content) {
        GlStateManager.color(1, 1, 1, 1);
        GL.setup2DRendering(() -> {
            gradientMaskShader.init();
            gradientMaskShader.setUniformf("location", x * ScaledResolution.getScaleFactor(), (Minecraft.getMinecraft().displayHeight - (height * ScaledResolution.getScaleFactor())) - (y * ScaledResolution.getScaleFactor()));
            gradientMaskShader.setUniformf("rectSize", width * ScaledResolution.getScaleFactor(), height * ScaledResolution.getScaleFactor());
            gradientMaskShader.setUniformf("alpha", alpha);
            gradientMaskShader.setUniformi("tex", 0);
            // Bottom Left
            gradientMaskShader.setUniformf("color1", Color.r(bottomLeft) / 255f, Color.g(bottomLeft) / 255f, Color.b(bottomLeft) / 255f);
            //Top left
            gradientMaskShader.setUniformf("color2", Color.r(topLeft) / 255f, Color.g(topLeft) / 255f, Color.b(topLeft) / 255f);
            //Bottom Right
            gradientMaskShader.setUniformf("color3", Color.r(bottomRight) / 255f, Color.g(bottomRight) / 255f, Color.b(bottomRight) / 255f);
            //Top Right
            gradientMaskShader.setUniformf("color4", Color.r(topRight) / 255f, Color.g(topRight) / 255f, Color.b(topRight) / 255f);
            content.run();
            gradientMaskShader.unload();
        });
    }

}
