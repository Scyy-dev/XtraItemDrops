package me.scyphers.xtraitemdrops.gui;

import me.scyphers.xtraitemdrops.ui.GUI;
import me.scyphers.xtraitemdrops.ui.ItemBuilder;
import me.scyphers.xtraitemdrops.ui.MenuGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HomeGUI extends MenuGUI {

    private static final String TITLE = "<dark_purple>XtraItemDrops</dark_purple>";

    public HomeGUI(@NotNull Plugin plugin, @NotNull Player player, UUID intendedViewer) {
        super(plugin, player, intendedViewer, TITLE, 54);
    }

    @Override
    public void draw() {
        super.draw();
        setItem(29, new ItemBuilder(Material.CREEPER_HEAD).name("<dark_purple>Entities</dark_purple>").build());
        setItem(33, new ItemBuilder(Material.GRASS_BLOCK).name("<dark_purple>Blocks</dark_purple>").build());
    }

    @Override
    public GUI<?> onClick(int slot, ClickType type) {
        return switch (slot) {
            case 29 -> new EntityDropSourceList(getPlugin(), getPlayer(), getViewer());
            case 33 -> new BlockDropSourceList(getPlugin(), getPlayer(), getViewer());
            default -> this;
        };
    }

    @Override
    public GUI<?> getPreviousGUI() {
        return this;
    }
}
