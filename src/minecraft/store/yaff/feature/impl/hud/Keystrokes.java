package store.yaff.feature.impl.hud;

import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.font.TTFFontManager;
import store.yaff.helper.Color;
import store.yaff.helper.tenacity.Rounded;

public class Keystrokes extends AbstractFeature {
    protected int keyF = 19, keyL = 19, keyB = 19, keyR = 19, keyA = 19, keyU = 19;

    public Keystrokes(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof store.yaff.event.impl.Render.Render2D renderEvent) {
            keyF = MathHelper.clamp(keyF != (mc.gameSettings.keyBindForward.pressed ? 255 : 0) ? keyF + ((mc.gameSettings.keyBindForward.pressed ? 255 : 0) - keyF) / 6 : 19, 19, 180);
            keyL = MathHelper.clamp(keyL != (mc.gameSettings.keyBindLeft.pressed ? 255 : 0) ? keyL + ((mc.gameSettings.keyBindLeft.pressed ? 255 : 0) - keyL) / 6 : 19, 19, 180);
            keyB = MathHelper.clamp(keyB != (mc.gameSettings.keyBindBack.pressed ? 255 : 0) ? keyB + ((mc.gameSettings.keyBindBack.pressed ? 255 : 0) - keyB) / 6 : 19, 19, 180);
            keyR = MathHelper.clamp(keyR != (mc.gameSettings.keyBindRight.pressed ? 255 : 0) ? keyR + ((mc.gameSettings.keyBindRight.pressed ? 255 : 0) - keyR) / 6 : 19, 19, 180);
            keyA = MathHelper.clamp(keyA != (mc.gameSettings.keyBindAttack.pressed ? 255 : 0) ? keyA + ((mc.gameSettings.keyBindAttack.pressed ? 255 : 0) - keyA) / 6 : 19, 19, 180);
            keyU = MathHelper.clamp(keyU != (mc.gameSettings.keyBindUseItem.pressed ? 255 : 0) ? keyU + ((mc.gameSettings.keyBindUseItem.pressed ? 255 : 0) - keyU) / 6 : 19, 19, 180);
            float height = renderEvent.getScaledResolution().getScaledHeight();
            GL11.glPushMatrix();
            Rounded.drawRound(10 + 28, height / 2.1f - 28, 25, 25, 2, Color.rgba(keyF, keyF, keyF, 140));
            TTFFontManager.fontRenderer18SGM.drawString(Keyboard.getKeyName(mc.gameSettings.keyBindForward.getKeyCode()), 10 + 28 + (25 / 2f - TTFFontManager.fontRenderer18SGM.getWidth(Keyboard.getKeyName(mc.gameSettings.keyBindForward.getKeyCode())) / 2f) + 0.01f, height / 2.1f - 28 + (25 / 2f - TTFFontManager.fontRenderer18SGM.getHeight(Keyboard.getKeyName(mc.gameSettings.keyBindForward.getKeyCode())) / 2f), Color.rgb(255, 255, 255));
            Rounded.drawRound(10, height / 2.1f, 25, 25, 2, Color.rgba(keyL, keyL, keyL, 140));
            TTFFontManager.fontRenderer18SGM.drawString(Keyboard.getKeyName(mc.gameSettings.keyBindLeft.getKeyCode()), 10 + (25 / 2f - TTFFontManager.fontRenderer18SGM.getWidth(Keyboard.getKeyName(mc.gameSettings.keyBindLeft.getKeyCode())) / 2f) + 0.01f, height / 2.1f + (25 / 2f - TTFFontManager.fontRenderer18SGM.getHeight(Keyboard.getKeyName(mc.gameSettings.keyBindLeft.getKeyCode())) / 2f), Color.rgb(255, 255, 255));
            Rounded.drawRound(10 + 28, height / 2.1f, 25, 25, 2, Color.rgba(keyB, keyB, keyB, 140));
            TTFFontManager.fontRenderer18SGM.drawString(Keyboard.getKeyName(mc.gameSettings.keyBindBack.getKeyCode()), 10 + 28 + (25 / 2f - TTFFontManager.fontRenderer18SGM.getWidth(Keyboard.getKeyName(mc.gameSettings.keyBindBack.getKeyCode())) / 2f) + 0.01f, height / 2.1f + (25 / 2f - TTFFontManager.fontRenderer18SGM.getHeight(Keyboard.getKeyName(mc.gameSettings.keyBindBack.getKeyCode())) / 2f), Color.rgb(255, 255, 255));
            Rounded.drawRound(10 + 28 * 2f, height / 2.1f, 25, 25, 2, Color.rgba(keyR, keyR, keyR, 140));
            TTFFontManager.fontRenderer18SGM.drawString(Keyboard.getKeyName(mc.gameSettings.keyBindRight.getKeyCode()), 10 + 28 * 2f + (25 / 2f - TTFFontManager.fontRenderer18SGM.getWidth(Keyboard.getKeyName(mc.gameSettings.keyBindRight.getKeyCode())) / 2f) + 0.01f, height / 2.1f + (25 / 2f - TTFFontManager.fontRenderer18SGM.getHeight(Keyboard.getKeyName(mc.gameSettings.keyBindRight.getKeyCode())) / 2f), Color.rgb(255, 255, 255));
            Rounded.drawRound(10, height / 2.1f + 28, 39, 25, 2, Color.rgba(keyA, keyA, keyA, 140));
            TTFFontManager.fontRenderer18SGM.drawString("ATK", 10 + (39 / 2f - TTFFontManager.fontRenderer18SGM.getWidth("ATK") / 2f) + 0.01f, height / 2.1f + 28 + (25 / 2f - TTFFontManager.fontRenderer18SGM.getHeight("ATK") / 2f), Color.rgb(255, 255, 255));
            Rounded.drawRound(10 + 42, height / 2.1f + 28, 39, 25, 2, Color.rgba(keyU, keyU, keyU, 140));
            TTFFontManager.fontRenderer18SGM.drawString("USE", 10 + 42 + (39 / 2f - TTFFontManager.fontRenderer18SGM.getWidth("USE") / 2f) + 0.01f, height / 2.1f + 28 + (25 / 2f - TTFFontManager.fontRenderer18SGM.getHeight("USE") / 2f), Color.rgb(255, 255, 255));
            GL11.glPopMatrix();
        }
    }

}
