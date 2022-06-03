package store.yaff.feature.impl.player;

import net.minecraft.network.play.client.CPacketPlayer;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.MotionEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.ListSetting;

import java.util.Objects;

public class FastUse extends AbstractFeature {
    public static final ListSetting useMode = new ListSetting("Mode", "None", "Matrix", () -> true, "Vanilla", "Matrix");

    public FastUse(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(useMode);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof MotionEvent) {
            switch (useMode.getListValue().toLowerCase()) {
                case "vanilla" -> {
                    if (mc.player.isEating() || mc.player.isDrinking()) {
                        for (int i = 0; i < 35; i++) {
                            Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer(mc.player.onGround));
                        }
                        mc.player.stopActiveHand();
                    }
                }
                case "matrix" -> {
                    if (mc.player.getItemInUseMaxCount() >= 8 && (mc.player.isEating() || mc.player.isDrinking())) {
                        for (int i = 0; i < 12; i++) {
                            Objects.requireNonNull(mc.getConnection()).getNetworkManager().sendPacketWithoutEvent(new CPacketPlayer(mc.player.onGround));
                        }
                        mc.player.stopActiveHand();
                    }
                }
            }
        }
    }

}
