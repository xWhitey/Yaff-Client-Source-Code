package store.yaff.feature.impl.player;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.combat.Aura;
import store.yaff.helper.Random;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class Spinner extends AbstractFeature {
    public static final ListSetting spinMode = new ListSetting("Mode", "None", "Smooth", () -> true, "Smooth", "Glitch", "Custom", "Original");
    public static final ListSetting pitchMode = new ListSetting("Pitch Mode", "None", "Zero", () -> true, "Zero", "Glitch", "Custom", "Original");
    public static final NumericSetting customYaw = new NumericSetting("Custom Yaw", "None", 64, -180, 180, 1, () -> true);
    public static final NumericSetting customYawDiff = new NumericSetting("Custom Yaw Diff", "None", 10, 0, 16, 1, () -> true);
    public static final NumericSetting customPitch = new NumericSetting("Custom Pitch", "None", 8, -90, 90, 1, () -> true);
    public static final BooleanSetting serverSide = new BooleanSetting("ServerSide", "None", true, () -> true);

    protected float rotationYawDegrees = 0;
    protected float rotationPitchDegrees = 0;

    public Spinner(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(spinMode, pitchMode, customYaw, customYawDiff, customPitch, serverSide);
    }

    public float rotationYaw(float additionalDegrees, float rotationDifference) {
        if (rotationYawDegrees > 180) {
            rotationYawDegrees = -180;
        }
        rotationYawDegrees += additionalDegrees;
        return Random.getRandomFloat(rotationYawDegrees - rotationDifference, rotationYawDegrees + rotationDifference);
    }

    public float rotationPitch(float additionalDegrees, float rotationDifference) {
        if (rotationPitchDegrees > 90) {
            rotationPitchDegrees = -90;
        }
        rotationPitchDegrees += additionalDegrees;
        return Random.getRandomFloat(rotationPitchDegrees - rotationDifference, rotationPitchDegrees + rotationDifference);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            if (Aura.targetEntity == null) {
                if (spinMode.getListValue().equalsIgnoreCase("Smooth")) {
                    float yaw = rotationYaw(customYaw.getNumericValue(), customYawDiff.getNumericValue());
                    if (serverSide.getBooleanValue()) {
                        motionEvent.setYaw(yaw);
                    }
                    mc.player.renderYawOffset = yaw;
                    mc.player.rotationYawHead = yaw;
                }
                if (spinMode.getListValue().equalsIgnoreCase("Glitch")) {
                    float yaw = Random.getRandomFloat(-180, 180);
                    if (serverSide.getBooleanValue()) {
                        motionEvent.setYaw(yaw);
                    }
                    mc.player.renderYawOffset = yaw;
                    mc.player.rotationYawHead = yaw;
                }
                if (spinMode.getListValue().equalsIgnoreCase("Custom")) {
                    if (serverSide.getBooleanValue()) {
                        motionEvent.setYaw(customYaw.getNumericValue());
                    }
                    mc.player.renderYawOffset = customYaw.getNumericValue();
                    mc.player.rotationYawHead = customYaw.getNumericValue();
                }
                if (pitchMode.getListValue().equalsIgnoreCase("Zero")) {
                    if (serverSide.getBooleanValue()) {
                        motionEvent.setPitch(0);
                    }
                    mc.player.rotationPitchHead = 0;
                }
                if (pitchMode.getListValue().equalsIgnoreCase("Glitch")) {
                    float pitch = Random.getRandomFloat(-90, 90);
                    if (serverSide.getBooleanValue()) {
                        motionEvent.setPitch(pitch);
                    }
                    mc.player.rotationPitchHead = pitch;
                }
                if (pitchMode.getListValue().equalsIgnoreCase("Custom")) {
                    if (serverSide.getBooleanValue()) {
                        motionEvent.setPitch(customPitch.getNumericValue());
                    }
                    mc.player.rotationPitchHead = customPitch.getNumericValue();
                }
            }
        }
    }

}
