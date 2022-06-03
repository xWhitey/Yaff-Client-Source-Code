package store.yaff.feature.impl.misc;

import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MiddleClickEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.Inventory;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;

public class MCP extends AbstractFeature {
    public static final ListSetting swapMode = new ListSetting("Swap", "None", "Client", () -> true, "Client", "Packet");
    public static final BooleanSetting swapBack = new BooleanSetting("Swap Back", "None", true, () -> true);

    public MCP(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(swapMode, swapBack);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MiddleClickEvent) {
            if (Inventory.isPearlExist()) {
                int slotBefore = mc.player.inventory.currentItem;
                switch (swapMode.getListValue().toLowerCase()) {
                    case "client" -> {
                        mc.player.inventory.currentItem = Inventory.getPearl();
                        mc.playerController.syncCurrentPlayItem();
                    }
                    case "packet" -> {
                        mc.player.connection.sendPacket(new CPacketHeldItemChange(Inventory.getPearl()));
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
        }
    }

}
