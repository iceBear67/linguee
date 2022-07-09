package io.ib67.bukkit.chat.action.client;

import io.ib67.bukkit.chat.ClickAction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.0")
public sealed abstract class ClientClickAction implements ClickAction {
    protected final String value;

    public ClientClickAction(String value) {
        this.value = value;
    }

    @Override
    public boolean onClick(Player sender) {
        throw new AssertionError("This should not be called");
    }

    public String getValue() {
        return value;
    }

    /**
     * Executes a command.
     */
    @ApiStatus.AvailableSince("0.1.0")
    public static final class ExecuteCommand extends ClientClickAction {
        /**
         * Creates a new ExecuteCommand.
         *
         * @param value the command to execute, starts with `/`
         */
        public ExecuteCommand(String value) {
            super(value);
        }
    }

    /**
     * Open a link.
     */
    @ApiStatus.AvailableSince("0.1.0")
    public static final class OpenLink extends ClientClickAction {
        /**
         * Creates a new OpenLink.
         *
         * @param value the url to be opened by the client.
         */
        public OpenLink(String value) {
            super(value);
        }
    }

    /**
     * Open a file at the given path.
     */
    @ApiStatus.AvailableSince("0.1.0")
    public static final class OpenFile extends ClientClickAction {
        /**
         * Creates a new OpenFile.
         *
         * @param value the path to the file to be opened.
         */
        public OpenFile(String value) {
            super(value);
        }
    }

    @ApiStatus.AvailableSince("0.1.0")
    public static final class SuggestText extends ClientClickAction {
        /**
         * Creates a new SuggestText.
         *
         * @param value the text to be suggested, which will be filled into the textbox.
         */
        public SuggestText(String value) {
            super(value);
        }
    }

    @ApiStatus.AvailableSince("0.1.0")
    public static final class WriteClipboard extends ClientClickAction {
        /**
         * Creates a new WriteClipboard.
         *
         * @param value the text to be written to the clipboard.
         */
        public WriteClipboard(String value) {
            super(value);
        }
    }
}
