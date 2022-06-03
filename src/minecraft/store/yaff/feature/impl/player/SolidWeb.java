package store.yaff.feature.impl.player;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

public class SolidWeb extends AbstractFeature {
    public SolidWeb(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
    }

}
