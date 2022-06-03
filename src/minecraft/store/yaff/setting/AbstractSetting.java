package store.yaff.setting;

import java.util.function.Supplier;

public abstract class AbstractSetting {
    protected String name, description;
    protected Supplier<Boolean> visible;

    protected AbstractSetting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Supplier<Boolean> getVisible() {
        return visible;
    }

    public void setVisible(Supplier<Boolean> visible) {
        this.visible = visible;
    }

}
