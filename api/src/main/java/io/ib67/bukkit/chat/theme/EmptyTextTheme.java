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
        public BaseComponent formatItalic(BaseComponent components) {
            return components;
        }

        @Override
        public BaseComponent formatActive(BaseComponent components) {
            return components;
        }

        @Override
        public BaseComponent formatBold(BaseComponent components) {
            return components;
        }

        @Override
        public BaseComponent formatRegular(BaseComponent components) {
            return components;
        }

        @Override
        public BaseComponent formatAccent(BaseComponent components) {
            return components;
        }

        @Override
        public BaseComponent formatLink(BaseComponent component) {
            return component;
        }
    }
}
