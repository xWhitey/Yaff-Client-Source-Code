package store.yaff.feature.impl.combat;

import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.NumericSetting;

import java.util.Objects;

public class Regen extends AbstractFeature {
    public static final NumericSetting minHealth = new NumericSetting("Trigger Health", "None", 14, 0, 20, 1, () -> true);
    public static final NumericSetting regenSpeed = new NumericSetting("Speed", "None", 12, 3, 20, 1, () -> true);

    public Regen(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(minHealth, regenSpeed);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (mc.player.getFoodStats().getFoodLevel() > 3 && mc.player.getHealth() <= minHealth.getNumericValue() && mc.player.getActivePotionEffect(MobEffects.REGENERATION) != null && mc.player.ticksExisted % 3 == 0) {
                for (byte b = 0; b < regenSpeed.getNumericValue(); b++) {
                    Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer(true));
                }
            }
        }
    }

}
