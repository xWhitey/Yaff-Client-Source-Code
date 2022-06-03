package store.yaff.feature.impl.combat;

import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Inventory;
import store.yaff.helper.Rotation;
import store.yaff.helper.Time;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public class AutoPotion extends AbstractFeature {
    public static final BooleanSetting autoHeal = new BooleanSetting("AutoHeal", "None", false, () -> true);
    public static final NumericSetting minHealth = new NumericSetting("Trigger Health", "None", 14, 0, 20, 1, () -> true);
    public static final ListSetting swapMode = new ListSetting("Swap", "None", "Client", () -> true, "Client", "Packet");
    public static final BooleanSetting swapBack = new BooleanSetting("Swap Back", "None", true, () -> true);

    private final Time timeManager = new Time();

    public AutoPotion(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(autoHeal, minHealth, swapMode, swapBack);
    }

    public void throwPotion(int itemSlot) {
        int slotBefore = mc.player.inventory.currentItem;
        switch (swapMode.getListValue().toLowerCase()) {
            case "client" -> {
                mc.player.inventory.currentItem = itemSlot;
                mc.playerController.syncCurrentPlayItem();
            }
            case "packet" -> {
                mc.player.connection.sendPacket(new CPacketHeldItemChange(itemSlot));
                mc.playerController.syncCurrentPlayItem();
            }
        }
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        if (swapBack.getBooleanValue()) {
            switch (swapMode.getListValue().toLowerCase()) {
                case "client" -> {
                    mc.player.inventory.currentItem = slotBefore;
                    mc.playerController.syncCurrentPlayItem();
                }
                case "packet" -> {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(slotBefore));
                    mc.playerController.syncCurrentPlayItem();
                }
            }
        }
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent motionEvent) {
            if (Inventory.isPotionExist(true)) {
                if (!autoHeal.getBooleanValue() || mc.player.getHealth() <= minHealth.getNumericValue()) {
                    for (Integer i : Inventory.getPotionSlots(autoHeal.getBooleanValue())) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemPotion) {
                            Rotation.setPacketRotations(motionEvent, new float[]{ mc.player.rotationYaw, 90 });
                            //Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90, true));
                            if (autoHeal.getBooleanValue()) {
                                if (motionEvent.getPitch() == 90 && timeManager.hasReached(240)) {
                                    throwPotion(i);
                                    timeManager.reset();
                                    break;
                                }
                            }
                            for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(mc.player.inventory.getStackInSlot(i))) {
                                if (!mc.player.isPotionActive(potioneffect.getPotion()) && motionEvent.getPitch() == 90 && timeManager.hasReached(240)) {
                                    throwPotion(i);
                                    timeManager.reset();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (!Inventory.isPotionExist(true) && (autoHeal.getBooleanValue() && mc.player.getHealth() > minHealth.getNumericValue())) {
                timeManager.reset();
            }
        }
    }

}
