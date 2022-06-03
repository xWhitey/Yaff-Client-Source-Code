package store.yaff.command;

import store.yaff.command.impl.*;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandManager {
    public final CopyOnWriteArrayList<AbstractCommand> commands = new CopyOnWriteArrayList<>();

    public CommandManager() {
        commands.add(new Macro("Macro", "None", "<(?) action> <key> <command...>", "m", "macro"));
        commands.add(new Bind("Bind", "None", "<(?) action> <feature name> <key>", "b", "bind"));
        commands.add(new Clip("Clip", "None", "<(?) clip type> [blocks]", "clip", "vclip", "hclip"));
        commands.add(new Friend("Friend", "None", "<(?) action> <entity name>", "f", "friend"));
        commands.add(new Near("Near", "None", "<(?) sorting> [reversed?]", "n", "near"));
        commands.add(new Configuration("Configuration", "None", "<(?) action>", "c", "conf", "cfg", "config", "configuration"));
        commands.add(new Configuration("Panic", "None", "", "panic"));
        commands.add(new Help("Help", "None", "[feature name]", "h", "help"));
    }

    @Nullable
    public CopyOnWriteArrayList<AbstractCommand> getCommands() {
        try {
            return commands;
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public AbstractCommand getCommand(String commandName) {
        try {
            return commands.stream().filter(c -> Arrays.stream(c.getAliases()).toList().contains(commandName)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public AbstractCommand getCommand(Class<? extends AbstractCommand> commandClass) {
        try {
            return commands.stream().filter(c -> c.getClass().equals(commandClass)).toList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

}
