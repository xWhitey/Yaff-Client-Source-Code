package store.yaff.command.impl;

import org.lwjgl.input.Keyboard;
import store.yaff.Yaff;
import store.yaff.command.AbstractCommand;
import store.yaff.feature.AbstractFeature;
import store.yaff.file.impl.Configuration;
import store.yaff.helper.Chat;

import java.util.Objects;

public class Bind extends AbstractCommand {
    public Bind(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            switch (args[1]) {
                case "add" -> {
                    AbstractFeature feature = Yaff.of.featureManager.getFeature(args[2]);
                    if (feature == null) {
                        Chat.addChatMessage("Feature not found");
                        return;
                    }
                    if (feature.getKey() != Keyboard.getKeyIndex(args[3].toUpperCase())) {
                        feature.setKey(Keyboard.getKeyIndex(args[3].toUpperCase()));
                        if (Keyboard.getKeyIndex(args[3].toUpperCase()) == Keyboard.KEY_NONE) {
                            Chat.addChatMessage("Bind deleted from " + feature.getName());
                        } else {
                            Chat.addChatMessage(feature.getName() + " binded to " + Keyboard.getKeyName(feature.getKey()));
                        }
                        Configuration.saveConfiguration();
                    } else {
                        Chat.addChatMessage(feature.getName() + " already binded to " + args[3].toUpperCase());
                    }
                }
                case "remove" -> {
                    AbstractFeature feature = Yaff.of.featureManager.getFeature(args[2]);
                    if (feature == null) {
                        Chat.addChatMessage("Feature not found");
                        return;
                    }
                    if (feature.getKey() != 0) {
                        feature.setKey(0);
                        Chat.addChatMessage("Bind deleted from " + feature.getName());
                        Configuration.saveConfiguration();
                    } else {
                        Chat.addChatMessage("Feature not binded to any key");
                    }
                }
                case "list" -> {
                    if (!Objects.requireNonNull(Yaff.of.featureManager.getFeatures()).stream().filter(f -> f.getKey() != 0).toList().isEmpty()) {
                        Objects.requireNonNull(Yaff.of.featureManager.getFeatures()).stream().filter(f -> f.getKey() != 0).forEach(f -> Chat.addChatMessage("Feature " + f.getName() + " binded to " + Keyboard.getKeyName(f.getKey())));
                    } else {
                        Chat.addChatMessage("Bind list is empty");
                    }
                }
                case "clear" -> {
                    Objects.requireNonNull(Yaff.of.featureManager.getFeatures()).clear();
                    Configuration.saveConfiguration();
                    Chat.addChatMessage("Bind list has been cleared");
                }
                default -> Chat.addChatMessage("Invalid action");
            }
        } catch (Exception e) {
            Chat.addChatMessage(getSyntax());
        }
    }

}
