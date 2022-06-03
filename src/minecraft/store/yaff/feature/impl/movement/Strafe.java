package store.yaff.feature.impl.movement;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.IFlagable;
import store.yaff.helper.Movement;

public class Strafe extends AbstractFeature implements IFlagable {
    private int movementTicks = 0;
    private double speed;

    public Strafe(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            if (Movement.hasMotion(true)) {
                switch (movementTicks) {
                    case 1 -> speed = Movement.getSpeed(mc.player.motionX, mc.player.motionZ);
                    case 2 -> speed = Movement.getSpeed(mc.player.motionX, mc.player.motionZ) + (0.041535 * Math.random());
                    case 3 -> speed = Movement.getSpeed(mc.player.motionX, mc.player.motionZ) / 2;
                }
                Movement.setSpeed((float) Math.max(speed, Movement.getSpeed(mc.player.motionX, mc.player.motionZ)));
                movementTicks++;
            }
        }
    }

    @Override
    public void onFlag() {
        super.setState(false);
    }

}
