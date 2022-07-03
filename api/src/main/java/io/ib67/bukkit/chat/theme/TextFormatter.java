package io.ib67.bukkit.chat.theme;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.0")
public interface TextFormatter {
    BaseComponent formatItalic(BaseComponent components);

    BaseComponent formatActive(BaseComponent components);

    BaseComponent formatBold(BaseComponent components);

    BaseComponent formatRegular(BaseComponent components);

    BaseComponent formatAccent(BaseComponent components);

    BaseComponent formatLink(BaseComponent component);
}
