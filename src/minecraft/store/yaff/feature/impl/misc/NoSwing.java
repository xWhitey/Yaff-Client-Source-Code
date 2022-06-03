package store.yaff.feature.impl.misc;

import net.minecraft.network.play.client.CPacketAnimation;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Packet;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

public class NoSwing extends AbstractFeature {
    public NoSwing(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Send packetEvent) {
            if (packetEvent.getPacket() instanceof CPacketAnimation) {
                packetEvent.setCancelled(true);
            }
        }
    }

}
