package store.yaff.event.impl;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.math.BlockPos;
import store.yaff.event.AbstractEvent;

public class Chunk {
    public static class A extends AbstractEvent {
        protected final RenderChunk renderChunk;
        protected final BlockPos blockPos;

        public A(RenderChunk renderChunk, BlockPos blockPos) {
            this.renderChunk = renderChunk;
            this.blockPos = blockPos;
        }

        public RenderChunk getRenderChunk() {
            return renderChunk;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }

    }

    public static class B extends AbstractEvent {
        protected final RenderChunk renderChunk;

        public B(RenderChunk renderChunk) {
            this.renderChunk = renderChunk;
        }

        public RenderChunk getRenderChunk() {
            return renderChunk;
        }

    }

}
