package store.yaff.feature.impl.player;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;

public class NoDelay extends AbstractFeature {
    public static BooleanSetting jumpDelay = new BooleanSetting("Jump Delay", "None", true, () -> true);
    public static BooleanSetting blockHitDelay = new BooleanSetting("BlockHit Delay", "None", true, () -> true);
    public static BooleanSetting leftClickDelay = new BooleanSetting("Left Click Delay", "None", true, () -> true);
    public static BooleanSetting rightClickDelay = new BooleanSetting("Right Click Delay", "None", true, () -> true);

    public NoDelay(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(jumpDelay, blockHitDelay, leftClickDelay, rightClickDelay);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (jumpDelay.getBooleanValue()) {
                mc.player.jumpTicks = 0;
            }
            if (blockHitDelay.getBooleanValue()) {
                mc.playerController.blockHitDelay = 0;
            }
            if (leftClickDelay.getBooleanValue()) {
                mc.leftClickCounter = 0;
            }
            if (rightClickDelay.getBooleanValue()) {
                mc.rightClickDelayTimer = 0;
            }
        }
        if (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this)) {
            mc.rightClickDelayTimer = 4;
        }
    }

}
