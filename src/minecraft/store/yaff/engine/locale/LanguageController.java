package store.yaff.engine.locale;

import net.minecraft.client.resources.Locale;
import store.yaff.Yaff;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class LanguageController {
    public static Locale YI18n;

    public static void loadLocaleData() {
        YI18n = new Locale();
        try {
            YI18n.loadLocaleData(new FileInputStream(Objects.requireNonNull(Yaff.of.fileManager.getFile(store.yaff.file.impl.Locale.class)).getPath()));
        } catch (IOException e) {
            Yaff.LOGGER.fatal("[Can't load Yaff locale file]");
            Yaff.safeShutdown();
        }
        /*
        for (AbstractFeature f : Objects.requireNonNull(Yaff.of.featureManager.getFeatures())) {
            f.setDescription(store.yaff.engine.locale.YI18n.format("yaff.client.feature." + f.getName().toLowerCase() + ".description"));
        }
         */
    }

}
