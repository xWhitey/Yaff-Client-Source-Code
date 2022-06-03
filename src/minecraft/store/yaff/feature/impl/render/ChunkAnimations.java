package store.yaff.feature.impl.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Chunk;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.NumericSetting;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ChunkAnimations extends AbstractFeature {
    public static final NumericSetting animationDelay = new NumericSetting("Delay", "None", 320, 0, 1000, 10, () -> true);

    protected final HashMap<RenderChunk, AtomicLong> chunkMap = new HashMap<>();

    public ChunkAnimations(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    public double slideUp(double t) {
        return (--t) * t * t + 1;
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Chunk.A chunkAEvent) {
            if (mc.player != null) {
                if (!chunkMap.containsKey(chunkAEvent.getRenderChunk())) {
                    chunkMap.put(chunkAEvent.getRenderChunk(), new AtomicLong(-1L));
                }
            }
        }
        if (event instanceof Chunk.B chunkBEvent) {
            if (chunkMap.containsKey(chunkBEvent.getRenderChunk())) {
                AtomicLong timeAlive = chunkMap.get(chunkBEvent.getRenderChunk());
                long timeClone = timeAlive.get();
                if (timeClone == -1L) {
                    timeClone = System.currentTimeMillis();
                    timeAlive.set(timeClone);
                }
                long timeDifference = System.currentTimeMillis() - timeClone;
                if (timeDifference <= animationDelay.getNumericValue()) {
                    double chunkY = chunkBEvent.getRenderChunk().getPosition().getY();
                    double offsetY = chunkY * slideUp(timeDifference / animationDelay.getNumericValue());
                    GlStateManager.translate(0.0, -chunkY + offsetY, 0.0);
                }
            }
        }
    }

}
