package store.yaff.feature.impl.hud;

import store.yaff.Yaff;
import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.font.TTFFontManager;
import store.yaff.helper.Color;
import store.yaff.helper.Render;
import store.yaff.helper.tenacity.GL;
import store.yaff.setting.impl.NumericSetting;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ArrayList extends AbstractFeature {
    public static final NumericSetting backgroundAlpha = new NumericSetting("BG Alpha", "None", 140, 0, 255, 1, () -> true);
    public static final NumericSetting glowAlpha = new NumericSetting("Glow Alpha", "None", 110, 0, 255, 1, () -> true);

    protected float arrayY = 0;

    public ArrayList(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(backgroundAlpha, glowAlpha);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof store.yaff.event.impl.Render.Render2D renderEvent) {
            float indentHeight = 2;
            arrayY += indentHeight;
            float width = renderEvent.getScaledResolution().getScaledWidth();
            List<AbstractFeature> featureList = Objects.requireNonNull(Yaff.of.featureManager.getFeatures()).stream().filter(f -> f.getVisible() && f.getState()).sorted(Comparator.comparingDouble(m -> -TTFFontManager.fontRenderer14SGM.getWidth(m.getName()))).toList();
            GL.setup2DRendering(() -> {
                for (AbstractFeature f : featureList) {
                    Render.drawRoundedRectXY(width - TTFFontManager.fontRenderer14SGM.getWidth(f.getName()) - 4, arrayY - indentHeight, TTFFontManager.fontRenderer14SGM.getWidth(f.getName()) + 4, TTFFontManager.fontRenderer14SGM.getHeight(f.getName()) + indentHeight, 0, Color.rgba(0, 0, 20, (int) backgroundAlpha.getNumericValue()));
                    Render.drawRoundedRectXY(width - TTFFontManager.fontRenderer14SGM.getWidth(f.getName()) - 4 - 2, arrayY - indentHeight, 2, TTFFontManager.fontRenderer14SGM.getHeight(f.getName()) + indentHeight, 0, Color.hex(0xFF7D7DA3));
                    Color.glColor(Color.colorMix(Color.hex(0xFFE8BDDB), Color.hex(0xFFBFC2FE), Math.abs(System.currentTimeMillis() / 10L) / 100D + 6D * arrayY * 2.55D / 60D)); //
                    TTFFontManager.fontRenderer14SGM.drawString(f.getName(), width - TTFFontManager.fontRenderer14SGM.getWidth(f.getName()) - 2, arrayY - indentHeight / 2f, Color.colorMix(Color.hex(0xFFE8BDDB), Color.hex(0xFFBFC2FE), Math.abs(System.currentTimeMillis() / 10L) / 100D + 6D * arrayY * 2.55D / 60D));
                    arrayY += TTFFontManager.fontRenderer14SGM.getHeight(f.getName()) + indentHeight;
                }
            });
            arrayY = 0;
        }
    }

}
