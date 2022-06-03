package store.yaff.event.impl;

import store.yaff.event.AbstractEvent;

public class HealEvent extends AbstractEvent {
    protected final float amount;

    public HealEvent(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

}
