package store.yaff.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import store.yaff.helper.Screen;

import java.util.Objects;

public class Desaturation {
    public final Minecraft mc = Minecraft.getMinecraft();
    public final ResourceLocation resourceLocation = new ResourceLocation("yaff/shaders/desaturate.json");
    public ShaderGroup shaderGroup;
    public Framebuffer framebuffer;

    public void onInit() {
        try {
            shaderGroup = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), resourceLocation);
            shaderGroup.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            framebuffer = new Framebuffer(Screen.sr.getScaledWidth() * ScaledResolution.getScaleFactor(), Screen.sr.getScaledHeight() * ScaledResolution.getScaleFactor(), true);
            framebuffer.bindFramebuffer(false);
        } catch (Exception ignored) {
        }
    }

    public void setSaturation(float saturation) {
        if (Screen.isSizeChanged() || framebuffer == null || shaderGroup == null) {
            onInit();
        }
        GL11.glPushMatrix();
        framebuffer.bindFramebuffer(true);
        shaderGroup.loadShaderGroup(mc.timer.field_194147_b);
        Objects.requireNonNull(shaderGroup.getShaders().get(0).getShaderManager().getShaderUniform("Saturation")).set(saturation);
        mc.getFramebuffer().bindFramebuffer(false);
        GL11.glPopMatrix();
    }

}
