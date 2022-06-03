package store.yaff.setting.impl;

import store.yaff.setting.AbstractSetting;

import java.util.List;
import java.util.function.Supplier;

public class ListSetting extends AbstractSetting {
    protected final List<String> listValues;
    protected String listValue;

    public ListSetting(String name, String description, String listValue, Supplier<Boolean> visible, String... listValues) {
        super(name, description);
        this.listValue = listValue;
        this.listValues = List.of(listValues);
        setVisible(visible);
    }

    public String getListValue() {
        return listValue;
    }

    public void setListValue(String listValue) {
        this.listValue = listValue;
    }

    public List<String> getListValues() {
        return listValues;
    }

}
