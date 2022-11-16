package me.scyphers.xtraitemdrops.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SubCommandMap<T extends Plugin> implements TabExecutor {

    private final Map<String, SubCommand> commands = new HashMap<>();

    private final T plugin;
    private final String permission;

    public SubCommandMap(T plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission(permission)) {
            sendNoPermission(sender);
            return true;
        }

        if (args.length == 0) return this.onNoArgCommand(sender);

        String subCommandName = args[0].toLowerCase(Locale.ROOT);
        if (!commands.containsKey(subCommandName)) {
            sendUnknownCommand(sender);
            return true;
        }

        if (!sender.hasPermission(permission + "." + subCommandName)) {
            sendNoPermission(sender);
            return true;
        }

        SubCommand subcommand = commands.getOrDefault(subCommandName, EmptyExecutor.INSTANCE);
        return subcommand.onCommand(sender, SubCommand.subCommandArgs(args));
    }

    public abstract void sendNoPermission(CommandSender sender);

    public abstract void sendUnknownCommand(CommandSender sender);

    public abstract boolean onNoArgCommand(CommandSender sender);

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(permission)) return Collections.emptyList();
        if (args.length == 0) return commands.keySet().stream()
                .filter(s -> sender.hasPermission(permission + "." + s))
                .collect(Collectors.toList());

        List<String> producedArgs = commands.getOrDefault(args[0].toLowerCase(Locale.ROOT), EmptyExecutor.INSTANCE).onTabComplete(sender, args);
        if (producedArgs == null) return Collections.emptyList();
        String currentArg = args[args.length - 1];
        return producedArgs.stream().filter(s -> s.contains(currentArg)).collect(Collectors.toList());
    }

    public void addSubcommand(String commandName, SubCommand subcommand) {
        String formattedName = commandName.toLowerCase(Locale.ROOT);
        if (commands.containsKey(formattedName)) throw new IllegalArgumentException("Subcommand already exists with name: " + formattedName);
        commands.put(formattedName, subcommand);
    }

    public T getPlugin() {
        return plugin;
    }

    public SubCommand getCommand(String command) {
        return commands.get(command);
    }

    private static final class EmptyExecutor implements SubCommand {

        public static EmptyExecutor INSTANCE = new EmptyExecutor();

        private EmptyExecutor() {}

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
            return false;
        }

        @Override
        public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
            return Collections.emptyList();
        }
    }

}
