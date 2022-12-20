package me.scyphers.xtraitemdrops.command;

import me.scyphers.xtraitemdrops.XtraItemDrops;
import me.scyphers.xtraitemdrops.command.admin.HelpCommand;
import me.scyphers.xtraitemdrops.command.admin.ReloadCommand;
import me.scyphers.xtraitemdrops.command.admin.UICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AdminCommand extends SubCommandMap {

    private final XtraItemDrops plugin;

    public AdminCommand(XtraItemDrops plugin) {
        super("xtraitemdrops.commands.admin");
        this.plugin = plugin;

        addSubcommand("open", new UICommand(plugin));
        addSubcommand("help", new HelpCommand(this, plugin));
        addSubcommand("reload", new ReloadCommand(plugin));

        PluginCommand command = Objects.requireNonNull(plugin.getCommand("drops"));
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public void sendNoPermission(CommandSender sender) {
        plugin.getMessenger().chat(sender, "errorMessages.noPermission");
    }

    @Override
    public void sendUnknownCommand(CommandSender sender) {
        plugin.getMessenger().chat(sender, "errorMessages.unknownCommand");
    }

    @Override
    public boolean onNoArgCommand(CommandSender sender) {

        if (!(sender instanceof Player player)) {
            plugin.getMessenger().chat(sender, "errorMessages.mustBePlayer");
            return true;
        }

        // execute the command, bypassing permission checks
        return getCommand("open").onCommand(sender, new String[] {"home", player.getName(), player.getName()});
    }
}
