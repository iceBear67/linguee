package io.ib67.bukkit.mcup.token;

import io.ib67.bukkit.mcup.MDToken;
import org.inlambda.kiwi.Stack;

public record LinkData(
        Stack<MDToken<?>> display,
        Stack<MDToken<?>> url
) {
}
