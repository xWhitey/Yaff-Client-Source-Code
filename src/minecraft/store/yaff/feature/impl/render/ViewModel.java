package store.yaff.feature.impl.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.TransformSideFirstPerson;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.NumericSetting;

public class ViewModel extends AbstractFeature {

    public static NumericSetting rightx = new NumericSetting("Right X", "None", 0, -2, 2, 0.1f, () -> true);
    public static NumericSetting righty = new NumericSetting("Right Y", "None", 0, -2, 2, 0.1f, () -> true);
    public static NumericSetting rightz = new NumericSetting("Right Z", "None", 0, -2, 2, 0.1f, () -> true);
    public static NumericSetting leftx = new NumericSetting("Left X", "None", 0, -2, 2, 0.1f, () -> true);
    public static NumericSetting lefty = new NumericSetting("Left Y", "None", 0, -2, 2, 0.1f, () -> true);
    public static NumericSetting leftz = new NumericSetting("Left Z", "None", 0, -2, 2, 0.1f, () -> true);

    public ViewModel(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(rightx, righty, rightz, leftx, lefty, leftz);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof TransformSideFirstPerson transformSideFirstPerson) {
            if (transformSideFirstPerson.getEnumHandSide() == EnumHandSide.RIGHT) {
                GlStateManager.translate(rightx.getNumericValue(), righty.getNumericValue(), rightz.getNumericValue());
            }
            if (transformSideFirstPerson.getEnumHandSide() == EnumHandSide.LEFT) {
                GlStateManager.translate(-leftx.getNumericValue(), lefty.getNumericValue(), leftz.getNumericValue());
            }
        }
    }

}
