package store.yaff.event.impl;

import net.minecraft.util.EnumHandSide;
import store.yaff.event.AbstractEvent;

public class TransformSideFirstPerson extends AbstractEvent {
    private final EnumHandSide enumHandSide;

    public TransformSideFirstPerson(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }

}

