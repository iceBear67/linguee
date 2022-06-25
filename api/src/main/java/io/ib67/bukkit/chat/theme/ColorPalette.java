package io.ib67.bukkit.chat.theme;

import net.md_5.bungee.api.ChatColor;

import static java.util.Objects.requireNonNull;

/**
 * Defines the colors for texts.
 *
 * @param primary   main color. also named default color
 * @param accent    secondary color.
 * @param reference used for links
 */
public record ColorPalette(
        ChatColor primary,
        ChatColor accent,
        ChatColor reference
) {
    public ColorPalette {
        requireNonNull(primary);
        requireNonNull(accent);
        requireNonNull(reference);
    }
}
