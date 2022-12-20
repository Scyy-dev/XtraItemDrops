package me.scyphers.xtraitemdrops.command.admin;

import me.scyphers.xtraitemdrops.XtraItemDrops;
import me.scyphers.xtraitemdrops.command.SubCommand;
import me.scyphers.xtraitemdrops.command.SubCommandMap;
import me.scyphers.xtraitemdrops.file.Messenger;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class HelpCommand implements SubCommand {

    private final SubCommandMap commandMap;
    private final Messenger messenger;

    public HelpCommand(SubCommandMap commandMap, XtraItemDrops plugin) {
        this.commandMap = commandMap;
        this.messenger = plugin.getMessenger();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {

        if (args.length < 1) {
            messenger.chat(sender, "errorMessages.invalidCommandLength");
            return true;
        }

        String command = args[0].toLowerCase(Locale.ROOT);

        String commandPermission = commandMap.getPermission() + "." + command;
        if (!sender.hasPermission(commandPermission)) {
            messenger.chat(sender, "errorMessages.noPermission");
            return true;
        }

        if (!commandMap.hasCommand(command)) {
            messenger.chat(sender, "errorMessages.unknownCommand", "%command%", command);
            return true;
        }

        messenger.chat(sender, "help.commands." + command);
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
