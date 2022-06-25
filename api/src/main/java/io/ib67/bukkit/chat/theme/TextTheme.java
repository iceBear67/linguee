package io.ib67.bukkit.chat.theme;

import org.jetbrains.annotations.ApiStatus;

/**
 * TextTheme determines how does your texts will look and feel like.
 */
@ApiStatus.AvailableSince("0.1.0")
public interface TextTheme {
    ColorPalette getColorPalette();

    TextFormatter getTextFormatter();
}
