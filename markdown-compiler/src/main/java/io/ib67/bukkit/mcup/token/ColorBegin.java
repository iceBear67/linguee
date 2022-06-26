package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;
import net.md_5.bungee.api.ChatColor;

public class ColorBegin extends MDToken<ColorBegin.ColorData> {

    public ColorBegin(ColorData data) {
        super(data, TokenType.COLOR_BEGIN);
    }

    public record ColorData(
            ChatColor color
    ) {
    }
}
