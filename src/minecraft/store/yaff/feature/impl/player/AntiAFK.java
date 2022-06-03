package store.yaff.feature.impl.player;

import net.minecraft.util.EnumHand;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Random;
import store.yaff.helper.Rotation;
import store.yaff.helper.Time;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

public class AntiAFK extends AbstractFeature {
    public static final BooleanSetting useSneak = new BooleanSetting("Use Sneak", "None", true, () -> true);
    public static final BooleanSetting useJump = new BooleanSetting("Use Jump", "None", true, () -> true);
    public static final BooleanSetting useSwing = new BooleanSetting("Use Swing", "None", true, () -> true);
    public static final BooleanSetting useRotation = new BooleanSetting("Use Rotation", "None", true, () -> true);
    public static final NumericSetting afkDelay = new NumericSetting("Delay", "None", 3, 1, 5, 1, () -> true);

    private final Time timeManager = new Time();

    public AntiAFK(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(useSneak, useJump, useSwing, useRotation, afkDelay);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (timeManager.hasReached(timeManager.getSecond((int) afkDelay.getNumericValue()))) {
                if (useSneak.getBooleanValue()) {
                    mc.gameSettings.keyBindSneak.pressed = true;
                }
                if (useSwing.getBooleanValue()) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
                if (useJump.getBooleanValue()) {
                    if (mc.player.onGround) {
                        mc.player.jump();
                    }
                }
                if (useRotation.getBooleanValue()) {
                    Rotation.setClientRotations(new float[]{ Random.getRandomInt(-180, 180), Random.getRandomInt(-90, 90) });
                }
                timeManager.reset();
            }
        }
    }

}
