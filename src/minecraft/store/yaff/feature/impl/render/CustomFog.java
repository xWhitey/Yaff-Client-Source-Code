package store.yaff.feature.impl.render;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FogEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Color;
import store.yaff.setting.impl.NumericSetting;

public class CustomFog extends AbstractFeature {

    public static NumericSetting distance = new NumericSetting("Distance", "None", 0.10F, 0.001F, 2, 0.01F, () -> true);

    public CustomFog(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(distance);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof FogEvent fogEvent) {
            int customColorValue = Color.rgb(133, 133, 199);
            fogEvent.setR(Color.r(customColorValue));
            fogEvent.setG(Color.g(customColorValue));
            fogEvent.setB(Color.b(customColorValue));
        }
    }

}
