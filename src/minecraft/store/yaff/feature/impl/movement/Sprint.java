package store.yaff.feature.impl.movement;

import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.StrafeEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.feature.impl.combat.Aura;
import store.yaff.helper.Movement;
import store.yaff.helper.World;
import store.yaff.setting.impl.BooleanSetting;

import java.util.Objects;

public class Sprint extends AbstractFeature {
    public static final BooleanSetting omniSprint = new BooleanSetting("Omni", "None", true, () -> true);

    public Sprint(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(omniSprint);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof StrafeEvent) {
            if (World.isMoveKeyPressed() && Movement.shouldSprint()) {
                if ((!Objects.requireNonNull(Yaff.of.featureManager.getFeature(Aura.class)).getState() || !Aura.dynamicSprintState) || Aura.targetEntity == null) {
                    if (omniSprint.getBooleanValue()) {
                        mc.player.setSprinting(true);
                    } else if (mc.gameSettings.keyBindForward.pressed) {
                        mc.player.setSprinting(true);
                    }
                }
            }
        }
    }

}
