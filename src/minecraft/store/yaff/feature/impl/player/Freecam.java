package store.yaff.feature.impl.player;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.*;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

import java.util.Objects;

public class Freecam extends AbstractFeature {
    protected double posX, posY, posZ;

    public Freecam(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (mc.player.ticksExisted % 5 == 0) {
                Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer(true));
            }
        }
        if (event instanceof Push.Block || event instanceof Push.Water || event instanceof Push.Entity) {
            event.setCancelled(true);
        }
        if (event instanceof Packet.Send packetSEvent) {
            if (packetSEvent.getPacket() instanceof CPacketPlayer || packetSEvent.getPacket() instanceof CPacketPlayerAbilities) {
                event.setCancelled(true);
            }
        }
        if (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) {
            EntityOtherPlayerMP ghostEntity = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
            ghostEntity.copyLocationAndAnglesFrom(mc.player);
            mc.world.addEntityToWorld(-128, ghostEntity);
            mc.player.capabilities.allowFlying = true;
            mc.player.capabilities.isFlying = true;
            posX = mc.player.posX;
            posY = mc.player.posY;
            posZ = mc.player.posZ;
            mc.player.onGround = true;
        }
        if (event instanceof WorldEvent || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            mc.renderGlobal.loadRenderers();
            mc.world.removeEntityFromWorld(-128);
            mc.player.setPositionAndRotation(posX, posY, posZ, mc.player.rotationYaw, mc.player.rotationPitch);
            mc.player.capabilities.allowFlying = false;
            mc.player.capabilities.isFlying = false;
            mc.player.onGround = true;
        }
    }

}
