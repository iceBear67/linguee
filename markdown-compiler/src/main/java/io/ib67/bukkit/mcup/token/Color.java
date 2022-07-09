package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;
import net.md_5.bungee.api.ChatColor;

public class Color extends MDToken.Flag {
    private final ChatColor color;
    public Color(ChatColor color) {
        super(TokenType.COLOR_BEGIN);
        this.color = color;
    }

    public ChatColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Color -> "+color;
    }
}
