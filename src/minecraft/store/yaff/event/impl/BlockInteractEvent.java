package store.yaff.event.impl;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import store.yaff.event.AbstractEvent;

public class BlockInteractEvent extends AbstractEvent {
    protected final BlockPos blockPos;
    protected final EnumFacing blockFacing;

    public BlockInteractEvent(BlockPos blockPos, EnumFacing blockFacing) {
        this.blockPos = blockPos;
        this.blockFacing = blockFacing;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public EnumFacing getBlockFacing() {
        return blockFacing;
    }

}
