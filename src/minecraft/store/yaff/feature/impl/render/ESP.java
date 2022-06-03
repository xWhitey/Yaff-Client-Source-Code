package store.yaff.feature.impl.render;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Color;
import store.yaff.helper.Render;

public class ESP extends AbstractFeature {
    public ESP(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof store.yaff.event.impl.Render.Render3D) {
            for (Entity e : mc.world.getLoadedEntityList()) {
                if (!(e instanceof EntityOtherPlayerMP)) {
                    continue;
                }
                Render.renderEntityBox(e, Color.hex(0xFF8585c7, 80));
            }
        }
    }

}
