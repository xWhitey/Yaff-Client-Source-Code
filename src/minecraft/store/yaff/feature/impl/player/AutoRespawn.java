package store.yaff.feature.impl.player;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

public class AutoRespawn extends AbstractFeature {
    public AutoRespawn(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (!mc.player.isEntityAlive()) {
                mc.player.respawnPlayer();
            }
        }
    }

}
