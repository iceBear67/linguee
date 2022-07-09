package io.ib67.bukkit.chat.theme;

import net.md_5.bungee.api.chat.BaseComponent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.0")
public interface TextFormatter {
    BaseComponent format(BaseComponent components, boolean italic,
                         boolean bold,
                         boolean quote, boolean placeholder,boolean link);
}
