package store.yaff.event;

import store.yaff.Yaff;

import java.util.Objects;

public abstract class AbstractEvent {
    protected boolean cancelled;

    protected AbstractEvent() {
        this.cancelled = false;
    }

    public void call() {
        try {
            Objects.requireNonNull(Yaff.of.featureManager.getFeatures(true)).forEach(f -> f.onEvent(this));
        } catch (Exception ignored) {
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
