package store.yaff.file.impl;

import com.google.gson.*;
import store.yaff.Yaff;
import store.yaff.feature.AbstractFeature;
import store.yaff.file.AbstractFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Objects;

public class Configuration extends AbstractFile {
    public Configuration(String name, String extension) {
        super(name, extension);
    }

    public static void saveConfiguration() {
        JsonObject jsonObject = new JsonObject();
        JsonObject featuresObject = new JsonObject();
        for (AbstractFeature f : Objects.requireNonNull(Yaff.of.featureManager.getFeatures())) {
            featuresObject.add("yaff." + f.getClass().getSimpleName().toLowerCase(), f.saveFeatureData());
        }
        jsonObject.add("Features", featuresObject);
        try {
            PrintWriter sWriter = new PrintWriter(Objects.requireNonNull(Yaff.of.fileManager.getFile(Configuration.class)).getPath());
            sWriter.println(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
            sWriter.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    public static void loadConfiguration() {
        try {
            JsonElement jsonElement = JsonParser.parseReader(new BufferedReader(new FileReader(Objects.requireNonNull(Yaff.of.fileManager.getFile(Configuration.class)).getPath())));
            if (jsonElement instanceof JsonNull) {
                return;
            }
            JsonObject modulesObject = jsonElement.getAsJsonObject().get("Features").getAsJsonObject();
            for (AbstractFeature f : Objects.requireNonNull(Yaff.of.featureManager.getFeatures())) {
                f.setState(false);
                f.loadFeatureData(modulesObject.getAsJsonObject("yaff." + f.getClass().getSimpleName().toLowerCase()));
            }
            saveConfiguration();
        } catch (Exception ignored) {
        }
    }

}
