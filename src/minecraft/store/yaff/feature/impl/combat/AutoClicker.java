package store.yaff.feature.impl.combat;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Random;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

public class AutoClicker extends AbstractFeature {
    public static final NumericSetting clickChance = new NumericSetting("Click Chance", "None", 70, 1, 100, 1, () -> true);
    public static final BooleanSetting useCooldown = new BooleanSetting("Cooldown", "None", true, () -> true);

    public AutoClicker(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(clickChance, useCooldown);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (mc.gameSettings.keyBindAttack.isKeyDown() && Random.getRandomInt(1, 100) <= clickChance.getNumericValue()) {
                if (!useCooldown.getBooleanValue() || mc.player.getCooledAttackStrength(0) == 1) {
                    mc.leftClickCounter = 0;
                    mc.clickMouse();
                }
            }
        }
    }

}
