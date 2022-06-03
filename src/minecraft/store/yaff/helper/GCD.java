package store.yaff.helper;

import net.minecraft.client.Minecraft;

public class GCD {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static float getSensivity() {
        return mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
    }

    public static float getGCD() {
        float mouseSens = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        return mouseSens * mouseSens * mouseSens * 1.2f;
    }

    public static float getGCDValue() {
        return (float) (getGCD() * 0.15D);
    }

    public static float getDeltaMouse(float delta) {
        return java.lang.Math.round(delta / getGCDValue());
    }

    public static float getFixedRotation(float rotation) {
        return getDeltaMouse(rotation) * getGCDValue();
    }

}
