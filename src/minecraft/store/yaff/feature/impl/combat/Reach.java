package store.yaff.feature.impl.combat;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.NumericSetting;

public class Reach extends AbstractFeature {
    public static final NumericSetting reachExpand = new NumericSetting("Expand", "None", 3.7f, 3, 5, 0.1f, () -> true);

    public Reach(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(reachExpand);
    }

    @Override
    public void onEvent(AbstractEvent event) {
    }

}
