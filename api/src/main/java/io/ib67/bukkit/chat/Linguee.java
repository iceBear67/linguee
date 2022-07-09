package io.ib67.bukkit.chat;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.ib67.bukkit.chat.intenal.fakeplugin.LingueePlugin;
import io.ib67.bukkit.chat.intenal.handler.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static org.inlambda.kiwi.Kiwi.byLazy;

public final class Linguee {
    private static final Logger lingueeLogger = Logger.getLogger("Linguee");

    private static final Linguee instance = new Linguee();

    public static Linguee getInstance() {
        return instance;
    }

    static {
        if (Linguee.class.getPackageName().equals("io.ib67.bukkit.chat.internal")) {
            lingueeLogger.warning("Linguee is not relocated properly, undefined behaviours may occur.");
        }
    }
    // --------------------------------------------------------------------------------------------------------------------
    public Linguee() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), getPlugin());
    }
    /**
     * The JavaPlugin instance to be used for registration of listeners and tasks.
     */
    private Supplier<Plugin> PLUGIN = byLazy(Linguee::createFakePlugin);

    private static Plugin createFakePlugin() {
        if (Bukkit.getPluginManager().getPlugin("Linguee-API") != null) {
            return Bukkit.getPluginManager().getPlugin("Linguee-API");
        }
        var plugin = new LingueePlugin(lingueeLogger);
        plugin.getLogger().info("Using fake plugin for Linguee, undefined behavior may occur.");
        plugin.getLogger().info("Please set a plugin instance for Linguee by calling `Linguee.setPlugin`");
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        PLUGIN = () -> plugin;
    }

    public Plugin getPlugin() {
        return PLUGIN.get();
    }

    private final Cache<String, ClickAction> clickActions = CacheBuilder.newBuilder()
            .expireAfterWrite(Duration.ofMinutes(2))
            .build();

    @ApiStatus.Internal
    public void registerCallback(String key, ClickAction action) {
        clickActions.put(key, action);
    }

    @ApiStatus.Internal
    @Nullable
    public ClickAction getCallbackInfo(String key) {
        return clickActions.getIfPresent(key);
    }

    @ApiStatus.Internal
    public void invalidateCallback(String key) {
        clickActions.invalidate(key);
    }

}
