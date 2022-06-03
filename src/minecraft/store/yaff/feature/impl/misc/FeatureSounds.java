package store.yaff.feature.impl.misc;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.render.GUI;
import store.yaff.helper.Sound;
import store.yaff.setting.impl.NumericSetting;

public class FeatureSounds extends AbstractFeature {
    public static NumericSetting soundVolume = new NumericSetting("Volume", "None", 50.0F, 1.0F, 100.0F, 1.0F, () -> true);

    public FeatureSounds(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(soundVolume);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof FeatureStateEvent stateEvent && !(stateEvent.getFeature() instanceof GUI)) {
            float volume = soundVolume.getNumericValue() / 10.0F;
            if (stateEvent.getState()) {
                Sound.playSound("enable.wav", -30.0F + volume * 3.0F, false);
            } else {
                Sound.playSound("disable.wav", -30.0F + volume * 3.0F, false);
            }
        }
    }

}
