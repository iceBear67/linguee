package io.ib67.bukkit.chat.theme;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class SimpleTextFormatter extends AbstractTextFormatter {
    public SimpleTextFormatter(ColorPalette colorPalette) {
        super(colorPalette);
    }

    @Override
    public BaseComponent formatItalic(BaseComponent components) {
        return formatAccent(components);
    }

    @Override
    public BaseComponent formatActive(BaseComponent components) {
        components.setColor(colorPalette.reference());
        return components;
    }

    @Override
    public BaseComponent formatBold(BaseComponent components) {
        components.setBold(true);
        components.setColor(colorPalette.accent());
        return components;
    }

    @Override
    public BaseComponent formatRegular(BaseComponent components) {
        components.setColor(colorPalette.primary());
        return components;
    }

    @Override
    public BaseComponent formatAccent(BaseComponent components) {
        components.setColor(colorPalette.accent());
        components.setItalic(true);
        return components;
    }

    @Override
    public BaseComponent formatLink(BaseComponent component) {
        component.setUnderlined(true);
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(TextComponent.fromLegacyText("Click Me!", ChatColor.WHITE))));
        component.setColor(colorPalette.reference());
        return component;
    }
}
