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
    public BaseComponent format(BaseComponent components, boolean italic, boolean bold, boolean quote, boolean placeholder, boolean link) {
        if(italic){
            components.setItalic(true);
        }
        if(bold){
            components.setBold(true);
        }
        if(quote){
            components.setColor(colorPalette.accent());
        }
        if(link){
            components.setUnderlined(true);
            components.setColor(colorPalette.reference());
            components.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("Click Me!")));
        }
        return components;
    }
}
