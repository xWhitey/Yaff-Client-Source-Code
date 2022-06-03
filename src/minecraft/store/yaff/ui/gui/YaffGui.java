package store.yaff.ui.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import store.yaff.Yaff;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.render.Gui;
import store.yaff.file.impl.Configuration;
import store.yaff.font.TTFFontManager;
import store.yaff.helper.Color;
import store.yaff.helper.render.Render;
import store.yaff.helper.Screen;
import store.yaff.helper.Time;
import store.yaff.setting.AbstractSetting;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class YaffGui extends GuiContainer {
    private final Time timeManager = new Time();
    public boolean isOnScreen = false;
    public boolean isMoving = false;
    public int mWheel = 0;
    public float dragX, dragY;
    public float moduleScrollY, moduleScrollYNow;
    public float settingScrollY, settingScrollYNow;
    public int valueX = 0;
    public float valueY = 0;

    @Override
    public void initGui() {
        isOnScreen = true;
        isMoving = false;
        timeManager.reset();
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        desaturation.setSaturation(0);
        Render.drawRoundedRectXY(0, 0, Screen.sr.getScaledWidth(), Screen.sr.getScaledHeight(), 0, Color.hex(0xFF8585c7, 40));
        if (!isOnScreen) {
            mc.displayGuiScreen(null);
        }
        mWheel = Mouse.getDWheel();
        if (isTriggered(windowX + 12, windowY + 12, windowX + 24, windowY + 24, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
            isMoving = true;
        }
        if (isMoving) {
            if (dragX == 0 && dragY == 0) {
                dragX = MathHelper.clamp(mouseX - windowX, 0, Screen.sr.getScaledWidth() - (currentFeature != null && currentFeature.getSettings().size() > 0 ? width + 15 + 170 : width));
                dragY = MathHelper.clamp(mouseY - windowY, 0, Screen.sr.getScaledHeight() - height);
            } else {
                windowX = MathHelper.clamp(mouseX - dragX, 0, Screen.sr.getScaledWidth() - (currentFeature != null && currentFeature.getSettings().size() > 0 ? width + 15 + 170 : width));
                windowY = MathHelper.clamp(mouseY - dragY, 0, Screen.sr.getScaledHeight() - height);
            }
        } else if (dragX != 0 || dragY != 0) {
            dragX = 0;
            dragY = 0;
        }
        Render.drawRoundedRect(windowX, windowY, windowX + width, windowY + height, 4, Color.rgb(21, 22, 25));
        Render.drawRoundedRectXY(windowX + 12, windowY + 12, 12, 12, 3, Color.hex(0xFF8585c7));
        float tempX = 47.5f;
        for (Category c : Category.values()) {
            Render.drawRoundedRectXY(windowX + tempX, windowY + 32.5f, 30, 30, 4, Color.hex(0xFF9E9ED2));
            TTFFontManager.faRenderer33I.drawString(c.getIcon(), (windowX + tempX) + (30 / 2f - TTFFontManager.faRenderer33I.getWidth(c.getIcon()) / 2f), (windowY + 32.5f) + (30 / 2f - TTFFontManager.faRenderer33I.getHeight(c.getIcon()) / 2f), Color.hex(0xFFeeeeee));
            if (c != currentCategory) {
                if (isTriggered(windowX + tempX, windowY + 32.5f, windowX + tempX + 30, windowY + 32.5f + 30, mouseX, mouseY)) {
                    Render.drawRoundedRectXY(windowX + tempX, windowY + 32.5f, 30, 30, 4, Color.hex(0xFF9E9ED2));
                } else {
                    Render.drawRoundedRectXY(windowX + tempX, windowY + 32.5f, 30, 30, 4, Color.hex(0xFF7D7DA3));
                }
                TTFFontManager.faRenderer33I.drawString(c.getIcon(), (windowX + tempX) + (30 / 2f - TTFFontManager.faRenderer33I.getWidth(c.getIcon()) / 2f), (windowY + 32.5f) + (30 / 2f - TTFFontManager.faRenderer33I.getHeight(c.getIcon()) / 2f), Color.hex(0xFFE5E5F3));
            }
            tempX += width / Category.values().length - 9.7f;
        }
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(0, 2 * ((int) (Screen.sr.getScaledHeight_double() - (windowY + height))) + 70, (int) (Screen.sr.getScaledWidth_double() * 2), (int) ((height) * 2) - 260);
        float modY = windowY + 105.5f + moduleScrollYNow;
        CopyOnWriteArrayList<AbstractFeature> featureList = Objects.requireNonNull(Yaff.of.featureManager.getFeatures());
        featureList.sort(Comparator.comparing(AbstractFeature::getName));
        for (AbstractFeature f : featureList) {
            if (f.getCategory() != currentCategory) {
                continue;
            }
            if (isTriggered(windowX + 35 + valueX, windowY + 96, windowX + 425 + valueX, windowY + 96 + 40 * 4, mouseX, mouseY) && isTriggered(windowX + 35 + valueX, modY - 10, windowX + 425 + valueX, modY + 25, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                if (timeManager.hasReached(140) && modY + 40 > (windowY + 70) && modY < (windowY + height)) {
                    f.toggle();
                    timeManager.reset();
                }
            }
            if (isTriggered(windowX + 35 + valueX, windowY + 96, windowX + 425 + valueX, windowY + 96 + 40 * 4, mouseX, mouseY) && timeManager.hasReached(140) && isTriggered(windowX + 35 + valueX, modY - 10, windowX + 425 + valueX, modY + 25, mouseX, mouseY) && Mouse.isButtonDown(1) && !isMoving) {
                if (currentFeature == f) {
                    currentFeature = null;
                } else {
                    settingScrollY = 0;
                    currentFeature = f;
                }
                timeManager.reset();
            }
            if (isTriggered(windowX + 35 + valueX, windowY + 96, windowX + 425 + valueX, windowY + 96 + 40 * 4, mouseX, mouseY) && isTriggered(windowX + 35 + valueX, modY - 10, windowX + 425 + valueX, modY + 25, mouseX, mouseY) && !isMoving) {
                Render.drawRoundedRect(windowX + 35 + valueX, modY - 10, windowX + 425 + valueX, modY + 25, 3, Color.rgb(43, 41, 45));
            } else {
                if (f.getState()) {
                    Render.drawRoundedRect(windowX + 35 + valueX, modY - 10, windowX + 425 + valueX, modY + 25, 3, Color.rgb(36, 34, 38));
                } else {
                    Render.drawRoundedRect(windowX + 35 + valueX, modY - 10, windowX + 425 + valueX, modY + 25, 3, Color.rgb(32, 31, 33));
                }
            }
            Render.drawRoundedRect(windowX + 35 + valueX, modY - 10, windowX + 60 + valueX, modY + 25, 3, Color.rgb(37, 35, 39));
            Render.drawRoundedRect(windowX + 410 + valueX, modY - 10, windowX + 425 + valueX, modY + 25, 3, Color.rgb(39, 38, 42));
            TTFFontManager.fontRenderer20SGM.drawString(".", windowX + 416 + valueX, modY - 5, Color.rgb(66, 64, 70));
            TTFFontManager.fontRenderer20SGM.drawString(".", windowX + 416 + valueX, modY - 1, Color.rgb(66, 64, 70));
            TTFFontManager.fontRenderer20SGM.drawString(".", windowX + 416 + valueX, modY + 3, Color.rgb(66, 64, 70));
            TTFFontManager.fontRenderer20SGM.drawString(f.getDescription() + ".", windowX + 160 + valueX, modY + (TTFFontManager.fontRenderer20SGM.getHeight(f.getDescription()) / 4f), Color.rgb(94, 95, 98));
            if (f.getState()) {
                TTFFontManager.fontRenderer20SGM.drawString(f.getName(), windowX + 75 + valueX, modY + (TTFFontManager.fontRenderer20SGM.getHeight(f.getName()) / 4f), Color.rgb(220, 220, 220));
                Render.drawRoundedRect(windowX + 35 + valueX, modY - 10, windowX + 60 + valueX, modY + 25, 3, Color.hex(0xFF8585c7, (int) (f.animationDelayAfter / 100f * 255)));
                //Render.drawImage(windowX + 105 + valueX, modY, 16, 16, new ResourceLocation("client/vapeclickgui/feature.png"), Color.rgb(220, 220, 220));
                f.animationDelay = 100;
                Render.drawRoundedRect(windowX + 390 + valueX, modY + 2, windowX + 400 + valueX, modY + 12, 3, Color.hex(0xFF8585c7));
            } else {
                TTFFontManager.fontRenderer20SGM.drawString(f.getName(), windowX + 75 + valueX, modY + (TTFFontManager.fontRenderer20SGM.getHeight(f.getName()) / 4f), Color.rgb(108, 109, 113));
                //Render.drawImage(windowX + 105 + valueX, modY, 16, 16, new ResourceLocation("client/vapeclickgui/feature.png"), Color.rgb(92, 90, 94));
                f.animationDelay = 0;
                Render.drawRoundedRect(windowX + 390 + valueX, modY + 2, windowX + 400 + valueX, modY + 12, 3, Color.rgb(59, 60, 65));
            }
            if (f.animationDelayAfter != f.animationDelay) {
                f.animationDelayAfter += (f.animationDelay - f.animationDelayAfter) / 8;
            }
            modY += 40;
        }
        if (isTriggered(windowX + 35 + valueX, windowY + 60, windowX + 425 + valueX, windowY + height, mouseX, mouseY)) {
            if (mWheel < 0 && Math.abs(moduleScrollY) + 220 < ((Objects.requireNonNull(Yaff.of.featureManager.getFeatures(currentCategory)).size() + 2) * 40)) {
                moduleScrollY -= 32;
            }
            if (mWheel > 0 && moduleScrollY < 0) {
                moduleScrollY += 32;
            }
        }
        if (moduleScrollYNow != moduleScrollY) {
            moduleScrollYNow += (moduleScrollY - moduleScrollYNow) / 15;
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        if (currentFeature != null && currentFeature.getSettings().size() > 0) {
            Render.drawRoundedRectXY(windowX + width + 15, windowY, 170, height, 4, Color.rgb(21, 22, 25));
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(0, 2 * ((int) (Screen.sr.getScaledHeight_double() - (windowY + height))) + 30, (int) (Screen.sr.getScaledWidth_double() * 2), (int) ((height) * 2) - 60);
            if (isTriggered(windowX + width + 15, windowY, windowX + width + 15 + 170, windowY + height, mouseX, mouseY)) {
                if (mWheel < 0 && Math.abs(settingScrollY) + 170 < ((currentFeature.getSettings().stream().filter(s -> !(s instanceof ListSetting)).toList().size() + currentFeature.getSettings().stream().filter(s -> s instanceof ListSetting).mapToInt(s -> ((ListSetting) s).getListValues().size()).sum()) * 23)) {
                    settingScrollY -= 32;
                }
                if (mWheel > 0 && settingScrollY < 0) {
                    settingScrollY += 32;
                }
            }
            if (settingScrollYNow != settingScrollY) {
                settingScrollYNow += (settingScrollY - settingScrollYNow) / 15;
            }
            valueY = windowY + 15 + settingScrollYNow;
            for (AbstractSetting s : currentFeature.getSettings()) {
                if (s.getVisible().get()) {
                    TTFFontManager.fontRenderer20SGM.drawString(s.getName(), windowX + width + 30, valueY + 4, -1);
                    if (s instanceof BooleanSetting b) {
                        Render.drawRoundedRectXY(windowX + width + 185 - 27.5f, valueY + 4, 12, 12, 3, Color.hex(0xFF8585c7));
                        if (!b.getBooleanValue()) {
                            Render.drawRoundedRectXY(windowX + width + 185 - 27.5f, valueY + 4, 12, 12, 3, Color.rgb(59, 60, 65));
                        }
                        if (isTriggered(windowX + width + 185 - 27.5f, valueY + 4, windowX + width + 185 - 25 + 12, valueY + 16, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                            if (timeManager.hasReached(140)) {
                                b.setBooleanValue(!b.getBooleanValue());
                                Configuration.saveConfiguration();
                                timeManager.reset();
                            }
                        }
                        valueY += 20;
                    }
                    if (s instanceof NumericSetting n) {
                        float present = ((windowX + width + 170) - (windowX + width + 30)) * (n.getNumericValue() - n.getMinimumValue()) / (n.getMaximumValue() - n.getMinimumValue());
                        TTFFontManager.fontRenderer20SGM.drawString(String.valueOf(store.yaff.helper.Math.dFormat.format(n.getNumericValue())), windowX + width + 185 - 14 - TTFFontManager.fontRenderer20SGM.getWidth(String.valueOf(store.yaff.helper.Math.dFormat.format(n.getNumericValue()))), valueY + 4, -1);
                        Render.drawRoundedRectXY(windowX + width + 30, valueY + 20, 140, 3, 3, Color.rgb(59, 60, 65));
                        if (n.getNumericValue() > n.getMinimumValue()) {
                            Render.drawRoundedRectXY(windowX + width + 30, valueY + 20, present, 3, 3, Color.hex(0xFF8585c7));
                        }
                        if (isTriggered(windowX + width + 30, valueY + 18, windowX + width + 170, valueY + 23.5f, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                            float increment = n.getIncrementValue();
                            float percentage = Math.min(Math.max(0, (mouseX - (windowX + width + 30)) / (((windowX + width + 170) - (windowX + width + 30)))), 1);
                            float currentValue = Math.round((n.getMinimumValue() + ((n.getMaximumValue() - n.getMinimumValue()) * percentage)) * (1 / increment)) / (1 / increment);
                            n.setNumericValue(MathHelper.clamp(currentValue, n.getMinimumValue(), n.getMaximumValue()));
                            Configuration.saveConfiguration();
                        }
                        valueY += 30;
                    }
                    if (s instanceof ListSetting l) {
                        for (String sl : l.getListValues()) {
                            valueY += 20;
                            TTFFontManager.fontRenderer20SGM.drawString(sl, windowX + width + 50, valueY + 5, -1);
                            Render.drawRoundedRectXY(windowX + width + 30, valueY + 4, 12, 12, 3, Color.hex(0xFF8585c7));
                            if (!sl.equalsIgnoreCase(l.getListValue())) {
                                Render.drawRoundedRectXY(windowX + width + 30, valueY + 4, 12, 12, 3, Color.rgb(59, 60, 65));
                            }
                            if (isTriggered(windowX + width + 30, valueY + 4, windowX + width + 30 + 12, valueY + 16, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                                if (timeManager.hasReached(140)) {
                                    l.setListValue(sl);
                                    Configuration.saveConfiguration();
                                    timeManager.reset();
                                }
                            }
                        }
                        valueY += 24;
                    }
                    GL11.glColor4f(1, 1, 1, 1);
                }
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
        if (isTriggered(windowX + 100 + valueX, windowY + 60, windowX + 425 + valueX, windowY + height, mouseX, mouseY)) {
            if (mWheel < 0 && Math.abs(moduleScrollY) + 220 < ((Objects.requireNonNull(Yaff.of.featureManager.getFeatures(currentCategory)).size() + 2) * 40)) {
                moduleScrollY -= 16;
            }
            if (mWheel > 0 && moduleScrollY < 0) {
                moduleScrollY += 16;
            }
        }
        if (moduleScrollYNow != moduleScrollY) {
            moduleScrollYNow += (moduleScrollY - moduleScrollYNow) / 15;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        float tempX = 47.5f;
        for (Category m : Category.values()) {
            if (isTriggered(windowX + tempX, windowY + 32.5f, windowX + tempX + 30f, windowY + 32.5f + 30f, mouseX, mouseY)) {
                if (currentCategory != m) {
                    moduleScrollY = 0;
                }
                currentCategory = m;
            }
            tempX += width / Category.values().length - 9.7f;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (isMoving) {
            isMoving = false;
        }
        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if (isOnScreen && (keyCode == Objects.requireNonNull(Yaff.of.featureManager.getFeature(Gui.class)).getKey() || keyCode == Keyboard.KEY_ESCAPE)) {
            isOnScreen = false;
            mc.mouseHelper.grabMouseCursor();
            mc.inGameHasFocus = true;
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void onResize(@Nullable Minecraft mcIn, int w, int h) {
        windowX = MathHelper.clamp(windowX, 0, Screen.sr.getScaledWidth() - (currentFeature != null && currentFeature.getSettings().size() > 0 ? width + 15 + 170 : width));
        windowY = MathHelper.clamp(windowY, 0, Screen.sr.getScaledHeight() - height);
    }

    public boolean isTriggered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

}
