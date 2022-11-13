package me.scyphers.xtraitemdrops.ui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class MenuGUI extends InventoryGUI {

    private final int backSlot, exitSlot;

    public MenuGUI(@NotNull Plugin plugin, @NotNull Player player, UUID intendedViewer, @NotNull String name, int size) {
        super(plugin, player, intendedViewer, name, size);

        this.backSlot = size - 9;
        this.exitSlot = size - 1;

        this.fill(BACKGROUND);
        setItem(backSlot, new ItemBuilder(Material.BARRIER).name("<red>Back</red>").build());
        setItem(exitSlot, new ItemBuilder(Material.IRON_DOOR).name("<red>Exit</red>").build());
    }

    @Override
    public @NotNull GUI<?> handleInteraction(InventoryClickEvent event) {
        event.setCancelled(true);

        int slot = event.getRawSlot();

        if (slot == backSlot) return getPreviousGUI();
        if (slot == exitSlot) {
            this.setShouldClose(true);
            return new StaticGUI(this);
        }

        return onClick(slot, event.getClick());

    }

    public abstract GUI<?> onClick(int slot, ClickType type);

    public abstract GUI<?> getPreviousGUI();

    @Override
    public boolean allowPlayerInventoryEdits() {
        return false;
    }
}
