package me.scyphers.xtraitemdrops;

import me.scyphers.xtraitemdrops.command.AdminCommand;
import me.scyphers.xtraitemdrops.file.FileManager;
import me.scyphers.xtraitemdrops.file.Messenger;
import me.scyphers.xtraitemdrops.file.Settings;
import me.scyphers.xtraitemdrops.ui.InventoryListener;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class XtraItemDrops extends JavaPlugin {

    private AsyncQueue<XtraItemDrops> asyncQueue;

    private FileManager fileManager;

    @Override
    public void onEnable() {
        this.asyncQueue = new AsyncQueue<>(this, 20);

        this.fileManager = new FileManager(this);
        fileManager.loadAll();

        this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);

        AdminCommand adminCommand = new AdminCommand(this);

    }

    public AsyncQueue<XtraItemDrops> getQueue() {
        return asyncQueue;
    }

    public Settings getSettings() {
        return fileManager.getSettingsFile();
    }

    public Messenger getMessenger() {
        return fileManager.getMessengerFile();
    }

    public void reload(CommandSender sender) {
        sender.sendMessage("reloading...");
        try {
            fileManager.reloadFiles();
            sender.sendMessage("successfully reloaded!");
        } catch (Exception e) {
            sender.sendMessage("An error occurred while reloading files!");
            this.getSLF4JLogger().error("An error occurred reloading files", e);
        }
    }
}
