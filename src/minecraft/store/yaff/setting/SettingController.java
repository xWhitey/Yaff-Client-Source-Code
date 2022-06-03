package store.yaff.setting;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SettingController {
    public final CopyOnWriteArrayList<AbstractSetting> settings = new CopyOnWriteArrayList<>();

    public final CopyOnWriteArrayList<AbstractSetting> getSettings() {
        return settings;
    }

    public final void addSettings(@Nonnull AbstractSetting... settingList) {
        this.settings.addAll(List.of(settingList));
    }

}
