package store.yaff.feature.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Movement;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class Jesus extends AbstractFeature {

    public static ListSetting jesusMode = new ListSetting("Jesus Mode", "None", "Matrix", () -> true, "Matrix", "NCP");
    public static NumericSetting jesusSpeed = new NumericSetting("Jesus Speed", "None", 4, 1, 10, 0.1F, () -> true);
    public static BooleanSetting autoMotionStop = new BooleanSetting("Auto Motion Stop", "None", true, () -> jesusMode.getListValue().equalsIgnoreCase("Matrix"));
    private int waterTicks = 0;

    public Jesus(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(jesusMode, jesusSpeed, autoMotionStop);
    }

    private boolean isWater() {
        BlockPos bp1 = new BlockPos(mc.player.posX - 0.5D, mc.player.posY - 0.5D, mc.player.posZ - 0.5D);
        BlockPos bp2 = new BlockPos(mc.player.posX - 0.5D, mc.player.posY - 0.5D, mc.player.posZ + 0.5D);
        BlockPos bp3 = new BlockPos(mc.player.posX + 0.5D, mc.player.posY - 0.5D, mc.player.posZ + 0.5D);
        BlockPos bp4 = new BlockPos(mc.player.posX + 0.5D, mc.player.posY - 0.5D, mc.player.posZ - 0.5D);
        return mc.player.world.getBlockState(bp1).getBlock() == Blocks.WATER && mc.player.world.getBlockState(bp2).getBlock() == Blocks.WATER && mc.player.world.getBlockState(bp3).getBlock() == Blocks.WATER && mc.player.world.getBlockState(bp4).getBlock() == Blocks.WATER || mc.player.world.getBlockState(bp1).getBlock() == Blocks.LAVA && mc.player.world.getBlockState(bp2).getBlock() == Blocks.LAVA && mc.player.world.getBlockState(bp3).getBlock() == Blocks.LAVA && mc.player.world.getBlockState(bp4).getBlock() == Blocks.LAVA;
    }

    @Override
    public void onEvent(AbstractEvent event) {
        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1D, mc.player.posZ);
        Block block = mc.world.getBlockState(blockPos).getBlock();
        String mode = jesusMode.getListValue();
        if (event instanceof MotionEvent motionEvent) {
            if (mode.equalsIgnoreCase("Matrix")) {
//                if (this.isWater() && block instanceof BlockLiquid) {
//                    ++this.waterTicks;
//                    mc.player.motionY = 0.0D;
//                    motionEvent.setOnGround(false);
//                    mc.player.onGround = false;
//                    motionEvent.setY(mc.player.ticksExisted % 2 == 0 ? motionEvent.getY() + 0.02D : motionEvent.getY() - 0.02D);
//                    if (mc.player.ticksExisted % 2 == 0) {
//                        Movement.setSpeed((jesusSpeed.getNumericValue() * 5.2F));
//                    } else {
//                        Movement.setSpeed(0.07000000029802322f);
//                    }
//                } else {
//                    this.waterTicks = MathHelper.clamp(this.waterTicks, 0, this.waterTicks);
//                    this.waterTicks -= 10;
//                    if (this.autoMotionStop.getBooleanValue() && this.waterTicks >= 5 && !mc.player.onGround) {
//                        Movement.setSpeed(0.0f);
//                    }
//                }
                if (this.isWater() && block instanceof BlockLiquid) {
                    ++this.waterTicks;
                    //if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.5D, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                    // if (!mc.player.onGround) {
                    mc.player.isAirBorne = true;
                    Movement.setSpeed(mc.player.ticksExisted % 1 == 0 ? jesusSpeed.getNumericValue() : 0.1F);
                    motionEvent.setY(mc.player.ticksExisted % 2 == 0 ? motionEvent.getY() + 0.02 : motionEvent.getY() - 0.02);
                    //}
                    // }
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 2.0E-7D, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                        mc.player.motionX = 0.0D;
                        mc.player.motionY = 0.029999999329447746D;
                        //mc.player.jumpMovementFactor = 0.008F;
                        mc.player.motionZ = 0.0D;
                    }
                } else {
                    this.waterTicks = MathHelper.clamp(this.waterTicks, 0, this.waterTicks);
                    this.waterTicks -= 10;
                    if (autoMotionStop.getBooleanValue() && this.waterTicks >= 5 && !mc.player.onGround) {
                        Movement.setSpeed(0.0f);
                        mc.player.motionY = 0.06;
                        mc.player.jumpMovementFactor = 0.01F;
                    }
//                    if (mc.player.isCollidedHorizontally) {
//                        Movement.setSpeed(0.0f);
//                        mc.player.motionY = 0.3000000298023224D;
//                        mc.player.jumpMovementFactor = 0.01F;
//                        mc.player.setSprinting(false);
//                    }
                }
            }
            if (mode.equalsIgnoreCase("NCP")) {
                if (mc.player.isInWater() || (mc.player.movementInput.jump))
                    mc.player.motionY = 0.06000000238418583F;
                else if (mc.player.ticksExisted % 2 == 0)
                    Movement.setSpeed(0.3f);
                motionEvent.setY(motionEvent.getY() - 0.015625);
            }
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            mc.timer.field_194150_d = 1.0F;
            this.waterTicks = 0;
        }
    }

}
