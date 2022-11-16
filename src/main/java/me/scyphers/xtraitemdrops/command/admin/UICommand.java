package me.scyphers.xtraitemdrops.command.admin;

import me.scyphers.xtraitemdrops.XtraItemDrops;
import me.scyphers.xtraitemdrops.command.SubCommand;
import me.scyphers.xtraitemdrops.gui.BlockDropSourceList;
import me.scyphers.xtraitemdrops.gui.EntityDropSourceList;
import me.scyphers.xtraitemdrops.gui.HomeGUI;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public class UICommand implements SubCommand {

    private final XtraItemDrops plugin;

    private final Map<String, BiConsumer<Player, UUID>> guiConsumers;

    public UICommand(XtraItemDrops plugin) {
        this.plugin = plugin;
        this.guiConsumers = this.initConsumers();
    }

    private Map<String, BiConsumer<Player, UUID>> initConsumers() {
        return Map.of(
                "home", ((player, uuid) -> new HomeGUI(plugin, player, uuid).open()),
                "blocks", ((player, uuid) -> new BlockDropSourceList(plugin, player, uuid).open()),
                "entities", ((player, uuid) -> new EntityDropSourceList(plugin, player, uuid).open())
        );
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {

        String gui = args[0].toLowerCase(Locale.ROOT);
        if (!guiConsumers.containsKey(gui)) {
            // TODO - send a gui not found message
            return true;
        }

        String playerName = args[1];
        Player player = plugin.getServer().getPlayer(playerName);
        if (player == null) {
            // TODO - send a player not found message
            return true;
        }

        String viewerName = args[2];
        plugin.getQueue().addPlayerTask(viewerName, (xtraItemDrops, viewer) -> tryOpenGUI(gui, player, viewer));
        return true;
    }

    private void tryOpenGUI(String gui, Player player, OfflinePlayer viewer) {

        if (!viewer.hasPlayedBefore() && !viewer.isOnline()) {
            // TODO - send a player not found message
            return;
        }

        // Sanity check
        if (!guiConsumers.containsKey(gui)) return;
        guiConsumers.get(gui).accept(player, viewer.getUniqueId());
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return switch (args.length) {
            case 2, 3 -> getOnlinePlayerNames(plugin);
            default -> Collections.emptyList();
        };
    }
}
