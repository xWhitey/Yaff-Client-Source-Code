package store.yaff.event.impl;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;

public class FeatureDisableEvent extends AbstractEvent {
    protected final AbstractFeature feature;

    public FeatureDisableEvent(AbstractFeature feature) {
        this.feature = feature;
    }

    public AbstractFeature getFeature() {
        return feature;
    }

}
