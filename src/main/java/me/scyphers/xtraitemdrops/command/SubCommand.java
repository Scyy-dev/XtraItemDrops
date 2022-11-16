package me.scyphers.xtraitemdrops.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface SubCommand extends TabExecutor {

    @Override
    default boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return onCommand(sender, subCommandArgs(args));
    }

    boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args);

    @Override
    default @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return onTabComplete(sender, subCommandArgs(args));
    }

    List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args);

    static String[] subCommandArgs(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    default List<String> getOnlinePlayerNames(Plugin plugin) {
        return plugin.getServer().getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

}
