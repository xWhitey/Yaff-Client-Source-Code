package store.yaff.event.impl;

import store.yaff.event.AbstractEvent;

public class DamageEvent extends AbstractEvent {
    protected final float amount;

    public DamageEvent(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

}
