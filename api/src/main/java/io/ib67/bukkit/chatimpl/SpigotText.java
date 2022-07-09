package io.ib67.bukkit.chatimpl;

import io.ib67.bukkit.chat.ClickAction;
import io.ib67.bukkit.chat.Text;
import io.ib67.bukkit.chat.action.client.ClientClickAction;
import io.ib67.bukkit.chat.action.client.ClientHoverAction;
import io.ib67.bukkit.chat.theme.TextTheme;
import io.ib67.bukkit.mcup.MDCompiler;
import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.token.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.inlambda.kiwi.Kiwi;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SpigotText implements Text {
    private static final Text SPACER = Text.of(" ");
    private static final Text SPACER_ISOLATE_COLOR = Text.of(" &r");
    private static final Text ISOLATE_COLOR = Text.of("&r");
    private ClickAction clickAction;
    private ClientHoverAction<?> hoverAction;
    private ChatColor defaultColor;
    private final List<Text> concatedTexts;
    private final String text;
    private final boolean compile;
    private final TextTheme theme;

    SpigotText(ChatColor defaultColor, SpigotText text, List<Text> concatedTexts, boolean compile, TextTheme theme) {
        this.defaultColor = defaultColor;
        this.concatedTexts = concatedTexts;
        this.text = text.text;
        this.clickAction = text.clickAction;
        this.hoverAction = text.hoverAction;
        this.compile = compile;
        this.theme = theme;
    }

    public SpigotText(ChatColor defaultColor, String text, boolean compile, TextTheme theme) {
        this.defaultColor = defaultColor;
        this.compile = compile;
        this.theme = theme;
        this.concatedTexts = new ArrayList<>();
        this.text = text;
    }

    @Override
    public Text withColor(ChatColor defaultColor) {
        this.defaultColor = defaultColor;
        return this;
    }

    @Override
    public Text withTheme(TextTheme theme) {
        return new SpigotText(defaultColor, this, concatedTexts, compile, theme);
    }

    @Override
    public Text withClickAction(@NotNull ClickAction action) {
        if (!(action instanceof ClientClickAction)) {
            Kiwi.todo();
        }
        this.clickAction = action;
        return this;
    }

    @Override
    public Text withHoverAction(@NotNull ClientHoverAction<?> action) {
        hoverAction = action;
        return this;
    }

    @Override
    public Text concat(@NotNull Text anotherText, boolean withSpace, boolean isolateColor) {
        var cText = new LinkedList<>(concatedTexts);
        if (withSpace) {
            cText.add(isolateColor ? SPACER_ISOLATE_COLOR : SPACER);
        } else if (isolateColor) {
            cText.add(ISOLATE_COLOR);
        }
        cText.add(anotherText);
        return new SpigotText(defaultColor, this, cText, true, theme);
    }

    @Override
    public void send(@NotNull CommandSender sender, @NotNull UnaryOperator<String> placeholderMapper, int delay) {
        // renderer.
        var components = new LinkedList<BaseComponent>();
        components.addAll(List.of(render(theme, placeholderMapper)));
        for (Text concatedText : concatedTexts) {
            components.addAll(List.of(concatedText.render(theme, placeholderMapper)));
        }
        sender.spigot().sendMessage(components.toArray(new BaseComponent[0]));
    }

    public BaseComponent[] render(TextTheme theme, @NotNull UnaryOperator<String> placeholderMapper) {
        return new BaseComponent[]{render(new MDCompiler(text).toTokenStream(), placeholderMapper, theme)};
    }

    private BaseComponent render(Stack<MDToken<?>> ts, @NotNull UnaryOperator<String> placeholderMapper, TextTheme theme) {
        boolean italic = false;
        boolean bold = false;
        boolean quote = false;
        var components = new LinkedList<BaseComponent>();
        for (MDToken<?> t : ts) {
            if (t instanceof Bold) bold = t == Bold.BEGIN;
            if (t instanceof Italic) italic = t == Italic.BEGIN;
            if (t instanceof Quote) quote = t == Quote.BEGIN;
            if (t instanceof Link link) {
                var component = render(link.getData().display(), placeholderMapper, theme);
                component = theme.getTextFormatter().formatLink(component);
                var url = link.getData().url();
                if (url.startsWith("/")) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, link.getData().url()));
                } else {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link.getData().url()));
                }
                components.add(component);
                continue;
            }
            if (t instanceof Literal l) {
                components.add(renderText(italic, bold, quote, l.getData(), theme, placeholderMapper));
            }
        }
        return components.stream().reduce((a, b) -> {
            a.addExtra(b);
            return a;
        }).orElseThrow();
    }

    private BaseComponent renderText(boolean italic, boolean bold, boolean quote, String data, TextTheme theme, @NotNull UnaryOperator<String> placeholderMapper) {
        var component = new TextComponent();
        component.setText(data);
        component = (TextComponent) theme.getTextFormatter().formatRegular(component);
        if (defaultColor != null) {
            component.setColor(defaultColor);
        }
        if (bold) {
            component = (TextComponent) theme.getTextFormatter().formatBold(component);
        }
        if (italic || quote) {
            component = (TextComponent) theme.getTextFormatter().formatItalic(component);
        }
        if (clickAction instanceof ClientClickAction s) {
            ClickEvent.Action act = null;
            if (s instanceof ClientClickAction.ExecuteCommand) {
                act = ClickEvent.Action.RUN_COMMAND;
            } else if (s instanceof ClientClickAction.OpenFile) {
                act = ClickEvent.Action.OPEN_FILE;
            } else if (s instanceof ClientClickAction.OpenLink) {
                act = ClickEvent.Action.OPEN_URL;
            } else if (s instanceof ClientClickAction.SuggestText) {
                act = ClickEvent.Action.SUGGEST_COMMAND;
            } else if (s instanceof ClientClickAction.WriteClipboard) {
                act = ClickEvent.Action.COPY_TO_CLIPBOARD;
            }
            component.setClickEvent(new ClickEvent(act, s.getValue()));
            component = (TextComponent) theme.getTextFormatter().formatActive(component);
        }
        return component;
    }

    @Override
    public String str() {
        return text;
    }
}
