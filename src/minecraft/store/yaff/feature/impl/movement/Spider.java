package store.yaff.feature.impl.movement;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Time;
import store.yaff.setting.impl.NumericSetting;

public class Spider extends AbstractFeature {
    public static NumericSetting spiderTicks = new NumericSetting("Ticks", "None", 2, 0, 5, 0.1f, () -> true);

    private final Time timeManager = new Time();

    public Spider(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(spiderTicks);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            if (mc.gameSettings.keyBindForward.pressed && mc.player.isCollidedHorizontally) {
                if (timeManager.hasReached(spiderTicks.getNumericValue() * 100)) {
                    motionEvent.setOnGround(true);
                    mc.player.onGround = true;
                    mc.player.isCollidedVertically = true;
                    mc.player.isCollidedHorizontally = true;
                    mc.player.isAirBorne = true;
                    mc.player.jump();
                    timeManager.reset();
                }
            }
        }
    }

}
