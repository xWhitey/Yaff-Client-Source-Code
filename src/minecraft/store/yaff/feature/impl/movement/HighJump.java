package store.yaff.feature.impl.movement;

import net.minecraft.network.play.client.CPacketEntityAction;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Time;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class HighJump extends AbstractFeature {
    public static final NumericSetting jumpMotion = new NumericSetting("Motion", "None", 5, 1, 10, 0.1f, () -> true);
    public static final ListSetting jumpMode = new ListSetting("Mode", "None", "Matrix", () -> true, "Matrix");

    private final Time timeManager = new Time();

    public HighJump(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(jumpMotion, jumpMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent) {
            if (jumpMode.getListValue().equalsIgnoreCase("Matrix")) {
                //if (mc.player.hurtTime > 0) {
                if (timeManager.hasReached(350)) {
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    timeManager.reset();
                }
                mc.player.motionY += 0.15000000596046448D;
                mc.player.motionY = jumpMotion.getNumericValue();
                //}
            }
        }
    }

}
