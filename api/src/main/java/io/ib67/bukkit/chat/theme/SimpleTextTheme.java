package io.ib67.bukkit.chat.theme;

public record SimpleTextTheme(ColorPalette palette) implements TextTheme{
    @Override
    public ColorPalette getColorPalette() {
        return palette;
    }

    @Override
    public TextFormatter getTextFormatter() {
        return new SimpleTextFormatter(palette);
    }
}
