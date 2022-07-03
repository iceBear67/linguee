package io.ib67.bukkit.chat.theme;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.jetbrains.annotations.ApiStatus;

/**
 * Presets.
 */
@ApiStatus.AvailableSince("0.1.0")
public class TextThemes {
    private TextThemes(){

    }

    public static final TextTheme worldguard = new SimpleTextTheme(new ColorPalette(
            ChatColor.GRAY,
            ChatColor.RED,
            ChatColor.YELLOW
    ));

    public static final TextTheme ocean = new SimpleTextTheme(new ColorPalette(
            ChatColor.AQUA,
            ChatColor.WHITE,
            ChatColor.YELLOW
    ));

    public static final TextTheme essentials = new SimpleTextTheme(new ColorPalette(
            ChatColor.GOLD,
            ChatColor.RED,
            ChatColor.DARK_PURPLE
    ));

    public static TextTheme DEFAULT = ocean;

}
