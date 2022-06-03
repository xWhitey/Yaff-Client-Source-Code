package store.yaff.setting.impl;

import store.yaff.setting.AbstractSetting;

import java.util.function.Supplier;

public class BooleanSetting extends AbstractSetting {
    protected boolean booleanValue;

    public BooleanSetting(String name, String description, boolean booleanValue, Supplier<Boolean> visible) {
        super(name, description);
        this.booleanValue = booleanValue;
        setVisible(visible);
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

}
