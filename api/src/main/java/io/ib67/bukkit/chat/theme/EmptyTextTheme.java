package io.ib67.bukkit.chat.theme;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;

final class EmptyTextTheme implements TextTheme {
    @Override
    public ColorPalette getColorPalette() {
        return new ColorPalette(ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE);
    }

    @Override
    public TextFormatter getTextFormatter() {
        return new NoneTextFormatter();
    }

    private class NoneTextFormatter implements TextFormatter {
        @Override
        public BaseComponent format(BaseComponent components, boolean italic, boolean bold, boolean quote, boolean placeholder, boolean link) {
            return components;
        }
    }
}
