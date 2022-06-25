package io.ib67.bukkit.chat.theme;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.0")
public abstract class AbstractTextFormatter implements TextFormatter {
    protected final ColorPalette colorPalette;

    public AbstractTextFormatter(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }
}
