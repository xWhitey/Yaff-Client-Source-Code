package store.yaff.feature.impl.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

public class Eagle extends AbstractFeature {
    public Eagle(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            mc.gameSettings.keyBindSneak.pressed = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.5D, mc.player.posZ)).getBlock() == Blocks.AIR;
        }
    }

}
