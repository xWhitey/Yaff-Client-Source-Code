package store.yaff.feature.impl.player;

import net.minecraft.network.play.client.CPacketCloseWindow;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Packet;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

public class XCarry extends AbstractFeature {
    public XCarry(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Receive packetEvent) {
            if (packetEvent.getPacket() instanceof CPacketCloseWindow cPacketCloseWindow) {
                if (cPacketCloseWindow.windowId == 0) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
