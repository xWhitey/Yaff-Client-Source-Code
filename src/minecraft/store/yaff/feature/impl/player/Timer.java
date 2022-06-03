package store.yaff.feature.impl.player;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.NumericSetting;

public class Timer extends AbstractFeature {
    public static final NumericSetting timerSpeed = new NumericSetting("Speed", "None", 1.2f, 0.1f, 6, 0.1f, () -> true);

    public Timer(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(timerSpeed);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            mc.timer.field_194150_d = timerSpeed.getNumericValue();
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            mc.timer.field_194150_d = 1f;
        }
    }

}
