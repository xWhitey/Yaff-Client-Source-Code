package store.yaff.feature.impl.combat;

import net.minecraft.util.EnumParticleTypes;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.HitEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Random;
import store.yaff.setting.impl.BooleanSetting;

import java.util.Objects;

public class AttackParticles extends AbstractFeature {
    public static final BooleanSetting onCritical = new BooleanSetting("On Critical", "None", true, () -> true);

    public AttackParticles(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(onCritical);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof HitEvent hitEvent) {
            if (!onCritical.getBooleanValue() || hitEvent.getHitType() == HitEvent.HitType.CRITICAL) {
                mc.effectRenderer.emitParticleAtEntity(hitEvent.getEntity(), Objects.requireNonNull(EnumParticleTypes.getParticleFromId(Random.getRandomIntExclude(0, 48, 1, 2, 4, 5, 6, 7, 8, 15, 16, 17, 18, 19, 25, 28, 31, 32, 35, 36, 37, 37, 39, 40, 41, 43, 44, 45, 46, 48))));
            }
        }
    }

}
