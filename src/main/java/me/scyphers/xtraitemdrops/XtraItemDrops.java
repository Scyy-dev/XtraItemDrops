package me.scyphers.xtraitemdrops;

import org.bukkit.plugin.java.JavaPlugin;

public class XtraItemDrops extends JavaPlugin {

    private AsyncQueue<XtraItemDrops> asyncQueue;

    @Override
    public void onEnable() {
        this.asyncQueue = new AsyncQueue<>(this, 20);
    }

    public AsyncQueue<XtraItemDrops> getQueue() {
        return asyncQueue;
    }
}
