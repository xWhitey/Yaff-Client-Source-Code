package store.yaff.ui.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import store.yaff.Yaff;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.render.GUI;
import store.yaff.file.impl.Configuration;
import store.yaff.font.TTFFontManager;
import store.yaff.helper.Color;
import store.yaff.helper.Screen;
import store.yaff.helper.Time;
import store.yaff.helper.tenacity.Rounded;
import store.yaff.helper.tenacity.Stencil;
import store.yaff.setting.AbstractSetting;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class NewYaffGui extends GuiContainer {
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

    /*
        desaturation.setSaturation(0);
        Rounded.drawRound(0, 0, Screen.sr.getScaledWidth(), Screen.sr.getScaledHeight(), 0, Color.hex(0xFF8585c7, 40));
        if (!isOnScreen) {
            mc.displayGuiScreen(null);
        }
        mWheel = Mouse.getDWheel();
        if (isTriggered(windowX, windowY + 10, windowX + 120, windowY + 10 + TTFFontManager.fontRenderer24MSB.getHeight(Yaff.of.name), mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
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
        Rounded.drawRound(windowX, windowY - 1, width, 6, 6, Color.hex(0xFF8585c7));
        Rounded.drawRound(windowX, windowY, width, height, 5, Color.hex(0xFF111111));
        Rounded.drawRound(windowX + 10, windowY + 38.5f - 5, 100, Category.values().length * (height / Category.values().length - 8.2f) + 5, 5, Color.rgb(21, 21, 22));
        TTFFontManager.fontRenderer24MSB.drawString(Yaff.of.name, windowX + 60 - TTFFontManager.fontRenderer24MSB.getWidth(Yaff.of.name) / 2f + 0.1f - 10, windowY + 10, Color.hex(0xFF8585c7));
        Color.glColor(Color.hex(0xFFeeeeee, 220));
        TTFFontManager.fontRenderer16SGM.drawString("beta", windowX + 60 - TTFFontManager.fontRenderer16SGM.getWidth("beta") / 2f + 0.1f + 15, windowY + 10, Color.hex(0xFFeeeeee, 30));
        float tempY = 38.5f;
        for (Category c : Category.values()) {
            if (c == currentCategory) {
                Color.glColor(Color.hex(0xFF8585c7));
                Rounded.drawRound(windowX + 10, windowY + tempY + 16 - TTFFontManager.fontRenderer16SGM.getHeight(c.getName()) - 7, 100, 14 + TTFFontManager.fontRenderer16SGM.getHeight(c.getName()), 0, Color.hex(0xFF8585c7));
            }
            Color.glColor(Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            TTFFontManager.faRenderer16I.drawString(c.getIcon(), windowX + 16 + 8 - TTFFontManager.faRenderer16I.getWidth(c.getIcon()) / 2f + 1, windowY + tempY + 16 - TTFFontManager.fontRenderer16SGM.getHeight(c.getIcon()) + 1, Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            Color.glColor(Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            TTFFontManager.fontRenderer16SGM.drawString(c.getName(), windowX + 16 + 18, windowY + tempY + 16 - TTFFontManager.fontRenderer16SGM.getHeight(c.getName()), Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            tempY += height / Category.values().length - 8.2f;
        }
        TTFFontManager.fontRenderer16SGM.drawString("Build " + Yaff.of.build, windowX + 4, windowY + height - TTFFontManager.fontRenderer16SGM.getHeight("Build " + Yaff.of.build) - 4, Color.hex(0xFFeeeeee, 30));
        super.drawScreen(mouseX, mouseY, partialTicks);
     */

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        desaturation.setSaturation(0);
        Rounded.drawRound(0, 0, Screen.sr.getScaledWidth(), Screen.sr.getScaledHeight(), 0, Color.hex(0xFF8585c7, 40));
        if (!isOnScreen) {
            mc.displayGuiScreen(null);
        }
        mWheel = Mouse.getDWheel();
        if (isTriggered(windowX, windowY + 10, windowX + 120, windowY + 10 + TTFFontManager.fontRenderer24MSB.getHeight(Yaff.of.name), mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
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
        Rounded.drawRound(windowX, windowY - 1, width, 6, 4, Color.hex(0xFF8585c7));
        Rounded.drawRound(windowX, windowY, width, height, 3, Color.rgb(25, 25, 25));
        //Rounded.drawRound(windowX, windowY, 120, height, 5, Color.rgb(29, 29, 29));
        Rounded.drawRound(windowX + 15, windowY + 38.5f - 5, 100, Category.values().length * (height / Category.values().length - 8.2f) + 5, 3, Color.rgb(32, 32, 32));
        float modY = windowY + 38.5f + moduleScrollYNow;
        CopyOnWriteArrayList<AbstractFeature> featureList = Objects.requireNonNull(Yaff.of.featureManager.getFeatures());
        featureList.sort(Comparator.comparing(AbstractFeature::getName));
        Stencil.write(false);
        Rounded.drawRound(windowX + 15 + 120, windowY + 38.5f - 5, 240, Category.values().length * (height / Category.values().length - 8.2f) + 5, 0, Color.hex(0xFF000000));
        Stencil.erase(true);
        for (AbstractFeature f : featureList) {
            if (f.getCategory() != currentCategory) {
                continue;
            }
            if (isTriggered(windowX + 15 + 120, modY - 5, windowX + 15 + 120 + 240, modY - 5 + 28, mouseX, mouseY) && isTriggered(windowX + 15 + 120, windowY + 38.5f - 5, windowX + 15 + 120 + 240, windowY + 38.5f - 5 + Category.values().length * (height / Category.values().length - 8.2f) + 5, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                if (timeManager.hasReached(140) && modY + 40 > (windowY + 70) && modY < (windowY + height)) {
                    f.toggle();
                    timeManager.reset();
                }
            }
            if (isTriggered(windowX + 15 + 120, modY - 5, windowX + 15 + 120 + 240, modY - 5 + 28, mouseX, mouseY) && isTriggered(windowX + 15 + 120, windowY + 38.5f - 5, windowX + 15 + 120 + 240, windowY + 38.5f - 5 + Category.values().length * (height / Category.values().length - 8.2f) + 5, mouseX, mouseY) && Mouse.isButtonDown(1) && !isMoving) {
                if (timeManager.hasReached(140)) {
                    if (currentFeature == f) {
                        currentFeature = null;
                    } else if (f.getSettings().size() > 0) {
                        settingScrollY = 0;
                        currentFeature = f;
                    }
                    timeManager.reset();
                }
            }
            Rounded.drawRound(windowX + 15 + 120, modY - 5, 240, 28, 3, Color.rgb(32, 32, 32));
            Rounded.drawRound(windowX + 15 + 120 + 12, modY - 5 + 10, 8, 8, 1, f.getState() ? Color.hex(0xFF8585c7, (int) (f.animationDelayAfter / 100f * 255)) : Color.rgb(44, 44, 45));
            f.animationDelay = f.getState() ? 100 : 0;
            if (f.animationDelayAfter != f.animationDelay) {
                f.animationDelayAfter += (f.animationDelay - f.animationDelayAfter) / 8;
            }
            Color.glColor(Color.hex(0xFFeeeeee, 180));
            TTFFontManager.fontRenderer16SGM.drawString(f.getName() + " - " + f.getDescription() + ".", windowX + 15 + 120 + 30, modY - 5 + 28 / 2f - TTFFontManager.fontRenderer16SGM.getHeight(f.getName() + " - " + f.getDescription() + ".") / 2f, Color.hex(0xFFeeeeee, 180));
            modY += 38;
        }
        Stencil.dispose();
        if (currentFeature != null) {
            Rounded.drawRound(windowX + width + 18, windowY, 150, height, 3, Color.rgb(25, 25, 25));
            valueY = windowY + 8 + settingScrollYNow;
            Stencil.write(false);
            Rounded.drawRound(windowX + width + 18, windowY, 150, height, 3, Color.hex(0xFF000000));
            Stencil.erase(true);
            for (AbstractSetting s : currentFeature.getSettings()) {
                if (s.getVisible().get()) {
                    Color.glColor(Color.hex(0xFFeeeeee, 180));
                    TTFFontManager.fontRenderer16SGM.drawString(s.getName(), windowX + width + 18 + 12, valueY + 4, Color.hex(0xFFeeeeee, 180));
                    if (s instanceof BooleanSetting b) {
                        Rounded.drawRound(windowX + width + 18 + 150 - 20, valueY + 4, 8, 8, 1, b.getBooleanValue() ? Color.hex(0xFF8585c7) : Color.rgb(59, 60, 65));
                        if (isTriggered(windowX + width + 18 + 150 - 20, valueY + 4, windowX + width + 18 + 150 - 20 + 12, valueY + 12, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                            if (timeManager.hasReached(140)) {
                                b.setBooleanValue(!b.getBooleanValue());
                                Configuration.saveConfiguration();
                                timeManager.reset();
                            }
                        }
                        valueY += 16;
                    }
                    if (s instanceof NumericSetting n) {
                        float present = ((windowX + width + 30 + 128) - (windowX + width + 30)) * (n.getNumericValue() - n.getMinimumValue()) / (n.getMaximumValue() - n.getMinimumValue());
                        Color.glColor(Color.hex(0xFFeeeeee, 180));
                        TTFFontManager.fontRenderer16SGM.drawString(String.valueOf(MathHelper.dFormat.format(n.getNumericValue())), windowX + width + 18 + 150 - 8 - TTFFontManager.fontRenderer16SGM.getWidth(String.valueOf(MathHelper.dFormat.format(n.getNumericValue()))), valueY + 4, Color.hex(0xFFeeeeee, 180));
                        Rounded.drawRound(windowX + width + 30, valueY + 20, 128, 2, 1, Color.rgb(59, 60, 65));
                        if (n.getNumericValue() > n.getMinimumValue()) {
                            Rounded.drawRound(windowX + width + 30, valueY + 20, present, 2, 1, Color.hex(0xFF8585c7));
                        }
                        if (isTriggered(windowX + width + 30, valueY + 18, windowX + width + 30 + 128, valueY + 23.5f, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                            float increment = n.getIncrementValue();
                            float percentage = Math.min(Math.max(0, (mouseX - (windowX + width + 30)) / (((windowX + width + 30 + 128) - (windowX + width + 30)))), 1);
                            float currentValue = Math.round((n.getMinimumValue() + ((n.getMaximumValue() - n.getMinimumValue()) * percentage)) * (1 / increment)) / (1 / increment);
                            n.setNumericValue(MathHelper.clamp(currentValue, n.getMinimumValue(), n.getMaximumValue()));
                            Configuration.saveConfiguration();
                        }
                        valueY += 26;
                    }
                    if (s instanceof ListSetting l) {
                        for (String sl : l.getListValues()) {
                            valueY += 16;
                            Color.glColor(Color.hex(0xFFeeeeee, 180));
                            TTFFontManager.fontRenderer16SGM.drawString(sl, windowX + width + 45, valueY + 4, Color.hex(0xFFeeeeee, 180));
                            Rounded.drawRound(windowX + width + 30, valueY + 4, 8, 8, 1, sl.equalsIgnoreCase(l.getListValue()) ? Color.hex(0xFF8585c7) : Color.rgb(59, 60, 65));
                            if (isTriggered(windowX + width + 30, valueY + 4, windowX + width + 30 + 8, valueY + 12, mouseX, mouseY) && Mouse.isButtonDown(0) && !isMoving) {
                                if (timeManager.hasReached(140)) {
                                    l.setListValue(sl);
                                    Configuration.saveConfiguration();
                                    timeManager.reset();
                                }
                            }
                        }
                        valueY += 18;
                    }
                    GL11.glColor4f(1, 1, 1, 1);
                }
            }
            Stencil.dispose();
            if (isTriggered(windowX + width + 18, windowY, windowX + width + 18 + 150, windowY + height, mouseX, mouseY)) {
                if (mWheel < 0 && Math.abs(settingScrollY) + 170 < ((currentFeature.getSettings().stream().filter(s -> !(s instanceof ListSetting)).toList().size() + currentFeature.getSettings().stream().filter(s -> s instanceof ListSetting).mapToInt(s -> ((ListSetting) s).getListValues().size()).sum()) * 20)) {
                    settingScrollY -= 32;
                }
                if (mWheel > 0 && settingScrollY < 0) {
                    settingScrollY += 32;
                }
            }
            if (settingScrollYNow != settingScrollY) {
                settingScrollYNow += (settingScrollY - settingScrollYNow) / 15;
            }
        }
        if (isTriggered(windowX + 15 + 120, windowY + 38.5f - 5, windowX + 15 + 120 + 240, windowY + 38.5f - 5 + Category.values().length * (height / Category.values().length - 8.2f) + 5, mouseX, mouseY)) {
            if (mWheel < 0 && Math.abs(moduleScrollY) + 220 < ((Objects.requireNonNull(Yaff.of.featureManager.getFeatures(currentCategory)).size() + 2) * 37)) {
                moduleScrollY -= 32;
            }
            if (mWheel > 0 && moduleScrollY < 0) {
                moduleScrollY += 32;
            }
        }
        if (moduleScrollYNow != moduleScrollY) {
            moduleScrollYNow += (moduleScrollY - moduleScrollYNow) / 15;
        }
        TTFFontManager.fontRenderer24MSB.drawString(Yaff.of.name, windowX + 65 - TTFFontManager.fontRenderer24MSB.getWidth(Yaff.of.name) / 2f + 0.1f - 10, windowY + 10, Color.hex(0xFF8585c7));
        Color.glColor(Color.hex(0xFFeeeeee, 220));
        TTFFontManager.fontRenderer16SGM.drawString("beta", windowX + 65 - TTFFontManager.fontRenderer16SGM.getWidth("beta") / 2f + 0.1f + 15, windowY + 10, Color.hex(0xFFeeeeee, 30));
        float tempY = 38.5f;
        for (Category c : Category.values()) {
            if (c == currentCategory) {
                Color.glColor(Color.hex(0xFF8585c7));
                Rounded.drawRound(windowX + 15, windowY + tempY + 16 - TTFFontManager.fontRenderer16SGM.getHeight(c.getName()) - 6, 100, 12 + TTFFontManager.fontRenderer16SGM.getHeight(c.getName()), 0, Color.hex(0xFF8585c7));
            }
            Color.glColor(Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            TTFFontManager.faRenderer16I.drawString(c.getIcon(), windowX + 21 + 8 - TTFFontManager.faRenderer16I.getWidth(c.getIcon()) / 2f + 1, windowY + tempY + 16 - TTFFontManager.fontRenderer16SGM.getHeight(c.getIcon()) + 1, Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            Color.glColor(Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            TTFFontManager.fontRenderer16SGM.drawString(c.getName(), windowX + 21 + 18, windowY + tempY + 16 - TTFFontManager.fontRenderer16SGM.getHeight(c.getName()), Color.hex(0xFFeeeeee, c == currentCategory ? 220 : 180));
            tempY += height / Category.values().length - 8.2f;
            GL11.glColor4f(1, 1, 1, 1);
        }
        TTFFontManager.fontRenderer16SGM.drawString("Build " + Yaff.of.build, windowX + 4, windowY + height - TTFFontManager.fontRenderer16SGM.getHeight("Build " + Yaff.of.build) - 4, Color.hex(0xFFeeeeee, 30));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        float tempY = 38.5f;
        for (Category c : Category.values()) {
            if (isTriggered(windowX, windowY + tempY + 16 - TTFFontManager.fontRenderer16SGM.getHeight(c.getName()) - 7, windowX + 120, windowY + tempY + 16 - 7 + 14, mouseX, mouseY)) {
                if (currentCategory != c) {
                    moduleScrollY = 0;
                }
                currentCategory = c;
            }
            tempY += height / Category.values().length - 8.2f;
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
        if (isOnScreen && (keyCode == Objects.requireNonNull(Yaff.of.featureManager.getFeature(GUI.class)).getKey() || keyCode == Keyboard.KEY_ESCAPE)) {
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
