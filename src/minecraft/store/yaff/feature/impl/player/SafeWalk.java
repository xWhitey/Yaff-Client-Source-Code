package store.yaff.feature.impl.player;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MoveEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.World;
import store.yaff.setting.impl.BooleanSetting;

public class SafeWalk extends AbstractFeature {
    public static final BooleanSetting includeJumps = new BooleanSetting("Include Jumps", "None", true, () -> true);

    public SafeWalk(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(includeJumps);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MoveEvent moveEvent) {
            double x = moveEvent.getX();
            double y = moveEvent.getY();
            double z = moveEvent.getZ();
            if (mc.player.onGround || (includeJumps.getBooleanValue() && World.isOnVoid(1))) {
                double increment = 0.05;
                while (x != 0.0) {
                    if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(x, -1.0, 0.0)).isEmpty()) {
                        break;
                    }
                    if (x < increment && x >= -increment) {
                        x = 0.0;
                    } else if (x > 0.0) {
                        x -= increment;
                    } else {
                        x += increment;
                    }
                }
                while (z != 0.0) {
                    if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -1.0, z)).isEmpty()) {
                        break;
                    }
                    if (z < increment && z >= -increment) {
                        z = 0.0;
                    } else if (z > 0.0) {
                        z -= increment;
                    } else {
                        z += increment;
                    }
                }
                while (x != 0.0 && z != 0.0 && mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(x, -1.0, z)).isEmpty()) {
                    if (x < increment && x >= -increment) {
                        x = 0.0;
                    } else if (x > 0.0) {
                        x -= increment;
                    } else {
                        x += increment;
                    }
                    if (z < increment && z >= -increment) {
                        z = 0.0;
                    } else if (z > 0.0) {
                        z -= increment;
                    } else {
                        z += increment;
                    }
                }
            }
            moveEvent.setX(x);
            moveEvent.setY(y);
            moveEvent.setZ(z);
        }
    }

}
