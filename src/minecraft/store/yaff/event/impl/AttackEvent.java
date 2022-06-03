package store.yaff.event.impl;

import net.minecraft.entity.Entity;
import store.yaff.event.AbstractEvent;

public class AttackEvent extends AbstractEvent {
    protected final Entity entity;

    public AttackEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}
