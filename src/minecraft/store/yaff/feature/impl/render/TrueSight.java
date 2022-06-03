package store.yaff.feature.impl.render;

import net.minecraft.entity.Entity;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

import java.util.ArrayList;

public class TrueSight extends AbstractFeature {
    protected final ArrayList<Entity> invisibleEntity = new ArrayList<>();

    public TrueSight(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            mc.world.getLoadedEntityList().stream().filter(e -> e.isEntityAlive() && e.isInvisible() && e != mc.player).forEach(invisibleEntity::add);
            invisibleEntity.forEach(e -> e.setInvisible(false));
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this))) {
            invisibleEntity.forEach(e -> e.setInvisible(true));
            invisibleEntity.clear();
        }
    }

}
