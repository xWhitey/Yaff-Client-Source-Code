package store.yaff.feature;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.FeatureStateEvent;
import store.yaff.file.impl.Configuration;
import store.yaff.setting.AbstractSetting;
import store.yaff.setting.SettingController;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.ListSetting;
import store.yaff.setting.impl.NumericSetting;

public abstract class AbstractFeature extends SettingController {
    protected final Category category;
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final String name, description;
    public float animationDelay, animationDelayAfter;
    protected boolean state, visible;
    protected BindType bindType = BindType.TOGGLE;
    protected int key;

    protected AbstractFeature(String name, String description, Category category, int key) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.key = key;
        this.state = false;
        this.visible = true;
    }

    public abstract void onEvent(AbstractEvent event);

    public void toggle() {
        this.state = !this.state;
        FeatureStateEvent featureEvent = new FeatureStateEvent(this, this.state);
        featureEvent.call();
        Configuration.saveConfiguration();
    }

    public void onKeyDown(int key) {
        if (key == this.key) {
            this.toggle();
        }
    }

    public void onKeyUp(int key) {
        if (key == this.key && this.bindType == BindType.HOLD) {
            this.setState(false);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
        FeatureStateEvent featureEvent = new FeatureStateEvent(this, state);
        featureEvent.call();
        Configuration.saveConfiguration();
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public BindType getBindType() {
        return bindType;
    }

    public void setBindType(BindType bindType) {
        this.bindType = bindType;
    }

    public JsonObject saveFeatureData() {
        JsonObject object = new JsonObject();
        JsonObject propertiesObject = new JsonObject();
        for (AbstractSetting s : getSettings()) {
            if (s instanceof BooleanSetting b) {
                propertiesObject.addProperty(s.getName(), b.getBooleanValue());
            }
            if (s instanceof ListSetting l) {
                propertiesObject.addProperty(s.getName(), l.getListValue());
            }
            if (s instanceof NumericSetting n) {
                propertiesObject.addProperty(s.getName(), n.getNumericValue());
            }
            object.add("Settings", propertiesObject);
        }
        object.addProperty("state", getState());
        object.addProperty("key", getKey());
        object.addProperty("bind", getBindType().name());
        object.addProperty("visible", getVisible());
        return object;
    }

    public void loadFeatureData(JsonObject jsonFeature) {
        if (jsonFeature != null) {
            for (AbstractSetting s : getSettings()) {
                JsonObject propertiesObject = jsonFeature.getAsJsonObject("Settings");
                if (s == null || propertiesObject == null || !propertiesObject.has(s.getName())) {
                    continue;
                }
                if (s instanceof BooleanSetting b) {
                    b.setBooleanValue(propertiesObject.get(s.getName()).getAsBoolean());
                }
                if (s instanceof ListSetting l) {
                    l.setListValue(propertiesObject.get(s.getName()).getAsString());
                }
                if (s instanceof NumericSetting n) {
                    n.setNumericValue(MathHelper.clamp(propertiesObject.get(s.getName()).getAsFloat(), n.getMinimumValue(), n.getMaximumValue()));
                }
            }
            if (jsonFeature.has("state")) {
                setState(jsonFeature.get("state").getAsBoolean());
            }
            if (jsonFeature.has("key")) {
                setKey(jsonFeature.get("key").getAsInt());
            }
            if (jsonFeature.has("bind")) {
                try {
                    setBindType(BindType.valueOf(jsonFeature.get("bind").getAsString()));
                } catch (Exception ignored) {
                }
            }
            if (jsonFeature.has("visible")) {
                setVisible(jsonFeature.get("visible").getAsBoolean());
            }
        }
    }

    public enum BindType {
        TOGGLE, HOLD
    }

}
