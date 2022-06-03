package store.yaff.helper.tenacity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import store.yaff.helper.Color;

public class Rounded {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final Shader roundedShader = new Shader("roundedRect");
    public static final Shader roundedOutlineShader = new Shader("roundedRectOutline");

    public static void drawRound(float x, float y, float width, float height, float radius, int color) {
        GlStateManager.color(1, 1, 1, 1);
        GL.setup2DRendering(() -> {
            roundedShader.init();
            setupRoundedRectUniforms(x, y, width, height, radius, roundedShader);
            roundedShader.setUniformi("blur", 0);
            roundedShader.setUniformf("color", Color.r(color) / 255f, Color.g(color) / 255f, Color.b(color) / 255f, Color.a(color) / 255f);
            Shader.drawQuads(x - 1, y - 1, width + 2, height + 2);
            roundedShader.unload();
        });
    }

    public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, int color, int outlineColor) {
        GlStateManager.color(1, 1, 1, 1);
        GL.setup2DRendering(() -> {
            roundedOutlineShader.init();
            setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
            roundedOutlineShader.setUniformf("outlineThickness", outlineThickness * ScaledResolution.getScaleFactor());
            roundedOutlineShader.setUniformf("color", Color.r(color) / 255f, Color.g(color) / 255f, Color.b(color) / 255f, Color.a(color) / 255f);
            roundedOutlineShader.setUniformf("outlineColor", Color.r(outlineColor) / 255f, Color.g(outlineColor) / 255f, Color.b(outlineColor) / 255f, Color.a(outlineColor) / 255f);
            Shader.drawQuads(x - (2 + outlineThickness), y - (2 + outlineThickness), width + (4 + outlineThickness * 2), height + (4 + outlineThickness * 2));
            roundedOutlineShader.unload();
        });
    }

    private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius, Shader roundedTexturedShader) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        roundedTexturedShader.setUniformf("location", x * ScaledResolution.getScaleFactor(), (Minecraft.getMinecraft().displayHeight - (height * ScaledResolution.getScaleFactor())) - (y * ScaledResolution.getScaleFactor()));
        roundedTexturedShader.setUniformf("rectSize", width * ScaledResolution.getScaleFactor(), height * ScaledResolution.getScaleFactor());
        roundedTexturedShader.setUniformf("radius", radius * ScaledResolution.getScaleFactor());
    }

}
