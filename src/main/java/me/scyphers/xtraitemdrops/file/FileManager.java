package me.scyphers.xtraitemdrops.file;

import me.scyphers.xtraitemdrops.XtraItemDrops;

public class FileManager {

    private final XtraItemDrops plugin;

    private final MessengerFile messengerFile;
    private final SettingsFile settingsFile;

    private final int saveTaskID;

    public FileManager(XtraItemDrops plugin) {
        this.plugin = plugin;

        this.messengerFile = new MessengerFile(plugin);
        this.settingsFile = new SettingsFile(plugin);

        this.saveTaskID = this.scheduleSaveTask(72000);

    }

    public MessengerFile getMessengerFile() {
        return messengerFile;
    }
    public SettingsFile getSettingsFile() {
        return settingsFile;
    }

    public void loadAll() {
        messengerFile.load();
        settingsFile.load();
    }

    public void reloadConfigs() {
        messengerFile.load();
        settingsFile.load();
    }

    public void saveAll() {
        messengerFile.save();
        settingsFile.save();
    }

    public XtraItemDrops getPlugin() {
        return plugin;
    }

    public int scheduleSaveTask(int saveTicks) {
        return plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::saveAll, saveTicks, saveTicks);
    }

    public void cancelSaveTask() {
        plugin.getServer().getScheduler().cancelTask(saveTaskID);
    }
}
