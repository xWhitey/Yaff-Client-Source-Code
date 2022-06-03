package store.yaff.feature.impl.combat;

import net.minecraft.network.play.client.CPacketPlayer;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.AttackEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Random;
import store.yaff.setting.impl.ListSetting;

import java.util.Objects;

public class Criticals extends AbstractFeature {
    public static final ListSetting criticalsMode = new ListSetting("Mode", "None", "Matrix", () -> true, "Jump", "LowJump", "Packet", "PacketOld", "NCP", "Matrix");

    public Criticals(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(criticalsMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof AttackEvent) {
            double x = mc.player.posX;
            double y = mc.player.posY;
            double z = mc.player.posZ;
            if (criticalsMode.getListValue().equalsIgnoreCase("Jump")) {
                if (mc.player.onGround) {
                    mc.player.jump();
                }
            }
            if (criticalsMode.getListValue().equalsIgnoreCase("LowJump")) {
                if (mc.player.onGround) {
                    mc.player.motionY = 0.22f;
                }
            }
            if (criticalsMode.getListValue().equalsIgnoreCase("Packet")) {
                Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 0.11, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 0.1100013579, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 1.3579E-6D, z, false));
            }
            if (criticalsMode.getListValue().equalsIgnoreCase("PacketOld")) {
                Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 0.11, z, true));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 0.0625D, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 1.1E-5D, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y, z, false));
            }
            if (criticalsMode.getListValue().equalsIgnoreCase("NCP")) {
                Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 0.11, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 0.1100013579, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 0.0000013579, z, false));
            }
            if (criticalsMode.getListValue().equalsIgnoreCase("Matrix")) {
                mc.player.onGround = false;
                mc.player.fallDistance = (float) 1.0E-12;
                mc.player.motionY = 1.0E-12;
                mc.player.isCollidedVertically = true;
                Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + 1, z, false));
                mc.getConnection().getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer.Position(x, y + Random.getRandomFloat(0.00000001F, 0.0000002F), z, false));
            }
        }
    }

}
