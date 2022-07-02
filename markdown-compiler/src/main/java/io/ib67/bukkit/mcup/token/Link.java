package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.TokenType;

public class Link extends MDToken<LinkData> {
    public Link(LinkData data) {
        super(data, TokenType.LINK);
    }
}
