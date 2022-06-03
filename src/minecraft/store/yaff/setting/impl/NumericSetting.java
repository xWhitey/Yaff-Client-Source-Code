package store.yaff.setting.impl;

import store.yaff.setting.AbstractSetting;

import java.util.function.Supplier;

public class NumericSetting extends AbstractSetting {
    protected final float minimumValue, maximumValue, incrementValue;
    protected float numericValue;

    public NumericSetting(String name, String description, float numericValue, float minimumValue, float maximumValue, float incrementValue, Supplier<Boolean> visible) {
        super(name, description);
        this.numericValue = numericValue;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.incrementValue = incrementValue;
        setVisible(visible);
    }

    public float getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(float numericValue) {
        this.numericValue = numericValue;
    }

    public float getMinimumValue() {
        return minimumValue;
    }

    public float getMaximumValue() {
        return maximumValue;
    }

    public float getIncrementValue() {
        return incrementValue;
    }

}
