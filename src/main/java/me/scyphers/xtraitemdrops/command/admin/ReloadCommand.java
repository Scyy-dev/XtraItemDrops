package me.scyphers.xtraitemdrops.command.admin;

import me.scyphers.xtraitemdrops.XtraItemDrops;
import me.scyphers.xtraitemdrops.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ReloadCommand implements SubCommand {

    private final XtraItemDrops plugin;

    public ReloadCommand(XtraItemDrops plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        plugin.reload(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
