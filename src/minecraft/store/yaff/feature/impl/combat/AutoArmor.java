package store.yaff.feature.impl.combat;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemArmor;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Inventory;
import store.yaff.helper.Time;
import store.yaff.setting.impl.NumericSetting;

import java.util.Objects;

public class AutoArmor extends AbstractFeature {
    public static final NumericSetting equipmentSpeed = new NumericSetting("Equipment Speed", "None", 120, 100, 600, 10, () -> true);

    private final Time timeManager = new Time();

    public AutoArmor(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(equipmentSpeed);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (mc.currentScreen instanceof GuiInventory) {
                if (!Inventory.isInventoryFull(36)) {
                    for (Integer i : Inventory.getRArmorSlots(true).stream().filter(Objects::nonNull).toList()) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemArmor itemArmor) {
                            if (timeManager.hasReached(equipmentSpeed.getNumericValue())) {
                                if (mc.player.inventory.armorItemInSlot(itemArmor.armorType.getIndex()).getItem() instanceof ItemArmor armorInSlot) {
                                    if (Inventory.getTotalArmorValue(itemArmor, mc.player.inventory.getStackInSlot(i)) > Inventory.getTotalArmorValue(armorInSlot, mc.player.inventory.getStackInSlot(8 - itemArmor.armorType.getIndex()))) {
                                        //mc.playerController.updateController();
                                        //Inventory.swapSlots(8 - itemArmor.armorType.getIndex(), i <= 9 ? 36 + i : i);
                                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 8 - itemArmor.armorType.getIndex(), 0, ClickType.QUICK_MOVE, mc.player);
                                        mc.playerController.updateController();
                                        timeManager.reset();
                                        return;
                                    }
                                    //} else {
                                    //    mc.playerController.updateController();
                                    //    Inventory.swapSlots(i <= 9 ? 36 + i : i, 8 - itemArmor.armorType.getIndex());
                                    //    timeManager.reset();
                                    //}
                                }
                                if (mc.player.inventory.armorItemInSlot(itemArmor.armorType.getIndex()).getItem() instanceof ItemAir) {
                                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i <= 9 ? 36 + i : i, 0, ClickType.QUICK_MOVE, mc.player);
                                    mc.playerController.updateController();
                                    timeManager.reset();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
