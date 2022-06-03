package store.yaff.feature.impl.world;

import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Inventory;
import store.yaff.helper.Time;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class ChestStealer extends AbstractFeature {
    public static final BooleanSetting randomStealer = new BooleanSetting("randomStealer", "None", true, () -> true);
    public static final NumericSetting stealerSpeed = new NumericSetting("stealerSpeed", "None", 120, 100, 600, 10, () -> true);
    public static final ListSetting stealerMode = new ListSetting("stealerMode", "None", "Collect", () -> true, "Collect", "Drop");

    private final Time timeManager = new Time();

    public ChestStealer(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(randomStealer, stealerSpeed, stealerMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (mc.player.openContainer != null && mc.player.openContainer instanceof ContainerChest chestContainer) {
                if (!Inventory.isChestEmpty(chestContainer)) {
                    for (Byte b : Inventory.getItemStacksInChest(chestContainer, randomStealer.getBooleanValue())) {
                        ItemStack containerStack = chestContainer.getLowerChestInventory().getStackInSlot(b);
                        if (!Inventory.isEmpty(containerStack) && timeManager.hasReached(stealerSpeed.getNumericValue())) {
                            switch (stealerMode.getListValue().toLowerCase()) {
                                case "collect" -> mc.playerController.windowClick(chestContainer.windowId, b, 0, ClickType.QUICK_MOVE, mc.player);
                                case "drop" -> mc.playerController.windowClick(chestContainer.windowId, b, 0, ClickType.THROW, mc.player);
                            }
                            timeManager.reset();
                        }
                    }
                } else {
                    mc.player.closeScreen();
                }
            }
        }
    }

}
