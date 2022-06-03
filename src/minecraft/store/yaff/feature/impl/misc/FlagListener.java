package store.yaff.feature.impl.misc;

import net.minecraft.network.play.server.SPacketPlayerPosLook;
import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Packet;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.IFlagable;

import java.util.Objects;

public class FlagListener extends AbstractFeature {
    public FlagListener(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Receive packetEvent) {
            if (packetEvent.getPacket() instanceof SPacketPlayerPosLook) {
                Objects.requireNonNull(Yaff.of.featureManager.getFeatures()).stream().filter(f -> f instanceof IFlagable).forEach(f -> ((IFlagable) f).onFlag());
                mc.timer.field_194150_d = 1f;
                mc.player.speedInAir = 0.02f;
                mc.playerController.updateController();
            }
        }
    }

}
