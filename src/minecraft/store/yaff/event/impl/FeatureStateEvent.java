package store.yaff.event.impl;

import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;

import java.util.Objects;

public class FeatureStateEvent extends AbstractEvent {
    protected final AbstractFeature feature;
    protected final boolean state;

    public FeatureStateEvent(AbstractFeature feature, boolean state) {
        this.feature = feature;
        this.state = state;
    }

    public AbstractFeature getFeature() {
        return feature;
    }

    public boolean getState() {
        return state;
    }

    @Override
    public void call() {
        try {
            if (!getState()) {
                Objects.requireNonNull(Yaff.of.featureManager.getFeatures()).forEach(f -> f.onEvent(new FeatureDisableEvent(getFeature())));
            }
            Objects.requireNonNull(Yaff.of.featureManager.getFeatures(true)).forEach(f -> f.onEvent(this));
        } catch (Exception ignored) {
        }
    }

}
