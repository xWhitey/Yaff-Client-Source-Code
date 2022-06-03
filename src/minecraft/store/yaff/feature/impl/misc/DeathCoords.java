package store.yaff.feature.impl.misc;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Chat;

public class DeathCoords extends AbstractFeature {
    public DeathCoords(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (!mc.player.isEntityAlive()) {
                if (mc.player.deathTime < 1f) {
                    Chat.addChatMessage("Death Coordinates: X: " + mc.player.posX + " Y: " + mc.player.posY + " Z: " + mc.player.posZ);
                }
            }
        }
    }

}
