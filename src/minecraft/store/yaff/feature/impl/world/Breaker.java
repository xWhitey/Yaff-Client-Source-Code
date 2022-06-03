package store.yaff.feature.impl.world;

import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.event.impl.Render;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Color;
import store.yaff.helper.Rotation;
import store.yaff.helper.Time;
import store.yaff.helper.World;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class Breaker extends AbstractFeature {
    public static final NumericSetting breakerRadius = new NumericSetting("Radius", "None", 4, 1, 8, 1, () -> true);
    public static final NumericSetting breakerDelay = new NumericSetting("Delay", "None", 4, 1, 300, 1, () -> true);
    public static final ListSetting renderMode = new ListSetting("Render Mode", "None", "Fill", () -> true, "Fill", "Frame");

    private final Time timeManager = new Time();

    public Breaker(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(breakerRadius, breakerDelay, renderMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            for (BlockPos b : World.getBlocks((int) breakerRadius.getNumericValue())) {
                if (mc.world.getBlockState(b).getBlock() == Blocks.BED) {
                    Rotation.setPacketRotations(motionEvent, Rotation.getVec3dRotations(new Vec3d(b.getX() + 0.5F, b.getY() + 0.5F, b.getZ() + 0.5F)));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, b, mc.player.getHorizontalFacing()));
                    if (timeManager.hasReached(breakerDelay.getNumericValue())) {
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, b, mc.player.getHorizontalFacing()));
                        mc.player.swingArm(EnumHand.MAIN_HAND);
                        timeManager.reset();
                    }
                }
            }
        }
        if (event instanceof Render.Render3D) {
            for (BlockPos b : World.getBlocks((int) breakerRadius.getNumericValue())) {
                if (mc.world.getBlockState(b).getBlock() == Blocks.BED) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        store.yaff.helper.Render.renderBlock(b, Color.rgba(211, 54, 80, 160), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        store.yaff.helper.Render.renderBlockFrame(b, Color.rgba(211, 54, 80, 200), 0, 0);
                    }
                }
            }
        }
    }

}
