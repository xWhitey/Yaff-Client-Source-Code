package store.yaff.feature.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Movement;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class Leave extends AbstractFeature {

    public static ListSetting leaveMode = new ListSetting("Leave Mode", "None", "Water", () -> true, "Water", "Cactus", "Web");
    public static NumericSetting waterMotion = new NumericSetting("Water Leave Height", "None", 10, 1, 10, 1, () -> leaveMode.getListValue().equalsIgnoreCase("Water"));
    public static NumericSetting cactusMotion = new NumericSetting("Cactus Leave Height", "None", 10, 1, 10, 1, () -> leaveMode.getListValue().equalsIgnoreCase("Cactus"));
    public static NumericSetting webMotion = new NumericSetting("Web Leave Height", "None", 10, 1, 10, 1, () -> leaveMode.getListValue().equalsIgnoreCase("Web"));

    public Leave(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(leaveMode, waterMotion, cactusMotion, webMotion);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent) {
            switch (leaveMode.getListValue().toLowerCase()) {
                case "cactus" -> {
                    if (mc.player.hurtTime > 3) {
                        if (mc.player.fallDistance > 0.0f && mc.player.motionY < 0.01) {
                            mc.player.motionY = 10;
                        }
                    }
                }
                case "water" -> {
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.95, mc.player.posZ)).getBlock() == Blocks.WATER) {
                        mc.player.motionY = waterMotion.getNumericValue();
                        mc.player.onGround = true;
                        mc.player.isAirBorne = true;
                    }
                    if (mc.player.isInWater() || mc.player.isInLava()) {
                        mc.player.onGround = true;
                    }
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.0000001, mc.player.posZ)).getBlock() == Blocks.WATER) {
                        mc.player.motionY = 0.06f;
                    }
                }
                case "web" -> {
                    BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.6, mc.player.posZ);
                    Block block = mc.world.getBlockState(blockPos).getBlock();
                    if (mc.player.isInWeb) {
                        mc.player.motionY += 2F;
                    } else if (Block.getIdFromBlock(block) == 30) {
                        if (webMotion.getNumericValue() > 0) {
                            mc.player.motionY += 10;
                            for (int i = 0; i < 10; i++) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                            }
                            for (int i = 0; i < 10; i++) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 12000, mc.player.posZ, false));
                            }
                        } else {
                            mc.player.motionY = 0;
                        }
                        Movement.setSpeed(0.8f);
                        mc.gameSettings.keyBindJump.pressed = false;
                    }
                }
            }
        }
    }

}
