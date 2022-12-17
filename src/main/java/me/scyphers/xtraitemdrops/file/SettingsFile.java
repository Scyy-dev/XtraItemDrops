package me.scyphers.xtraitemdrops.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SettingsFile extends PluginDataFile implements Settings {

    public SettingsFile(@NotNull Plugin plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public void load(YamlConfiguration file) {

    }

    @Override
    public void save(YamlConfiguration file) {

    }
}
