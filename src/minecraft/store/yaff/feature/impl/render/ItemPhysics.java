package store.yaff.feature.impl.render;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

public class ItemPhysics extends AbstractFeature {
    public ItemPhysics(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
    }

}
