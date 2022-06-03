package store.yaff.feature.impl.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.*;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Color;
import store.yaff.helper.Render;
import store.yaff.helper.Time;
import store.yaff.helper.World;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XRay extends AbstractFeature {
    public static final BooleanSetting showScanner = new BooleanSetting("showScanner", "None", false, () -> true);
    public static final BooleanSetting xrayBypass = new BooleanSetting("xrayBypass", "None", false, () -> true);
    public static final BooleanSetting includeCoal = new BooleanSetting("includeCoal", "None", false, () -> true);
    public static final BooleanSetting includeIron = new BooleanSetting("includeIron", "None", true, () -> true);
    public static final BooleanSetting includeGold = new BooleanSetting("includeGold", "None", true, () -> true);
    public static final BooleanSetting includeLapis = new BooleanSetting("includeLapis", "None", false, () -> true);
    public static final BooleanSetting includeRedstone = new BooleanSetting("includeRedstone", "None", false, () -> true);
    public static final BooleanSetting includeEmerald = new BooleanSetting("includeEmerald", "None", false, () -> true);
    public static final BooleanSetting includeDiamond = new BooleanSetting("includeDiamond", "None", true, () -> true);
    public static final BooleanSetting includeQuartz = new BooleanSetting("includeQuartz", "None", true, () -> true);
    public static final BooleanSetting includeSpawners = new BooleanSetting("includeSpawners", "None", true, () -> true);
    public static final NumericSetting checkDelay = new NumericSetting("checkDelay", "None", 20, 0, 100, 2, () -> true);
    public static final NumericSetting blockGap = new NumericSetting("blockGap", "None", 3, 1, 8, 1, () -> true);
    public static final NumericSetting radiusXZ = new NumericSetting("radiusXZ", "None", 20, 2, 120, 2, () -> true);
    public static final NumericSetting radiusY = new NumericSetting("radiusY", "None", 8, 2, 40, 2, () -> true);
    public static final ListSetting renderMode = new ListSetting("renderMode", "None", "Fill", () -> true, "Fill", "Frame");

    protected static final ArrayList<BlockPos> blocksOre = new ArrayList<>();
    protected static ArrayList<BlockPos> blocksToCheck = new ArrayList<>();
    private final Time timeManager = new Time();

    public XRay(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(showScanner, xrayBypass, includeCoal, includeIron, includeGold, includeLapis, includeRedstone, includeEmerald, includeDiamond, includeQuartz, includeSpawners, checkDelay, blockGap, radiusXZ, radiusY, renderMode);
    }

    public boolean isOre(Block block) {
        return (includeSpawners.getBooleanValue() && World.isSpawner(block)) || World.isOreFilter(block, includeCoal.getBooleanValue(), includeIron.getBooleanValue(), includeGold.getBooleanValue(), includeLapis.getBooleanValue(), includeRedstone.getBooleanValue(), includeEmerald.getBooleanValue(), includeDiamond.getBooleanValue(), includeQuartz.getBooleanValue());
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent) {
            if (xrayBypass.getBooleanValue()) {
                if (timeManager.hasReached(checkDelay.getNumericValue())) {
                    if (blocksToCheck.size() >= 1) {
                        BlockPos blockPos = blocksToCheck.get(0);
                        mc.playerController.clickBlock(blockPos, EnumFacing.UP);
                        blocksToCheck.remove(0);
                    }
                    timeManager.reset();
                }
            }
        }
        if (event instanceof Packet.Receive packetEvent) {
            if (xrayBypass.getBooleanValue()) {
                if (packetEvent.getPacket() instanceof SPacketBlockChange m) {
                    if (isOre(m.getBlockState().getBlock()) && !blocksOre.contains(m.getBlockPosition())) {
                        blocksOre.add(m.getBlockPosition());
                        blocksToCheck.addAll(World.getNearestBlocks(m.getBlockPosition()));
                    }
                } else if (packetEvent.getPacket() instanceof SPacketMultiBlockChange m) {
                    for (SPacketMultiBlockChange.BlockUpdateData blockData : m.getChangedBlocks()) {
                        if (isOre(blockData.getBlockState().getBlock()) && !blocksOre.contains(blockData.getPos())) {
                            blocksOre.add(blockData.getPos());
                            blocksToCheck.addAll(World.getNearestBlocks(blockData.getPos()));
                        }
                    }
                }
            }
        }
        if (event instanceof store.yaff.event.impl.Render.Render3D) {
            if (blocksToCheck.size() >= 1 && showScanner.getBooleanValue()) {
                BlockPos bp = blocksToCheck.get(0);
                if (!blocksOre.contains(bp))
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(bp, Color.rgba(211, 54, 80, 60), 0, 0);
                    }
                if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                    Render.renderBlockFrame(bp, Color.rgba(211, 54, 80, 60), 0, 0);
                }
            }
            for (BlockPos p : blocksOre) {
                if (mc.world.getBlockState(p).getBlock() == Blocks.COAL_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(21, 21, 21, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(21, 21, 21, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.IRON_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(236, 236, 236, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(236, 236, 236, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.GOLD_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(255, 225, 35, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(255, 225, 35, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.LAPIS_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(36, 113, 255, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(36, 113, 255, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.REDSTONE_ORE || mc.world.getBlockState(p).getBlock() == Blocks.LIT_REDSTONE_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(211, 54, 80, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(211, 54, 80, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.EMERALD_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(116, 255, 77, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(116, 255, 77, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.DIAMOND_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(55, 255, 224, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(55, 255, 224, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.QUARTZ_ORE) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(255, 255, 255, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(255, 255, 255, 60), 0, 0);
                    }
                }
                if (mc.world.getBlockState(p).getBlock() == Blocks.MOB_SPAWNER) {
                    if (renderMode.getListValue().equalsIgnoreCase("Fill")) {
                        Render.renderBlock(p, Color.rgba(151, 109, 234, 60), 0, 0);
                    }
                    if (renderMode.getListValue().equalsIgnoreCase("Frame")) {
                        Render.renderBlockFrame(p, Color.rgba(151, 109, 234, 60), 0, 0);
                    }
                }
            }
        }
        if (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) {
            for (BlockPos p : World.getBlocks(Math.round(radiusXZ.getNumericValue()), Math.round(radiusY.getNumericValue()), Math.round(radiusXZ.getNumericValue()))) {
                if (isOre(mc.world.getBlockState(p).getBlock())) {
                    if (xrayBypass.getBooleanValue()) {
                        blocksToCheck.add(p);
                    } else {
                        blocksOre.add(p);
                    }
                }
            }
            blocksToCheck = (ArrayList<BlockPos>) IntStream.range(0, blocksToCheck.size()).filter(n -> n % blockGap.getNumericValue() == 0).mapToObj(blocksToCheck::get).collect(Collectors.toList());
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            blocksOre.clear();
            blocksToCheck.clear();
        }
    }

}
