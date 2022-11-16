package me.scyphers.xtraitemdrops;

import me.scyphers.xtraitemdrops.command.AdminCommand;
import me.scyphers.xtraitemdrops.ui.InventoryListener;
import org.bukkit.plugin.java.JavaPlugin;

public class XtraItemDrops extends JavaPlugin {

    private AsyncQueue<XtraItemDrops> asyncQueue;

    @Override
    public void onEnable() {
        this.asyncQueue = new AsyncQueue<>(this, 20);

        this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);

        AdminCommand adminCommand = new AdminCommand(this);

    }

    public AsyncQueue<XtraItemDrops> getQueue() {
        return asyncQueue;
    }
}
