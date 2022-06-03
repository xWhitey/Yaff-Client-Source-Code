package store.yaff.feature.impl.combat;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.NumericSetting;

public class HitBox extends AbstractFeature {
    public static final NumericSetting hitboxExpand = new NumericSetting("Expand", "None", 0.03f, 0.01f, 0.5f, 0.01f, () -> true);

    public HitBox(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(hitboxExpand);
    }

    @Override
    public void onEvent(AbstractEvent event) {
    }

}
