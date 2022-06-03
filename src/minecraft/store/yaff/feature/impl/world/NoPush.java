package store.yaff.feature.impl.world;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.Push;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;

public class NoPush extends AbstractFeature {
    public static final BooleanSetting waterPush = new BooleanSetting("waterPush", "None", true, () -> true);
    public static final BooleanSetting playerPush = new BooleanSetting("playerPush", "None", true, () -> true);
    public static final BooleanSetting blockPush = new BooleanSetting("blockPush", "None", true, () -> true);

    public NoPush(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(waterPush, playerPush, blockPush);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Push.Water && waterPush.getBooleanValue()) {
            event.setCancelled(true);
        }
        if (event instanceof Push.Entity && playerPush.getBooleanValue()) {
            event.setCancelled(true);
        }
        if (event instanceof Push.Block && blockPush.getBooleanValue()) {
            event.setCancelled(true);
        }
        if (event instanceof UpdateEvent && playerPush.getBooleanValue()) {
            mc.player.entityCollisionReduction = 1;
        }
        if (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this)) {
            mc.player.entityCollisionReduction = 0;
        }
    }

}
