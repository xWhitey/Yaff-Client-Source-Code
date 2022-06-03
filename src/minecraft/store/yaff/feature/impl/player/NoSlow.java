package store.yaff.feature.impl.player;

import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.ListSetting;

public class NoSlow extends AbstractFeature {
    public static final ListSetting noSlowMode = new ListSetting("Mode", "None", "Matrix", () -> true, "NCP", "AAC5", "Matrix", "Sunrise");

    public NoSlow(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(noSlowMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent) {
            if (mc.player.isHandActive()) {
                if (noSlowMode.getListValue().equalsIgnoreCase("AAC5")) {
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                }
            }
        }
        if (event instanceof UpdateEvent) {
            switch (noSlowMode.getListValue().toLowerCase()) {
                case "ncp" -> {
                    if (mc.player.isHandActive()) {
                        mc.playerController.syncCurrentPlayItem();
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    }
                }
                case "matrix" -> {
                    if (mc.player.isHandActive()) {
                        if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                            if (mc.player.ticksExisted % 2 == 0) {
                                mc.player.motionX *= 0.48D;
                                mc.player.motionZ *= 0.48D;
                            }
                        } else if ((double) mc.player.fallDistance > 0.7D) {
                            mc.player.motionX *= 0.9700000286102295D;
                            mc.player.motionZ *= 0.9700000286102295D;
                        }
                    }
                }
                case "sunrise" -> {
                    if (mc.player.isHandActive()) {
                        if (mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                            if (mc.player.ticksExisted % 2 == 0) {
                                mc.player.motionX *= 0.47D;
                                mc.player.motionZ *= 0.47D;
                            }
                        } else if ((double) mc.player.fallDistance > 0.2D) {
                            mc.player.motionX *= 0.9300000071525574D;
                            mc.player.motionZ *= 0.9300000071525574D;
                        }
                    }
                }
            }
        }
    }

}
