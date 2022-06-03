package store.yaff.feature.impl.misc;

import net.minecraft.client.settings.GameSettings;
import org.lwjgl.opengl.Display;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.UpdateEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;

public class CPUReducer extends AbstractFeature {
    public CPUReducer(String name, String description, Category category, int key) {
        super(name, description, category, key);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof UpdateEvent) {
            mc.gameSettings.limitFramerate = Display.isActive() ? (int) GameSettings.Options.FRAMERATE_LIMIT.getValueMax() : 5;
        }
    }

}
