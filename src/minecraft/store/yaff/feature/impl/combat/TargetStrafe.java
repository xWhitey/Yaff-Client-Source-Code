package store.yaff.feature.impl.combat;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.*;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Rotation;
import store.yaff.helper.World;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

public class TargetStrafe extends AbstractFeature {
    public static final NumericSetting strafeDistance = new NumericSetting("Distance", "None", 2.3f, 1, 5, 0.1f, () -> true);
    public static final NumericSetting strafeSpeed = new NumericSetting("Speed", "None", 0.23f, 0.1f, 1, 0.01f, () -> true);
    public static final BooleanSetting autoShift = new BooleanSetting("AutoShift", "None", true, () -> true);
    public static final BooleanSetting autoJump = new BooleanSetting("AutoJump", "None", true, () -> true);

    protected float wrappedPosition = 0;
    protected byte strafeDirection = 1;

    public TargetStrafe(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(strafeDistance, strafeSpeed, autoShift, autoJump);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (Aura.targetEntity != null) {
                if (autoJump.getBooleanValue() && mc.player.onGround) {
                    mc.player.jump();
                }
                if (autoShift.getBooleanValue()) {
                    mc.gameSettings.keyBindSneak.pressed = World.isFalling();
                }
            }
        }
        if (event instanceof MotionEvent) {
            if (Aura.targetEntity != null) {
                if (mc.player.isCollidedHorizontally || mc.player.isInLava() || mc.player.isInWeb || mc.gameSettings.keyBindLeft.isPressed() || mc.gameSettings.keyBindRight.isPressed() || World.isOnVoid(3)) {
                    strafeDirection *= -1;
                }
                wrappedPosition = (float) Math.atan2((mc.player.posZ - Aura.targetEntity.posZ), (mc.player.posX - Aura.targetEntity.posX));
                wrappedPosition += strafeSpeed.getNumericValue() / mc.player.getDistanceToEntity(Aura.targetEntity) * strafeDirection;
                double xPos = Aura.targetEntity.posX + strafeDistance.getNumericValue() * Math.cos(wrappedPosition);
                double zPos = Aura.targetEntity.posZ + strafeDistance.getNumericValue() * Math.sin(wrappedPosition);
                mc.player.motionX = -Math.sin((float) Math.toRadians(Rotation.positionToDegrees(xPos, zPos))) * strafeSpeed.getNumericValue();
                mc.player.motionZ = Math.cos((float) Math.toRadians(Rotation.positionToDegrees(xPos, zPos))) * strafeSpeed.getNumericValue();
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            if (autoShift.getBooleanValue()) {
                mc.gameSettings.keyBindSneak.pressed = false;
            }
        }
    }

}
