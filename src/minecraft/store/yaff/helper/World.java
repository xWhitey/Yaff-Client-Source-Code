package store.yaff.helper;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class World {
    public static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isContainer(Block block, boolean includeShulkers) {
        return block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST || (!includeShulkers || (block == Blocks.field_190977_dl || block == Blocks.field_190978_dm || block == Blocks.field_190979_dn || block == Blocks.field_190980_do || block == Blocks.field_190981_dp || block == Blocks.field_190982_dq || block == Blocks.field_190983_dr || block == Blocks.field_190984_ds || block == Blocks.field_190985_dt || block == Blocks.field_190986_du || block == Blocks.field_190987_dv || block == Blocks.field_190988_dw || block == Blocks.field_190989_dx || block == Blocks.field_190990_dy || block == Blocks.field_190991_dz || block == Blocks.field_190975_dA));
    }

    public static boolean isOre(Block block) {
        return block == Blocks.COAL_ORE || block == Blocks.IRON_ORE || block == Blocks.GOLD_ORE || block == Blocks.LAPIS_ORE || block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE || block == Blocks.EMERALD_ORE || block == Blocks.DIAMOND_ORE || block == Blocks.QUARTZ_ORE;
    }

    public static boolean isOreFilter(Block block, boolean includeCoal, boolean includeIron, boolean includeGold, boolean includeLapis, boolean includeRedstone, boolean includeEmerald, boolean includeDiamond, boolean includeQuartz) {
        return (includeCoal && block == Blocks.COAL_ORE) || (includeIron && block == Blocks.IRON_ORE) || (includeGold && block == Blocks.GOLD_ORE) || (includeLapis && block == Blocks.LAPIS_ORE) || (includeRedstone && (block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE)) || (includeEmerald && block == Blocks.EMERALD_ORE) || (includeDiamond && block == Blocks.DIAMOND_ORE) || (includeQuartz && block == Blocks.QUARTZ_ORE);
    }

    public static boolean isSpawner(Block block) {
        return block == Blocks.MOB_SPAWNER;
    }

    public static boolean isFullBlock(Block block) {
        return block.getBlockState().getBaseState().isFullBlock() && block.getBlockState().getBaseState().isFullCube();
    }

    public static boolean isMoveKeyPressed() {
        return mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown();
    }

    public static boolean isOnIce() {
        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1f, mc.player.posZ);
        Block block = mc.world.getBlockState(blockPos).getBlock();
        return block == Blocks.ICE || block == Blocks.PACKED_ICE || block == Blocks.FROSTED_ICE;
    }

    public static boolean isOnWeb() {
        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1f, mc.player.posZ);
        Block block = mc.world.getBlockState(blockPos).getBlock();
        return block == Blocks.WEB;
    }

    public static boolean isOnWater() {
        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.1f, mc.player.posZ);
        Block block = mc.world.getBlockState(blockPos).getBlock();
        return block == Blocks.WATER;
    }

    public static boolean isBlockAboveHead() {
        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY + 2, mc.player.posZ);
        return mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR;
    }

    public static boolean isOnVoid() {
        for (double d = mc.player.posY; d > 0; d--) {
            BlockPos blockPos = new BlockPos(mc.player.posX, d, mc.player.posZ);
            if (mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOnVoid(int blocksDown) {
        for (double d = mc.player.posY; d > (mc.player.posY - blocksDown); d--) {
            BlockPos blockPos = new BlockPos(mc.player.posX, d, mc.player.posZ);
            if (mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOnSameTeam(Entity entityIn) {
        return (mc.player.getDisplayName().getUnformattedText().substring(0, 2).equals(entityIn.getDisplayName().getUnformattedText().substring(0, 2))) || mc.player.isOnSameTeam(entityIn);
    }

    public static boolean isCriticalHit() {
        return (mc.player.fallDistance > 0.08f && !mc.player.onGround && !mc.player.isOnLadder() && !mc.player.isInWater() && !mc.player.isPotionActive(MobEffects.BLINDNESS) && !mc.player.isRiding()) && mc.player.getCooledAttackStrength(0.5f) > 0.9f;
    }

    public static boolean isFalling() {
        return !mc.player.onGround && !mc.player.isOnLadder() && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isInWeb && mc.player.isEntityAlive() && mc.player.fallDistance != 0;
    }

    public static ArrayList<BlockPos> getBlocksInRadius(BlockPos start, BlockPos end) {
        ArrayList<BlockPos> blocks = new ArrayList<>();
        BlockPos min = new BlockPos(java.lang.Math.min(start.getX(), end.getX()), java.lang.Math.min(start.getY(), end.getY()), java.lang.Math.min(start.getZ(), end.getZ()));
        BlockPos max = new BlockPos(java.lang.Math.max(start.getX(), end.getX()), java.lang.Math.max(start.getY(), end.getY()), java.lang.Math.max(start.getZ(), end.getZ()));
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    blocks.add(new BlockPos(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static ArrayList<BlockPos> getBlocks(int x, int y, int z) {
        BlockPos min = new BlockPos(mc.player.posX - x, mc.player.posY - y, mc.player.posZ - z);
        BlockPos max = new BlockPos(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
        return getBlocksInRadius(min, max);
    }

    public static ArrayList<BlockPos> getBlocks(int distanceIn) {
        return getBlocks(distanceIn, distanceIn, distanceIn);
    }

    public static ArrayList<BlockPos> getNearestBlocks(BlockPos blockPos) {
        ArrayList<BlockPos> nearBlocks = new ArrayList<>();
        nearBlocks.add(blockPos.add(1, 0, 0));
        nearBlocks.add(blockPos.add(-1, 0, 0));
        nearBlocks.add(blockPos.add(0, 1, 0));
        nearBlocks.add(blockPos.add(0, -1, 0));
        nearBlocks.add(blockPos.add(0, 0, 1));
        nearBlocks.add(blockPos.add(0, 0, -1));
        return nearBlocks;
    }

    public static Vec3d getVec3d(BlockPos blockPos) {
        return new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

}
