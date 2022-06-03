package store.yaff.feature.impl.combat;

import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.DamageEvent;
import store.yaff.event.impl.Packet;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

import java.util.Objects;

public class Velocity extends AbstractFeature {
    public static final BooleanSetting aacVelocity = new BooleanSetting("AAC", "None", false, () -> true);
    public static final BooleanSetting includeExplosions = new BooleanSetting("Explosions", "None", true, () -> true);
    public static final BooleanSetting requireAir = new BooleanSetting("Only in Air", "None", false, () -> true);
    public static final NumericSetting verticalPercent = new NumericSetting("Vertical", "None", 5, 0, 100, 1, () -> true);
    public static final NumericSetting horizontalPercent = new NumericSetting("Horizontal", "None", 5, 0, 100, 1, () -> true);
    public static final BooleanSetting reduceVelocity = new BooleanSetting("Reduce", "None", true, () -> true);
    public static final NumericSetting reducementPercent = new NumericSetting("Reducement", "None", 5, 0, 100, 1, () -> true);
    public static final NumericSetting yReducementPercent = new NumericSetting("Y Reducement", "None", 5, 0, 100, 1, () -> true);

    public Velocity(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(aacVelocity, includeExplosions, requireAir, verticalPercent, horizontalPercent, reduceVelocity, reducementPercent, yReducementPercent);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Receive packetEvent) {
            if (packetEvent.getPacket() instanceof SPacketEntityVelocity velocityPacket) {
                if (velocityPacket.getEntityID() == mc.player.getEntityId()) {
                    if (horizontalPercent.getNumericValue() == 0 && verticalPercent.getNumericValue() == 0) {
                        event.setCancelled(true);
                        return;
                    }
                    if (aacVelocity.getBooleanValue()) {
                        velocityPacket.motionX *= horizontalPercent.getNumericValue() / 100f;
                        velocityPacket.motionZ *= horizontalPercent.getNumericValue() / 100f;
                        velocityPacket.motionY *= verticalPercent.getNumericValue() / 100f;
                        Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        return;
                    }
                    if (!(requireAir.getBooleanValue() && !mc.player.onGround)) {
                        velocityPacket.motionX *= horizontalPercent.getNumericValue() / 100f;
                        velocityPacket.motionZ *= horizontalPercent.getNumericValue() / 100f;
                        velocityPacket.motionY *= verticalPercent.getNumericValue() / 100f;
                        if (reduceVelocity.getBooleanValue() && mc.player.ticksExisted % 3 == 0) {
                            velocityPacket.motionX *= -reducementPercent.getNumericValue() / 100f;
                            velocityPacket.motionZ *= -reducementPercent.getNumericValue() / 100f;
                            velocityPacket.motionY *= -yReducementPercent.getNumericValue() / 100f;
                        }
                    }
                }
            }
            if (packetEvent.getPacket() instanceof SPacketExplosion) {
                if (includeExplosions.getBooleanValue()) {
                    event.setCancelled(true);
                }
            }
        }
        if (event instanceof DamageEvent) {
            if (mc.player.hurtTime > 0 && aacVelocity.getBooleanValue()) {
                mc.player.motionX *= horizontalPercent.getNumericValue() / 100;
                mc.player.motionZ *= horizontalPercent.getNumericValue() / 100;
                mc.player.motionY *= verticalPercent.getNumericValue() / 100;
            }
        }
    }

}
