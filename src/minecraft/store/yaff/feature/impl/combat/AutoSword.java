package store.yaff.feature.impl.combat;

import net.minecraft.item.ItemSword;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.AttackEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Inventory;

public class AutoSword extends AbstractFeature {
    public AutoSword(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof AttackEvent) {
            if (!(mc.player.inventory.getCurrentItem().getItem() instanceof ItemSword)) {
                if (Inventory.isSwordExist(true)) {
                    mc.player.inventory.currentItem = Inventory.getBestSword(true);
                    mc.playerController.syncCurrentPlayItem();
                }
            }
        }
    }

}
