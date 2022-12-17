package me.scyphers.xtraitemdrops.file;

import me.scyphers.xtraitemdrops.XtraItemDrops;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class PluginDataFile {

    private final Plugin plugin;
    private final File file;
    private final String filePath;

    private final boolean readOnly;

    public PluginDataFile(@NotNull Plugin plugin, @NotNull String filePath) {
        this(plugin, filePath, true);
    }

    public PluginDataFile(@NotNull Plugin plugin, @NotNull String filePath, boolean readOnly) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), filePath);
        this.filePath = filePath;
        this.readOnly = readOnly;
    }

    public void load() {
        if (!this.getFile().exists()) {
            this.getFile().getParentFile().mkdirs();
            this.getPlugin().saveResource(this.getFilePath(), false);
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(this.getFile());
        this.load(configuration);
    }

    public abstract void load(YamlConfiguration file);

    public void save() {
        if (readOnly) return;
        YamlConfiguration configuration = new YamlConfiguration();
        this.save(configuration);
        try {
            configuration.save(getFile());
        } catch (Exception e) {
            // TODO - better handle save errors
            // Most errors here are already caught by the loading process
        }

    }

    public abstract void save(YamlConfiguration file);

    public @NotNull Plugin getPlugin() {
        return plugin;
    }

    public @NotNull File getFile() {
        return file;
    }

    public @NotNull String getFilePath() {
        return filePath;
    }


}
