package io.ib67.bukkit.chat;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiFunction;

@FunctionalInterface
@ApiStatus.AvailableSince("0.1.0")
public interface PlaceHolder {
    String apply(CommandSender sender, String placeholder);

    default PlaceHolder and(PlaceHolder holder) {
        return (sender, placeholder) -> {
            var result = apply(sender, placeholder);
            if (result == null) {
                return holder.apply(sender, placeholder);
            }
            return result;
        };
    }
}
