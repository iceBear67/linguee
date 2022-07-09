package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;

public class Placeholder extends MDToken.Flag {
    public static final Placeholder BEGN = new Placeholder(TokenType.PLACEHOLDER_BEGIN);
    public static final Placeholder END = new Placeholder(TokenType.PLACEHOLDER_END);
    protected Placeholder(TokenType type) {
        super(type);
    }
}
