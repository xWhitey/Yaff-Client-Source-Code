package store.yaff.feature.impl.render;

import store.yaff.event.AbstractEvent;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

public class Scoreboard extends AbstractFeature {
    public static final BooleanSetting noScoreboard = new BooleanSetting("Hide Scoreboard", "None", false, () -> true);
    public static final BooleanSetting removeNumbers = new BooleanSetting("Remove Numbers", "None", true, () -> true);
    public static final NumericSetting roundRadius = new NumericSetting("Round Radius", "None", 5, 1, 8, 1, () -> true);

    public Scoreboard(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(noScoreboard, removeNumbers, roundRadius);
    }

    @Override
    public void onEvent(AbstractEvent event) {
    }

}
