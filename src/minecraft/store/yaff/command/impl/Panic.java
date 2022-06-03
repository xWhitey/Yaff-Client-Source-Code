package store.yaff.command.impl;

import store.yaff.Yaff;
import store.yaff.command.AbstractCommand;
import store.yaff.helper.Chat;

import java.util.Objects;

public class Panic extends AbstractCommand {
    public Panic(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            Objects.requireNonNull(Yaff.of.featureManager.getFeatures(true)).forEach(f -> f.setState(false));
            Chat.addChatMessage("All features have been disabled");
        } catch (Exception e) {
            Chat.addChatMessage(getSyntax());
        }
    }

}
