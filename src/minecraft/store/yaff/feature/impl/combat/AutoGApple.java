package store.yaff.feature.impl.combat;

import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

public class AutoGApple extends AbstractFeature {
    public static final NumericSetting minHealth = new NumericSetting("Trigger Health", "None", 14, 0, 20, 1, () -> true);
    public static final BooleanSetting trackCooldown = new BooleanSetting("RW Cooldown", "None", false, () -> true);

    protected boolean isActive;

    public AutoGApple(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(minHealth, trackCooldown);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (mc.player.getHeldItemOffhand().getItem() instanceof ItemAppleGold && !mc.player.getCooldownTracker().hasCooldown(Items.GOLDEN_APPLE) && mc.player.getHealth() <= minHealth.getNumericValue()) {
                mc.gameSettings.keyBindUseItem.pressed = true;
            } else if (isActive) {
                mc.gameSettings.keyBindUseItem.pressed = false;
            }
        }
    }

}
