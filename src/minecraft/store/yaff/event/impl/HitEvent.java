package store.yaff.event.impl;

import net.minecraft.entity.Entity;
import store.yaff.event.AbstractEvent;

public class HitEvent extends AbstractEvent {
    protected final Entity entity;
    protected final HitType hitType;

    public HitEvent(Entity entity, HitType hitType) {
        this.entity = entity;
        this.hitType = hitType;
    }

    public Entity getEntity() {
        return entity;
    }

    public HitType getHitType() {
        return hitType;
    }

    public enum HitType {
        NODAMAGE, WEAK, STRONG, CRITICAL
    }

}
