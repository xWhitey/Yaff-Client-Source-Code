package store.yaff.command.impl;

import org.lwjgl.input.Keyboard;
import store.yaff.Yaff;
import store.yaff.command.AbstractCommand;
import store.yaff.helper.Chat;
import store.yaff.macro.MacroController;

import java.util.Objects;

public class Macro extends AbstractCommand {
    public Macro(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            switch (args[1]) {
                case "add" -> {
                    if (Keyboard.getKeyName(Keyboard.getKeyIndex(args[2].toUpperCase())).equalsIgnoreCase("NONE")) {
                        Chat.addChatMessage("Key not found");
                        break;
                    }
                    String formattedCommand = Chat.parseMessage((byte) 3, args);
                    new MacroController.LocalMacro(Keyboard.getKeyIndex(args[2].toUpperCase()), formattedCommand).add();
                    Chat.addChatMessage(formattedCommand + " binded to: " + args[2].toUpperCase());
                }
                case "remove" -> {
                    try {
                        Objects.requireNonNull(Yaff.of.macroController.getMacrosByKey(Keyboard.getKeyIndex(args[2].toUpperCase()))).forEach(MacroController.LocalMacro::remove);
                    } catch (Exception e) {
                        Chat.addChatMessage("No command is binded to " + args[2] + " key");
                    }
                    Chat.addChatMessage("All commands binded to " + args[2] + " have been removed");
                }
                case "list" -> {
                    if (Objects.requireNonNull(Yaff.of.macroController.getMacros()).isEmpty()) {
                        Chat.addChatMessage("Macro list is empty");
                        return;
                    }
                    Objects.requireNonNull(Yaff.of.macroController.getMacros()).forEach(c -> {
                        Chat.addChatMessage("Command " + '"' + c.getCommand() + '"' + " binded to " + Keyboard.getKeyName(c.getKey()));
                    });
                }
                case "clear" -> {
                    Objects.requireNonNull(Yaff.of.macroController.getMacros()).clear();
                    Chat.addChatMessage("Macro list has been cleared");
                }
                default -> Chat.addChatMessage("Invalid action");
            }
        } catch (Exception ignored) {
            Chat.addChatMessage(getSyntax());
        }
    }

}
