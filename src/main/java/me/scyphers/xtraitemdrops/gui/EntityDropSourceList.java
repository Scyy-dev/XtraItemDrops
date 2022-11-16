package me.scyphers.xtraitemdrops.gui;

import me.scyphers.xtraitemdrops.ui.GUI;
import me.scyphers.xtraitemdrops.ui.PagedListGUI;
import me.scyphers.xtraitemdrops.util.EntityMaterialMap;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class EntityDropSourceList extends PagedListGUI<EntityType> {

    private static final String TITLE = "<dark_purple>Entities</dark_purple>";

    public EntityDropSourceList(@NotNull Plugin plugin, @NotNull Player player, UUID viewer) {
        super(plugin, player, viewer, TITLE, 54, 4, 7, BACKGROUND, 47, 51);
    }

    @Override
    public GUI<?> getPreviousGUI() {
        return new HomeGUI(getPlugin(), getPlayer(), getViewer());
    }

    @Override
    public @NotNull List<EntityType> getList() {
        // TODO - get current entity list
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack display(EntityType entityType) {
        // TODO - apply lore to item
        return new ItemStack(EntityMaterialMap.fromEntity(entityType));
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
    public @NotNull GUI<?> handleItemInteraction(int slot, ClickType type, EntityType item) {
        // TODO - handle an item click
        return this;
    }
}
