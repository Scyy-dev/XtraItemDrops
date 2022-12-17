package me.scyphers.xtraitemdrops.command;

import me.scyphers.xtraitemdrops.XtraItemDrops;
import me.scyphers.xtraitemdrops.command.admin.ReloadCommand;
import me.scyphers.xtraitemdrops.command.admin.UICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AdminCommand extends SubCommandMap<XtraItemDrops> {

    public AdminCommand(XtraItemDrops plugin) {
        super(plugin, "xtraitemdrops.commands.admin");
        addSubcommand("open", new UICommand(plugin));
        addSubcommand("reload", new ReloadCommand(plugin));

        PluginCommand command = Objects.requireNonNull(plugin.getCommand("drops"));
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public void sendNoPermission(CommandSender sender) {
        // TODO - messaging system
        sender.sendMessage("no permission");
    }

    @Override
    public void sendUnknownCommand(CommandSender sender) {
        // TODO - messaging system
        sender.sendMessage("unknown command");
    }

    @Override
    public boolean onNoArgCommand(CommandSender sender) {

        if (!(sender instanceof Player player)) {
            // TODO - messaging system
            sender.sendMessage("must be player");
            return true;
        }

        // execute the command, bypassing permission checks
        return getCommand("open").onCommand(sender, new String[] {"home", player.getName(), player.getName()});
    }
}
