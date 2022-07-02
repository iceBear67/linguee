package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken.Flag;
import io.ib67.bukkit.mcup.TokenType;

public class Quote extends Flag {
    public static final Quote BEGIN = new Quote(TokenType.QUOTE_BEGIN);
    public static final Quote END = new Quote(TokenType.QUOTE_END);

    protected Quote(TokenType type) {
        super(type);
    }
}
