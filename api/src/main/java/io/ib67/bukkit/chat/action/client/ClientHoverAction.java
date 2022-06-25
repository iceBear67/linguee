package io.ib67.bukkit.chat.action.client;

import io.ib67.bukkit.chat.Text;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Things to be shown in the client.
 *
 * @param <T> the type of the message
 */
@ApiStatus.AvailableSince("0.1.0")
public sealed abstract class ClientHoverAction<T> {
    protected final T value;

    protected ClientHoverAction(T value) {
        this.value = value;
    }

    /**
     * Action that shows a text to player.
     */
    @ApiStatus.AvailableSince("0.1.0")
    public static final class ShowText extends ClientHoverAction<Text> {
        public ShowText(Text text) {
            super(text);
        }

        public ShowText(String text) {
            super(Text.of(text));
        }
    }

    /**
     * Action that shows a ItemStack to player.
     */
    @ApiStatus.AvailableSince("0.1.0")
    public static final class ShowItem extends ClientHoverAction<ItemStack> {
        public ShowItem(ItemStack value) {
            super(value);
        }
    }

    /**
     * Action that shows an Entity to player.
     */
    @ApiStatus.AvailableSince("0.1.0")
    public static final class ShowEntity extends ClientHoverAction<Entity> {
        public ShowEntity(Entity value) {
            super(value);
        }
    }
}
