package store.yaff.command.impl;

import store.yaff.command.AbstractCommand;
import store.yaff.helper.Chat;

public class Configuration extends AbstractCommand {
    public Configuration(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            switch (args[1]) {
                case "load" -> {
                    store.yaff.file.impl.Configuration.loadConfiguration();
                    Chat.addChatMessage("Configuration reloaded");
                }
                case "save" -> {
                    store.yaff.file.impl.Configuration.saveConfiguration();
                    Chat.addChatMessage("Configuration saved");
                }
                default -> Chat.addChatMessage("Invalid action");
            }
        } catch (Exception e) {
            Chat.addChatMessage(getSyntax());
        }
    }

}
