package store.yaff.event.impl;

import store.yaff.event.AbstractEvent;

public class JumpEvent extends AbstractEvent {
    protected float yaw;

    public JumpEvent(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

}
