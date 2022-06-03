package store.yaff.event.impl;

import net.minecraft.client.gui.ScaledResolution;
import store.yaff.event.AbstractEvent;

public class Render {
    public static class Render2D extends AbstractEvent {
        protected final float partialTicks;
        protected final ScaledResolution scaledResolution;

        public Render2D(float partialTicks, ScaledResolution scaledResolution) {
            this.partialTicks = partialTicks;
            this.scaledResolution = scaledResolution;
        }

        public float getPartialTicks() {
            return partialTicks;
        }

        public ScaledResolution getScaledResolution() {
            return scaledResolution;
        }

    }

    public static class Render3D extends AbstractEvent {
        protected final float partialTicks;

        public Render3D(float partialTicks) {
            this.partialTicks = partialTicks;
        }

        public float getPartialTicks() {
            return partialTicks;
        }

    }

}
