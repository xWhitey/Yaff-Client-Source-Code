package store.yaff.command;

import net.minecraft.client.Minecraft;

public abstract class AbstractCommand {
    protected static final String commandPrefix = ">";
    protected final String name, description, syntax;
    protected final String[] aliases;
    protected final Minecraft mc = Minecraft.getMinecraft();

    protected AbstractCommand(String name, String description, String syntax, String... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = aliases;
    }

    public static String getCommandPrefix() {
        return commandPrefix;
    }

    public abstract void onCommand(String... args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return "Syntax: " + commandPrefix + name.toLowerCase() + " " + syntax;
    }

    public String[] getAliases() {
        return aliases;
    }

}
