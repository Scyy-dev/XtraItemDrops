package me.scyphers.xtraitemdrops.gui;

import me.scyphers.xtraitemdrops.ui.GUI;
import me.scyphers.xtraitemdrops.ui.PagedListGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BlockDropSourceList extends PagedListGUI<Material> {

    private static final String TITLE = "<dark_purple>Blocks</dark_purple>";

    public BlockDropSourceList(@NotNull Plugin plugin, @NotNull Player player, UUID viewer) {
        super(plugin, player, viewer, TITLE, 54, 4, 7, BACKGROUND, 47, 51);
    }

    @Override
    public GUI<?> getPreviousGUI() {
        return new HomeGUI(getPlugin(), getPlayer(), getViewer());
    }

    @Override
    public @NotNull List<Material> getList() {
        // TODO - get current block list
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack display(Material material) {
        // TODO - apply lore to item
        return new ItemStack(material);
    }

    @Override
    public @Nullable ItemStack displayBlank() {
        return null;
    }

    @Override
    public boolean allowsInfinitePages() {
        return false;
    }

    @Override
    public @NotNull GUI<?> handleGenericInteraction(int slot, ClickType type) {
        return this;
    }

    @Override
    public @NotNull GUI<?> handleItemInteraction(int slot, ClickType type, Material item) {
        // TODO - handle an item click
        return this;
    }
}
