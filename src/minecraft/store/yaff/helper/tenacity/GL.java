package store.yaff.helper.tenacity;

import net.minecraft.client.renderer.GlStateManager;

import static org.lwjgl.opengl.GL11.*;

public class GL {

    public static void setup2DRendering(Runnable runnable) {
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        runnable.run();
        glEnable(GL_TEXTURE_2D);
        GlStateManager.disableBlend();
        glPopMatrix();
    }

    public static void setup3DRendering(Runnable runnable) {
        glPushMatrix();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glLineWidth(2);
        runnable.run();
        glLineWidth(2);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        GlStateManager.disableBlend();
        glPopMatrix();
    }

}
