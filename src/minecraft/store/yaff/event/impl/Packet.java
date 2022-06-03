package store.yaff.event.impl;

import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;

import java.util.Objects;

public class Packet {
    public static class Send extends AbstractEvent {
        protected net.minecraft.network.Packet<?> packet;

        public Send(net.minecraft.network.Packet<?> packet) {
            this.packet = packet;
        }

        public net.minecraft.network.Packet<?> getPacket() {
            return packet;
        }

        public void setPacket(net.minecraft.network.Packet<?> packet) {
            this.packet = packet;
        }

    }

    public static class Receive extends AbstractEvent {
        protected final net.minecraft.network.Packet<?> packet;

        public Receive(net.minecraft.network.Packet<?> packet) {
            this.packet = packet;
        }

        public net.minecraft.network.Packet<?> getPacket() {
            return packet;
        }

        @Override
        public void call() {
            try {
                Objects.requireNonNull(Yaff.of.featureManager.getFeatures(true)).forEach(f -> f.onEvent(this));
                Yaff.of.tpsController.onPacket(this);
            } catch (Exception ignored) {
            }
        }

    }

}
