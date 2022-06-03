package store.yaff.feature.impl.misc;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Packet;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.NumericSetting;

public class PacketLimiter extends AbstractFeature {
    public static final NumericSetting packetLimit = new NumericSetting("Limit", "None", 20, 1, 30, 1, () -> true);

    protected int sendedPackets = 0;

    public PacketLimiter(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(packetLimit);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Send) {
            sendedPackets++;
            if (sendedPackets > packetLimit.getNumericValue()) {
                event.setCancelled(true);
            }
        }
        if (event instanceof UpdateEvent) {
            if (mc.player.ticksExisted % 2 == 0) {
                sendedPackets = 0;
            }
        }
    }

}
