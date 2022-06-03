package store.yaff.feature.impl.player;

import net.minecraft.network.play.client.CPacketPlayer;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.ListSetting;

public class NoFall extends AbstractFeature {
    public static final ListSetting noFallMode = new ListSetting("Mode", "None", "Matrix", () -> true, "Matrix", "Matrix2");

    public NoFall(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(noFallMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent) {
            switch (noFallMode.getListValue().toLowerCase()) {
                case "matrix" -> {
                    if (mc.player.fallDistance > 3) {
                        mc.player.fallDistance = (float) (Math.random() * 1.0E-12);
                        mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, mc.player.rotationPitch, true));
                        mc.player.fallDistance = 0;
                    }
                }
                case "matrix2" -> {
                    if (mc.player.fallDistance > 3f) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
                        mc.player.motionY -= 0.2f;
                    }
                }
            }
        }
    }

}
