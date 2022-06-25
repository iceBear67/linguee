package io.ib67.bukkit.chat.action;

import io.ib67.bukkit.chat.action.client.ClientClickAction;
import io.ib67.bukkit.chat.action.client.ClientHoverAction;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.net.URI;

/**
 * Static Factories to create {@link ClientClickAction} and {@link ClientHoverAction}
 */
@ApiStatus.AvailableSince("0.1.0")
public final class TextAction {
    private TextAction() {
    }

    public static ClientClickAction.OpenLink openLinkClick(URI uri) {
        return new ClientClickAction.OpenLink(uri.toString());
    }

    public static ClientClickAction.ExecuteCommand executeCommandClick(String command) {
        return new ClientClickAction.ExecuteCommand(command);
    }

    /**
     * Create a {@link io.ib67.bukkit.chat.action.client.ClientClickAction.OpenFile} with given path
     *
     * @param pathToFile path to the file to be opened (on the client side)
     * @return
     */
    public static ClientClickAction.OpenFile openFileClick(String pathToFile) {
        return new ClientClickAction.OpenFile(pathToFile);
    }

    public static ClientClickAction.SuggestText suggestTextClick(String text) {
        return new ClientClickAction.SuggestText(text);
    }

    public static ClientClickAction.WriteClipboard writeClipboardClick(String text) {
        return new ClientClickAction.WriteClipboard(text);
    }

    // HOVER
    public static ClientHoverAction.ShowEntity showEntityHover(Entity entity) {
        return new ClientHoverAction.ShowEntity(entity);
    }

    public static ClientHoverAction.ShowItem showItemHover(ItemStack itemStack) {
        return new ClientHoverAction.ShowItem(itemStack);
    }

    public static ClientHoverAction.ShowText showTextHover(String text) {
        return new ClientHoverAction.ShowText(text);
    }
}
