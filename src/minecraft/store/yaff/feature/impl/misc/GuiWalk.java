package store.yaff.feature.impl.misc;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureDisableEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

import java.util.Arrays;

public class GuiWalk extends AbstractFeature {
    protected final KeyBinding[] moveKeys = new KeyBinding[]{ mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint };

    public GuiWalk(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
                Arrays.stream(moveKeys).forEach(k -> KeyBinding.setKeyBindState(k.getKeyCode(), Keyboard.isKeyDown(k.getKeyCode())));
            }
        }
        if (event instanceof FeatureDisableEvent disableEvent && disableEvent.getFeature().equals(this)) {
            Arrays.stream(moveKeys).forEach(k -> KeyBinding.setKeyBindState(k.getKeyCode(), false));
        }
    }

}
