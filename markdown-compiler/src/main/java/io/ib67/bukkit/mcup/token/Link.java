package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;

import java.util.Stack;

public class Link extends MDToken<Link.LinkData> {
    public Link(LinkData data, TokenType type) {
        super(data, type);
    }

    public record LinkData(String display, String url, Stack<TokenType> context) {
    }
}
