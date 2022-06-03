package store.yaff.ui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.math.MathHelper;
import store.yaff.helper.Color;
import store.yaff.helper.Screen;
import store.yaff.helper.tenacity.Rounded;
import store.yaff.ui.button.Button;

public class SplashScreen {
    public final Minecraft mc = Minecraft.getMinecraft();

    public int loadingProgress;
    public int maxProgress = 12;

    public void update() {
        drawSplash(mc.getTextureManager());
    }

    public void setProgress(int progress) {
        loadingProgress = MathHelper.clamp(progress, 0, maxProgress);
        update();
    }

    public void drawSplash(TextureManager textureManager) {
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0, Screen.sr.getScaledWidth(), Screen.sr.getScaledHeight(), 0, 1000, 3000);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0, 0, -2000);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        Rounded.drawRound(0, 0, Screen.sr.getScaledWidth(), Screen.sr.getScaledHeight(), 0, Color.hex(0xFFeeeeee));
        GlStateManager.resetColor();
        GlStateManager.color(1, 1, 1, 1);
        drawProgress();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.popMatrix();
        mc.updateDisplay();
    }

    public void drawProgress() {
        float calc = loadingProgress / 12f * (Screen.sr.getScaledWidth() - 20);
        GlStateManager.resetColor();
        //GlStateManager.TextureState.textureName = -1;
        //Render.drawBorderedRect(Screen.sr.getScaledWidth() / 2f - 50 / 2f, Screen.sr.getScaledHeight() / 2f - 50 / 2f, 50, 50, 4.5f, Color.hex(0xFF3b3b40, 242));
        new Button(28, Screen.sr.getScaledWidth() / 2f - 50 / 2f, Screen.sr.getScaledHeight() / 2f - 50 / 2f, 50, 50, Color.hex(0xFF3b3b40, 242), "V").drawButton();
        Rounded.drawRound(0, Screen.sr.getScaledHeight() - 3.5f, 0 + calc, 8, 0, Color.hex(0xFF3b3b40, 242));
    }

}
