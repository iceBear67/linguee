package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;

public class Color extends MDToken.Flag {
    public static final Color BEGIN = new Color(TokenType.COLOR_BEGIN);

    protected Color(TokenType type) {
        super(type);
    }
}
