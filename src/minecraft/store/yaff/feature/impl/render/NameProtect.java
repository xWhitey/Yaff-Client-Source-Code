package store.yaff.feature.impl.render;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;

public class NameProtect extends AbstractFeature {
    public static final BooleanSetting skinProtect = new BooleanSetting("skinProtect", "None", false, () -> true);

    public static String protectedName = "YaffLovesYou";

    public NameProtect(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(skinProtect);
    }

    @Override
    public void onEvent(AbstractEvent event) {
    }

}
