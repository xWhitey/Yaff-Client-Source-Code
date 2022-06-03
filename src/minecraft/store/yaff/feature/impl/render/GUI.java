package store.yaff.feature.impl.render;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.ui.gui.NewYaffGui;

public class GUI extends AbstractFeature {
    public GUI(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) {
            mc.displayGuiScreen(new NewYaffGui());
            super.setState(false);
        }
    }

}
