package store.yaff.feature.impl.render;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Color;
import store.yaff.helper.Render;

public class StorageESP extends AbstractFeature {
    public StorageESP(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof store.yaff.event.impl.Render.Render3D) {
            for (TileEntity t : mc.world.loadedTileEntityList) {
                if (!(t instanceof TileEntityChest) && !(t instanceof TileEntityEnderChest)) {
                    continue;
                }
                Render.renderBlock(t.getPos(), Color.hex(0xFF8585c7, 80), 0.06f, 0.12f);
            }
        }
    }

}
