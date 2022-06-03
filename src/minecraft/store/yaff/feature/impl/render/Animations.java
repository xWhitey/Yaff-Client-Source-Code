package store.yaff.feature.impl.render;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class Animations extends AbstractFeature {
    public static final ListSetting animationMode = new ListSetting("Mode", "None", "Hit", () -> true, "Hit", "Neutral", "Float", "Spin", "Astolfo", "Fap", "Jello");
    public static final NumericSetting itemSize = new NumericSetting("Size", "None", 0.9f, 0.3f, 1, 0.1f, () -> true);
    public static final NumericSetting spinSpeed = new NumericSetting("Spin Speed", "None", 6, 1, 20, 1, () -> animationMode.getListValue().equalsIgnoreCase("Spin"));

    public Animations(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(animationMode, itemSize, spinSpeed);
    }

    @Override
    public void onEvent(AbstractEvent event) {
    }

}
