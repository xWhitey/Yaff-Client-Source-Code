package store.yaff.feature.impl.movement;

import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.Packet;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Time;
import store.yaff.helper.World;
import store.yaff.setting.impl.NumericSetting;

import java.util.ArrayList;
import java.util.Objects;

public class NoVoid extends AbstractFeature {
    public static final NumericSetting revertDistance = new NumericSetting("Distance", "None", 16, 6, 40, 2, () -> true);
    public static final NumericSetting revertDelay = new NumericSetting("Delay", "None", 2, 1, 5, 1, () -> true);

    protected final ArrayList<CPacketPlayer> packetBuffer = new ArrayList<>();
    protected final double[] lastGroundPos = new double[3];
    private final Time timeManager = new Time();

    public NoVoid(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(revertDistance, revertDelay);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Send packetSEvent && packetSEvent.getPacket() instanceof CPacketPlayer playerPacket) {
            if (!packetBuffer.isEmpty() && mc.player.ticksExisted < 100) {
                packetBuffer.clear();
            }
            if (World.isOnVoid((int) revertDistance.getNumericValue())) {
                packetSEvent.setCancelled(true);
                packetBuffer.add(playerPacket);
                if (timeManager.hasReached(timeManager.getSecond((int) revertDelay.getNumericValue()))) {
                    Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(lastGroundPos[0], lastGroundPos[1] - 1, lastGroundPos[2], true));
                }
            } else {
                lastGroundPos[0] = mc.player.posX;
                lastGroundPos[1] = mc.player.posY;
                lastGroundPos[2] = mc.player.posZ;
                if (!packetBuffer.isEmpty()) {
                    for (CPacketPlayer p : packetBuffer) {
                        Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(p);
                    }
                    packetBuffer.clear();
                }
                timeManager.reset();
            }
        }
        if (event instanceof Packet.Receive packetREvent) {
            if (packetREvent.getPacket() instanceof SPacketPlayerPosLook && packetBuffer.size() > 1) {
                packetBuffer.clear();
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this))) {
            packetBuffer.clear();
        }
    }

}
