package io.ib67.bukkit.chat.intenal.fakeplugin;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class LingueePlugin extends PluginBase {
    private final Logger logger;
    public LingueePlugin(Logger lingueeLogger) {
        logger=lingueeLogger;
    }

    @NotNull
    @Override
    public File getDataFolder() {
        return null;
    }

    @NotNull
    @Override
    public PluginDescriptionFile getDescription() {
        return new PluginDescriptionFile("Linguee-API", "???", null);
    }

    @NotNull
    @Override
    public FileConfiguration getConfig() {
        return null;
    }

    @Nullable
    @Override
    public InputStream getResource(@NotNull String filename) {
        return null;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public void saveDefaultConfig() {

    }

    @Override
    public void saveResource(@NotNull String resourcePath, boolean replace) {

    }

    @Override
    public void reloadConfig() {

    }

    @NotNull
    @Override
    public PluginLoader getPluginLoader() {
        return new JavaPluginLoader(getServer());
    }

    @NotNull
    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public boolean isNaggable() {
        return false;
    }

    @Override
    public void setNaggable(boolean canNag) {

    }

    @Nullable
    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id) {
        return null;
    }

    @Nullable
    @Override
    public BiomeProvider getDefaultBiomeProvider(@NotNull String worldName, @Nullable String id) {
        return null;
    }

    @NotNull
    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
