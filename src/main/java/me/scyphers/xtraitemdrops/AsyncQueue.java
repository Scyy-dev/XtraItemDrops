package me.scyphers.xtraitemdrops;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AsyncQueue<T extends Plugin> {

    private final T plugin;

    private final BlockingQueue<Task<T, ?>> syncTasks;

    private final int taskID, taskCapacity;

    public AsyncQueue(T plugin, int taskCapacity) {
        this.plugin = plugin;
        this.taskCapacity = taskCapacity;
        this.syncTasks = new ArrayBlockingQueue<>(taskCapacity);
        this.taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::processTasks, 20, 1);
    }

    private void processTasks() {
        if (syncTasks.isEmpty()) return;
        Collection<Task<T, ?>> tasks = new ArrayList<>(taskCapacity);
        syncTasks.drainTo(tasks);
        tasks.forEach(task -> task.accept(plugin));
    }

    public <U> void addAsyncTask(Supplier<U> asyncTask, BiConsumer<T, U> syncTask) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            Task<T, U> task = new Task<>(asyncTask.get(), syncTask);
            this.addSyncTask(task);
        });
    }

    public void addPlayerTask(String playerName, BiConsumer<T, OfflinePlayer> syncTask) {
        this.addAsyncTask(() -> plugin.getServer().getOfflinePlayer(playerName), syncTask);
    }

    public void addPlayerTask(UUID playerUUID, BiConsumer<T, OfflinePlayer> syncTask) {
        this.addAsyncTask(() -> plugin.getServer().getOfflinePlayer(playerUUID), syncTask);
    }

    public synchronized void addSyncTask(Task<T, ?> task) {
        try {
            syncTasks.put(task);
        } catch (Exception ignored) {} // should never happen
    }

    public synchronized void addSyncTask(Consumer<T> task) {
        try {
            syncTasks.put(this.wrap(task));
        } catch (Exception ignored) {} // should never happen
    }

    public void endTasks() {
        plugin.getServer().getScheduler().cancelTask(taskID);
    }

    private Task<T, Void> wrap(Consumer<T> syncTask) {
        return new Task<>(null, (p, unused) -> syncTask.accept(p));
    }

    private static record Task<T extends Plugin, U>(U object, BiConsumer<T, U> task) implements Consumer<T> {
        @Override
        public void accept(T plugin) {
            task.accept(plugin, object);
        }
    }

}
