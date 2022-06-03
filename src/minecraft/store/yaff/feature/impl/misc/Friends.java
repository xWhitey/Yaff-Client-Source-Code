package store.yaff.feature.impl.misc;

import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.AttackEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;

public class Friends extends AbstractFeature {
    public static final BooleanSetting checkUUID = new BooleanSetting("Check UUID", "None", true, () -> true);
    public static final BooleanSetting checkName = new BooleanSetting("Check Name", "None", false, () -> true);
    public static final BooleanSetting preventAttack = new BooleanSetting("Prevent Attack", "None", true, () -> true);

    public Friends(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(checkUUID, checkName, preventAttack);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof AttackEvent attackEvent) {
            if (preventAttack.getBooleanValue()) {
                if (!(Yaff.of.friendController.getFriend(attackEvent.getEntity().getUniqueID()) == null && checkUUID.getBooleanValue()) && !(Yaff.of.friendController.getFriend(attackEvent.getEntity().getName()) == null && checkName.getBooleanValue())) {
                    attackEvent.setCancelled(true);
                }
            }
        }
    }

}
