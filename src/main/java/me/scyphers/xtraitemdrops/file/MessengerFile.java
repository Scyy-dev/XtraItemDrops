package me.scyphers.xtraitemdrops.file;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;
import org.bukkit.Nameable;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessengerFile extends PluginDataFile implements Messenger {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private static final String NO_PREFIX = "[NO_PREFIX]";

    private Map<String, String> messages;

    private String prefix;

    public MessengerFile(Plugin plugin) {
        super(plugin, "messages.yml");
    }

    @Override
    public void load(YamlConfiguration file) {
        this.messages = new HashMap<>();

        // Loop through every key
        for (String key : file.getKeys(true)) {
            if (key.equalsIgnoreCase("prefix")) continue;

            // Check if message is a single line message
            String message = file.getString(key, "");
            if (!message.equalsIgnoreCase("")) {
                messages.put(key, message);
                continue;
            }

            // Something that isn't a message found in config - log it to console
            this.getPlugin().getLogger().info("Invalid format for message found at " + key + " in messages.yml");

        }

        this.prefix = file.getString("prefix", "[COULD_NOT_LOAD_PREFIX] ");

    }

    @Override
    public void save(YamlConfiguration file) {

    }

    //  === UTILITY ===

    public static String replace(String message, String... replacements) {

        if (replacements == null || replacements.length == 0) return message;

        if (replacements.length % 2 != 0) throw new IllegalArgumentException("Not all placeholders have a corresponding replacement");

        for (int i = 0; i < replacements.length; i += 2) {
            String placeholder = replacements[i];
            String replacement = replacements[i + 1];
            message = message.replaceAll(placeholder, replacement);
        }

        return message;

    }

    private boolean isEmpty(Component component) {
        return Component.EQUALS.test(component, Component.empty());
    }

    // === MESSAGES ===

    @Override
    public Component get(@NotNull String key, @NotNull String... replacements) {

        String rawMessage = messages.getOrDefault(key, NO_PREFIX + key);
        if (rawMessage.equals("")) return Component.empty();

        String messagePrefix = "";
        if (!rawMessage.startsWith(NO_PREFIX)) {
            messagePrefix = prefix;
        } else {
            rawMessage = rawMessage.substring(NO_PREFIX.length());
        }

        rawMessage = messagePrefix + rawMessage;
        rawMessage = replace(rawMessage, replacements);
        return miniMessage.deserialize(rawMessage);

    }

    @Override
    public boolean has(String key) {
        return messages.containsKey(key);
    }

    @Override
    public Set<String> getKeys() {
        return Collections.unmodifiableSet(messages.keySet());
    }

    @Override
    public void chat(@NotNull Audience audience, @NotNull String key, @NotNull String @NotNull ... replacements) {
        Component message = this.get(key, replacements);
        if (isEmpty(message)) return;
        audience.sendMessage(message);
    }

    @Override
    public void actionBar(@NotNull Audience audience, @NotNull String key, String... replacements) {
        Component message = this.get(key, replacements);
        if (isEmpty(message)) return;
        audience.sendActionBar(message);
    }

    @Override
    public void bossBar(@NotNull Audience audience, @NotNull String key, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull String... replacements) {
        Component message = this.get(key, replacements);
        if (isEmpty(message)) return;
        BossBar bossBar = BossBar.bossBar(message, progress, color, overlay);
        audience.showBossBar(bossBar);
    }

    @Override
    public void title(@NotNull Audience audience, @NotNull String titlePath, @NotNull String subtitlePath, @NotNull Duration fadeIn, @NotNull Duration stay, @NotNull Duration fadeOut, @NotNull String... replacements) {
        Component title_c = this.get(titlePath, replacements);
        Component subtitle = this.get(subtitlePath, replacements);
        Times times = Times.times(fadeIn, stay, fadeOut);
        if (isEmpty(title_c) && isEmpty(subtitle)) return;

        Title title = Title.title(title_c, subtitle, times);
        audience.showTitle(title);
    }

    // === OTHER COMPONENT USE ===

    @Override
    public void name(@NotNull Nameable nameable, @NotNull String key, @NotNull String @NotNull ... replacements) {
        Component name = this.get(key, replacements);
        if (isEmpty(name)) return;
        nameable.customName(name);
    }
}
