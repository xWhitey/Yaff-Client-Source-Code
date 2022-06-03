package store.yaff.feature.impl.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketUseEntity;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Packet;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

import java.util.Objects;

public class WTap extends AbstractFeature {
    public WTap(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Packet.Send packetEvent) {
            if (packetEvent.getPacket() instanceof CPacketUseEntity entityPacket && entityPacket.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (mc.world.getEntityByID(entityPacket.entityId) instanceof EntityLivingBase targetEntity) {
                    if (mc.player.isSprinting()) {
                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
                    }
                }
            }
        }
    }

}
