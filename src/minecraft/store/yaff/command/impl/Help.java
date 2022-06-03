package store.yaff.command.impl;

import store.yaff.Yaff;
import store.yaff.command.AbstractCommand;
import store.yaff.helper.Chat;

import java.util.Arrays;
import java.util.Objects;

public class Help extends AbstractCommand {
    public Help(String name, String description, String syntax, String... aliases) {
        super(name, description, syntax, aliases);
    }

    @Override
    public void onCommand(String... args) {
        try {
            if (args.length > 1) {
                AbstractCommand command = Yaff.of.commandManager.getCommand(args[1]);
                if (command == null) {
                    Chat.addChatMessage("Command not found");
                    return;
                }
                Chat.addChatMessage(command.getName() + " - " + command.getDescription() + "; Aliases: " + Arrays.toString(command.getAliases()) + "; Syntax: " + commandPrefix + command.getName().toLowerCase() + " " + command.getSyntax());
                return;
            }
            Objects.requireNonNull(Yaff.of.commandManager.getCommands()).forEach(c -> Chat.addChatMessage(c.getName() + " - " + c.getDescription() + "; Aliases: " + Arrays.toString(c.getAliases()) + "; Syntax: " + commandPrefix + c.getName().toLowerCase() + " " + c.getSyntax()));
        } catch (Exception e) {
            Chat.addChatMessage(getSyntax());
        }
    }

}
