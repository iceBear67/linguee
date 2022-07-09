package io.ib67.bukkit.chat;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

/**
 * Defines what happens when a player clicks on a text.
 */
@ApiStatus.AvailableSince("0.1.0")
@FunctionalInterface
public interface ClickAction {
    /**
     * Invoked when a <strong>audience</strong> trys to click your text.
     *
     * @param sender the player who clicked on your text.
     * @return should we invalidate the key to prevent this action from being called again
     */
    boolean onClick(Player sender);
}
