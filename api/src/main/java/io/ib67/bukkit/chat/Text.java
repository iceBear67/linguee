package io.ib67.bukkit.chat;

import io.ib67.bukkit.chat.action.client.ClientHoverAction;
import io.ib67.bukkit.chat.theme.TextTheme;
import io.ib67.bukkit.chat.theme.TextThemes;
import io.ib67.bukkit.chatimpl.SpigotText;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.inlambda.kiwi.Kiwi.todo;

/**
 * Text is the core concept in Project Linguee, which represents an <strong>immutable (for text)</strong> object that holds information for player.<br>
 * <h1>## Compilation</h1><br>
 * Linguee Texts supports Built-In DSLs for expressive text representations. We will compile them when we're creating a Text.({@link #of(String...)})<br>
 * For further information, refer to online documentation.
 */
@ApiStatus.AvailableSince("0.1.0")
public interface Text {
    /**
     * Renders color for the given text.
     *
     * @param text text to be rendered
     * @return rendered text
     */
    static String color(@NotNull String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Compiles the texts into a text. Texts will be separated by a space.
     *
     * @param text texts to be compiled
     * @return compiled text
     */
    static Text of(@NotNull String... text) {
        return new SpigotText(defaultColor, Arrays.stream(text).collect(Collectors.joining(" ")), true, TextThemes.DEFAULT);
    }

    /**
     * Create a text with these texts without compilation.
     *
     * @param text texts
     * @return text
     */
    static Text ofRaw(@NotNull String... text) {
        return new SpigotText(defaultColor, Arrays.stream(text).collect(Collectors.joining(" ")), false, TextThemes.DEFAULT);
    }

    /**
     * Compiles the texts into a text. Texts will be separated by a line separator.
     *
     * @param text texts to be compiled
     * @return compiled text
     */
    static Text ofLines(String... text) {
        return of(Arrays.stream(text).collect(Collectors.joining("\n")));
    }

    /**
     * As same as {@link #ofLines(String...)} but with feature from {@link #ofRaw(String...)}
     *
     * @param text
     * @return
     */
    static Text ofLinesRaw(String... text) {
        return ofRaw(Arrays.stream(text).collect(Collectors.joining("\n")));
    }

    /**
     * Set the default color of this text
     * @param defaultColor default color
     * @return
     */
    Text withColor(ChatColor defaultColor);

    /**
     * Add a theme to the text.
     *
     * @param theme
     * @return
     */
    Text withTheme(TextTheme theme);

    /**
     * Sets the action when clicking this text.
     * Can only set one. Others will override the previous one.
     *
     * @param action action
     * @return this
     */
    Text withClickAction(@NotNull ClickAction action);

    /**
     * Sets the action when hovering this text.
     * Can only set one. Others will override the previous one.
     *
     * @param action action
     * @return this
     */
    Text withHoverAction(@NotNull ClientHoverAction<?> action);

    /**
     * Combine two texts into one. The object is on the first and the param text is on the second.
     *
     * @param anotherText  another text
     * @param withSpace    should we add a space
     * @param isolateColor prevent text to be polluted by the color from this.
     * @return the combined text
     */
    Text concat(@NotNull Text anotherText, boolean withSpace, boolean isolateColor);

    /**
     * Send results to the audience, also renders the text (if templates present).
     *
     * @param sender            audience of the text
     * @param placeholderMapper a function which maps placeholder names to value.
     * @param delay             ticks to wait before sending the text.
     * @throws IllegalArgumentException if there are some templates cannot be rendered correctly. usually caused by sending PAPI placeholders to console
     * @throws IllegalStateException    if texts are empty or some value is missing.
     */
    void send(@NotNull CommandSender sender, @NotNull Function<String, Object> placeholderMapper, int delay);

    /**
     * Render the output.
     *
     * @param placeholderMapper a function which maps placeholder names to value.
     * @return renderer result
     */
    BaseComponent[] render(@NotNull TextTheme theme, @NotNull Function<String, Object> placeholderMapper);

    // OVERLOADS - CONCAT

    /**
     * {@link #concat(Text, boolean, boolean)} with defaults. (spaces: true, isolate: true)
     *
     * @param anotherText text to append
     * @return the combined text
     */
    default Text concat(@NotNull Text anotherText) {
        return concat(anotherText, true, true);
    }

    /**
     * {@link #concat(Text, boolean, boolean)} with defaults. (isolate: true)
     *
     * @param anotherText text to append
     * @param withSpace   should we add a space between texts
     * @return the combined text
     */
    default Text concat(@NotNull Text anotherText, boolean withSpace) {
        return concat(anotherText, withSpace, true);
    }

    /**
     * {@link #concat(Text, boolean, boolean)} with string-convert
     *
     * @param anotherText  text to append
     * @param withSpace    should we add a space between texts
     * @param isolateColor prevent text to be polluted by the color from this.
     * @return the combined text
     */
    default Text concat(@NotNull String anotherText, boolean withSpace, boolean isolateColor) {
        return concat(of(anotherText), withSpace, isolateColor);
    }

    /**
     * {@link #concat(Text, boolean, boolean)} with defaults. (spaces: true, isolate: true)
     *
     * @param anotherText text to append
     * @return the combined text
     */
    default Text concat(@NotNull String anotherText) {
        return concat(anotherText, true, true);
    }

    /**
     * {@link #concat(Text, boolean, boolean)} with defaults. (isolate: true)
     *
     * @param anotherText text to append
     * @param withSpace   should we add a space between texts
     * @return the combined text
     */
    default Text concat(@NotNull String anotherText, boolean withSpace) {
        return concat(anotherText, withSpace, true);
    }


    // OVERLOADS -- SEND

    /**
     * Send results to the audience with some defaults (mapper always returning null, delay: 0)
     *
     * @param audience audience of the text
     */
    default void send(@NotNull CommandSender audience) {
        send(audience, any -> null, 0);
    }

    /**
     * Send results to the audience with some defaults (delay: 0)
     *
     * @param audience          audience of the text
     * @param placeholderMapper a function which maps placeholder names to value.
     */
    default void send(@NotNull CommandSender audience, @NotNull Function<String, Object> placeholderMapper) {
        send(audience, placeholderMapper, 0);
    }

    /**
     * Send results to the audience with some defaults (delay: 0)
     *
     * @param audience          audience of the text
     * @param placeholderMapper a map
     */
    default void send(@NotNull CommandSender audience, @NotNull Map<String, Object> placeholderMapper) {
        send(audience, placeholderMapper::get, 0);
    }

    /**
     * Send results to the audience with some delay
     *
     * @param audience
     * @param delay
     */
    default void send(@NotNull CommandSender audience, int delay) {
        send(audience, any -> null, delay);
    }

    /**
     * @return
     */
    @Deprecated
    default String str() {
        return this.toString();
    }


}
