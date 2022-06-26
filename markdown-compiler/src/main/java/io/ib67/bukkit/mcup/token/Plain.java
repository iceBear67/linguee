package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;

public class Plain extends MDToken<String> {
    public Plain(String data) {
        super(data, TokenType.PLAIN);
    }

    protected Plain(String data, TokenType type) {
        super(data, type);
    }
}
