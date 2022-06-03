package store.yaff.feature.impl.render;

import net.minecraft.util.math.Vec3d;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.event.impl.Render;
import store.yaff.event.impl.WorldEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

import java.util.ArrayList;
import java.util.List;

public class Breadcrumbs extends AbstractFeature {

    private final BooleanSetting timeoutBool = new BooleanSetting("Timeout", "None", true, () -> true);
    private final NumericSetting timeout = new NumericSetting("Time", "None", 15, 1, 150, 0.1F, () -> true);
    List<Vec3d> path = new ArrayList<>();

    public Breadcrumbs(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(timeout, timeoutBool);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            if (mc.player.lastTickPosX != mc.player.posX || mc.player.lastTickPosY != mc.player.posY || mc.player.lastTickPosZ != mc.player.posZ) {
                path.add(new Vec3d(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ));
            }
            if (timeoutBool.getBooleanValue())
                while (path.size() > (int) timeout.getNumericValue()) {
                    path.remove(0);
                }
        }
        if (event instanceof Render.Render3D) {
            store.yaff.helper.Render.renderBreadCrumbs(path);
        }
        if (event instanceof WorldEvent || (event instanceof FeatureStateEvent stateEvent && stateEvent.getFeature().equals(this)) || (event instanceof FeatureStateEvent featureStateEvent && featureStateEvent.getFeature().equals(this))) {
            path.clear();
        }
    }

}
