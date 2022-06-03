package store.yaff.feature.impl.misc;

import store.yaff.event.AbstractEvent;
import store.yaff.event.impl.Message;
import store.yaff.feature.AbstractFeature;
import store.yaff.feature.Category;
import store.yaff.helper.StringGenerator;
import store.yaff.setting.impl.BooleanSetting;
import store.yaff.setting.impl.NumericSetting;

public class ChatBypass extends AbstractFeature {
    public static final BooleanSetting useLower = new BooleanSetting("Use Lower", "None", true, () -> true);
    public static final BooleanSetting useUpper = new BooleanSetting("Use Upper", "None", true, () -> true);
    public static final BooleanSetting useDigits = new BooleanSetting("Use Digits", "None", true, () -> true);
    public static final BooleanSetting useSymbols = new BooleanSetting("Use Symbols", "None", true, () -> true);
    public static final BooleanSetting beforeMessage = new BooleanSetting("Before", "None", true, () -> true);
    public static final BooleanSetting afterMessage = new BooleanSetting("After", "None", true, () -> true);
    public static final NumericSetting stringLength = new NumericSetting("Length", "None", 8, 2, 8, 1, () -> true);

    protected final StringGenerator stringGenerator = new StringGenerator(useLower.getBooleanValue(), useUpper.getBooleanValue(), useDigits.getBooleanValue(), useSymbols.getBooleanValue());

    public ChatBypass(String name, String description, Category category, int key) {
        super(name, description, category, key);
        addSettings(useLower, useUpper, useDigits, useSymbols, beforeMessage, afterMessage, stringLength);
    }

    @Override
    public void onEvent(AbstractEvent event) {
        if (event instanceof Message.Send messageEvent) {
            String chatMessage = messageEvent.getMessage();
            if (beforeMessage.getBooleanValue()) {
                chatMessage = "[" + stringGenerator.generate(Math.round(stringLength.getNumericValue())) + "] " + messageEvent.getMessage();
            }
            if (afterMessage.getBooleanValue()) {
                chatMessage = messageEvent.getMessage() + " [" + stringGenerator.generate(Math.round(stringLength.getNumericValue())) + "]";
            }
            if (afterMessage.getBooleanValue() && beforeMessage.getBooleanValue()) {
                chatMessage = "[" + stringGenerator.generate(Math.round(stringLength.getNumericValue())) + "] " + messageEvent.getMessage() + " [" + stringGenerator.generate(Math.round(stringLength.getNumericValue())) + "]";
            }
            messageEvent.setMessage(chatMessage);
        }
    }

}
