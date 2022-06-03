package store.yaff.feature.impl.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.*;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.combat.Aura;
import store.yaff.helper.*;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class Scaffold extends AbstractFeature {
    public static final BooleanSetting useSprint = new BooleanSetting("Use Sprint", "None", false, () -> true);
    public static final BooleanSetting useShift = new BooleanSetting("Use Shift", "None", true, () -> true);
    public static final NumericSetting placeDelay = new NumericSetting("Place Delay", "None", 130, 50, 500, 15, () -> true);
    public static final ListSetting rotationMode = new ListSetting("Rotations", "None", "Packet", () -> true, "None", "Client", "Packet");
    public static final NumericSetting rotationDelay = new NumericSetting("Rotation Delay", "None", 42, 1, 80, 2, () -> true);
    public static final BooleanSetting toggleLerp = new BooleanSetting("Lerp", "None", true, () -> true);
    protected final Time rotationTimeManager = new Time();
    protected final Time placeTimeManager = new Time();
    protected BlockPos underBlockPos = null;
    protected BlockPos placeBlockPos = null;
    protected EnumFacing facing = null;
    protected float[] playerRotation;
    protected float lerpedPitch = 0, lerpedYaw = 0;

    public Scaffold(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(useSprint, useShift, placeDelay, rotationMode, rotationDelay, toggleLerp);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent && Inventory.isBlockExist()) {
            BlockPos blockPosUnder = new BlockPos(mc.player.posX, mc.player.posY - 0.5D, mc.player.posZ);
            AxisAlignedBB aabb = new AxisAlignedBB(blockPosUnder).expandXyz(-0.4f).expand(0, 3, 0);
            if (mc.world.getBlockState(blockPosUnder).getBlock() == Blocks.AIR) {
                underBlockPos = blockPosUnder;
                for (EnumFacing f : EnumFacing.values()) {
                    BlockPos offset = blockPosUnder.offset(f);
                    if (mc.world.getBlockState(offset).getBlock() != Blocks.AIR) {
                        facing = f;
                        placeBlockPos = offset;
                        break;
                    }
                }
            }
            if (rotationTimeManager.hasReached(Aura.rotationDelay.getNumericValue()) || playerRotation == null) {
                playerRotation = new float[]{ Rotation.getVec3dRotations(World.getVec3d(placeBlockPos))[0], mc.player.rotationPitch, 82f };
                rotationTimeManager.reset();
            }
            if (toggleLerp.getBooleanValue()) {
                lerpedYaw = GCD.getFixedRotation(MathHelper.lerp(lerpedYaw, playerRotation[0], 0.09f));
                lerpedPitch = GCD.getFixedRotation(MathHelper.lerp(lerpedPitch, playerRotation[1], 0.09f));
            }
            switch (rotationMode.getListValue().toLowerCase()) {
                case "client" -> Rotation.setClientRotations(toggleLerp.getBooleanValue() ? new float[]{ lerpedYaw, lerpedPitch } : playerRotation);
                case "packet" -> Rotation.setPacketRotations(motionEvent, toggleLerp.getBooleanValue() ? new float[]{ lerpedYaw, lerpedPitch } : playerRotation);
            }
            mc.player.setSprinting(useSprint.getBooleanValue() && mc.world.getBlockState(blockPosUnder).getBlock() != Blocks.AIR);
            mc.gameSettings.keyBindSneak.pressed = useShift.getBooleanValue() && mc.world.getBlockState(blockPosUnder).getBlock() == Blocks.AIR && aabb.intersectsWith(mc.player.getEntityBoundingBox().expandXyz(0.2f));
            Rotation.setPacketRotations(motionEvent, Rotation.getVec3dRotations(World.getVec3d(placeBlockPos)));
            mc.player.inventory.currentItem = Inventory.getBlock();
            mc.playerController.updateController();
            RayTraceResult rayTrace = mc.world.rayTraceBlocks(World.getVec3d(underBlockPos).addVector(0.1f, 0.1f, 0.1f), World.getVec3d(placeBlockPos).addVector(0.1f, 0.1f, 0.1f));
            if (rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK && aabb.intersectsWith(mc.player.getEntityBoundingBox().expandXyz(-0.1f))) {
                if (mc.player.onGround || !new AxisAlignedBB(blockPosUnder).expand(0, 0.02f, 0).intersectsWith(mc.player.getEntityBoundingBox())) {
                    if (placeTimeManager.hasReached(placeDelay.getNumericValue())) {
                        mc.playerController.updateController();
                        EnumActionResult enumactionresult = mc.playerController.processRightClickBlock(mc.player, mc.world, placeBlockPos, rayTrace.sideHit, rayTrace.hitVec, EnumHand.MAIN_HAND);
                        if (enumactionresult == EnumActionResult.SUCCESS) {
                            mc.player.swingArm(EnumHand.MAIN_HAND);
                            placeTimeManager.reset();
                        }
                        underBlockPos = null;
                    }
                }
            }
        }
        if (event instanceof StrafeEvent strafeEvent) {
            strafeEvent.setYaw(mc.player.rotationYaw);
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            mc.timer.field_194150_d = 1f;
        }
    }

}
