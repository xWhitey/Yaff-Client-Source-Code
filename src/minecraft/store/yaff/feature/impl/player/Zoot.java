package store.yaff.feature.impl.player;

import net.minecraft.init.MobEffects;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;

import java.util.Objects;

public class Zoot extends AbstractFeature {
    public static final BooleanSetting removeBlindness = new BooleanSetting("Blindness", "None", false, () -> true);
    public static final BooleanSetting removeNausea = new BooleanSetting("Nausea", "None", false, () -> true);

    public Zoot(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(removeBlindness, removeNausea);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (removeBlindness.getBooleanValue() && mc.player.isPotionActive(Objects.requireNonNull(MobEffects.BLINDNESS))) {
                mc.player.removeActivePotionEffect(Objects.requireNonNull(MobEffects.BLINDNESS));
            }
            if (removeNausea.getBooleanValue() && mc.player.isPotionActive(Objects.requireNonNull(MobEffects.NAUSEA))) {
                mc.player.removeActivePotionEffect(Objects.requireNonNull(MobEffects.NAUSEA));
            }
        }
    }

}
