package store.yaff.feature.impl.player;

import net.minecraft.network.play.client.CPacketPlayer;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.*;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Time;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

import java.util.ArrayList;
import java.util.Objects;

public class Blink extends AbstractFeature {
    public static final BooleanSetting usePulse = new BooleanSetting("Pulse", "None", true, () -> true);
    public static final NumericSetting blinkPulse = new NumericSetting("Pulse Delay", "None", 320, 100, 600, 10, () -> true);

    protected final ArrayList<CPacketPlayer> packetBuffer = new ArrayList<>();
    private final Time timeManager = new Time();

    public Blink(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(usePulse, blinkPulse);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (usePulse.getBooleanValue() && timeManager.hasReached(blinkPulse.getNumericValue()) && !packetBuffer.isEmpty()) {
                for (CPacketPlayer p : packetBuffer) {
                    Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(p);
                }
                packetBuffer.clear();
            }
        }
        if (event instanceof Packet.Send packetEvent) {
            if (packetEvent.getPacket() instanceof CPacketPlayer playerPacket) {
                packetBuffer.add(playerPacket);
                event.setCancelled(true);
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this))) {
            packetBuffer.clear();
        }
        if (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this)) {
            if (!packetBuffer.isEmpty()) {
                for (CPacketPlayer p : packetBuffer) {
                    Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(p);
                }
                packetBuffer.clear();
            }
        }
    }

}
