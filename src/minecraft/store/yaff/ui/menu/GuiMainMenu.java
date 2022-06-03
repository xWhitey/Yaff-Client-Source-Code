package store.yaff.ui.menu;

import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import store.yaff.Yaff;
import store.yaff.feature.impl.render.NameProtect;
import store.yaff.font.TTFFontManager;
import store.yaff.helper.Color;
import store.yaff.helper.Render;
import store.yaff.helper.Screen;
import store.yaff.helper.tenacity.Rounded;
import store.yaff.ui.button.Button;

import java.io.IOException;
import java.util.Objects;

public class GuiMainMenu extends GuiContainer {
    @Override
    public void initGui() {
        settingsButton.setY(Screen.sr.getScaledHeight() / 2f - (settingsButton.getHeight() / 2f));
        multiplayerButton.setY(settingsButton.getY() - multiplayerButton.getHeight() - 14);
        singleplayerButton.setY(multiplayerButton.getY() - singleplayerButton.getHeight() - 14);
        langButton.setY(settingsButton.getY() + langButton.getHeight() + 14);
        altButton.setY(langButton.getY() + altButton.getHeight() + 14);
        for (Button b : buttonList) {
            b.setX(Screen.sr.getScaledWidth() - 100);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        backgroundShader.useShader(this.width + Screen.sr.getScaledWidth(), this.height + Screen.sr.getScaledHeight(), mouseX, mouseY, (System.currentTimeMillis() - initTime) / 1000f);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(-1f, -1f);
        GL11.glVertex2f(-1f, 1f);
        GL11.glVertex2f(1f, 1f);
        GL11.glVertex2f(1f, -1f);
        GL11.glEnd();
        GL20.glUseProgram(0);
        Rounded.drawRound(0, 0, Screen.sr.getScaledWidth(), Screen.sr.getScaledHeight(), 0, Color.hex(0xFF8585c7, 10));
        GlStateManager.enableBlend();
        Color.glColor(Color.hex(0xFF3b3b40, 242));
        //TTFFontManager.fontRenderer24MSB.drawString("With <3 by zesho & SteamJays", 12, 10, Color.hex(0xFF3b3b40, 242));
        Yaff.of.changelogManager.renderChangelog();
        Color.glColor(Color.hex(0xFF3b3b40, 242));
        TTFFontManager.fontRenderer24MSB.drawString("Build " + Yaff.of.build, 12, Render.sr.getScaledHeight() - TTFFontManager.fontRenderer24MSB.getHeight("Build " + Yaff.of.build) - 27, Color.hex(0xFF3b3b40, 242));
        Color.glColor(Color.hex(0xFF3b3b40, 242));
        TTFFontManager.fontRenderer24MSB.drawString("Welcome, " + (Objects.requireNonNull(Yaff.of.featureManager.getFeature(NameProtect.class)).getState() ? "[" + NameProtect.protectedName + "]" : mc.getSession().getUsername()) + "!", 12, Render.sr.getScaledHeight() - TTFFontManager.fontRenderer24MSB.getHeight("Welcome, " + (Objects.requireNonNull(Yaff.of.featureManager.getFeature(NameProtect.class)).getState() ? NameProtect.protectedName : mc.getSession().getUsername()) + "!") - 12, Color.hex(0xFF3b3b40, 242));
        for (Button b : buttonList) {
            b.setColor(b.isTriggered(mouseX, mouseY) ? Color.hex(0xFF8585c7, 242) : Color.hex(0xFF3b3b40, 242));
            b.drawButton();
        }
        GlStateManager.popMatrix();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if ((Mouse.isButtonDown(0) || Mouse.isButtonDown(1))) {
            for (Button b : buttonList) {
                switch (b.getClickId(mouseX, mouseY)) {
                    case 1 -> mc.displayGuiScreen(new GuiWorldSelection(this));
                    case 2 -> mc.displayGuiScreen(new GuiMultiplayer(this));
                    case 3 -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                    case 4 -> mc.displayGuiScreen(new GuiLanguage(this, mc.gameSettings, mc.getLanguageManager()));
                    case 5 -> mc.shutdown();
                    case 6 -> mc.displayGuiScreen(new GuiAltLogin(this));
                }
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

}
