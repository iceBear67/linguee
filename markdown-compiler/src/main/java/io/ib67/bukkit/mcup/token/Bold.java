package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;

public class Bold extends MDToken.Flag {
    public static final Bold BEGIN = new Bold(TokenType.BOLD_BEGIN);
    public static final Bold END = new Bold(TokenType.BOLD_END);

    protected Bold(TokenType type) {
        super(type);
    }
}
