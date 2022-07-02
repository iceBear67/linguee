package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;

import java.util.Stack;

public record LinkData(
        Stack<MDToken<?>> display,
        String url
) {
}
