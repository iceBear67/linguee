package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;

public class Literal extends MDToken<String> {
    public Literal(String data) {
        super(data, TokenType.LITERAL);
    }
}
