package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken.Flag;
import io.ib67.bukkit.mcup.TokenType;

public class Italic extends Flag {
    public static final Italic BEGIN = new Italic(TokenType.ITALIC_BEGIN);
    public static final Italic END = new Italic(TokenType.ITALIC_END);

    protected Italic(TokenType type) {
        super(type);
    }
}
