package io.ib67.bukkit.chatimpl;

import io.ib67.bukkit.chat.ClickAction;
import io.ib67.bukkit.chat.Text;
import io.ib67.bukkit.chat.action.client.ClientClickAction;
import io.ib67.bukkit.chat.action.client.ClientHoverAction;
import io.ib67.bukkit.mcup.MDCompiler;
import io.ib67.bukkit.mcup.MDToken;
import io.ib67.bukkit.mcup.token.Bold;
import io.ib67.bukkit.mcup.token.Italic;
import io.ib67.bukkit.mcup.token.Literal;
import io.ib67.bukkit.mcup.token.Quote;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.inlambda.kiwi.Kiwi;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class SpigotText implements Text {
    private static final Text SPACER = Text.of(" ");
    private static final Text SPACER_ISOLATE_COLOR = Text.of(" &r");
    private static final Text ISOLATE_COLOR = Text.of("&r");
    private ClickAction clickAction;
    private ClientHoverAction<?> hoverAction;
    private final List<Text> concatedTexts;
    private final String text;
    private final boolean compile;

    SpigotText(SpigotText text, List<Text> concatedTexts, boolean compile) {
        this.concatedTexts = concatedTexts;
        this.text = text.text;
        this.clickAction = text.clickAction;
        this.hoverAction = text.hoverAction;
        this.compile = compile;
    }

    public SpigotText(String text, boolean compile) {
        this.compile = compile;
        this.concatedTexts = new ArrayList<>();
        this.text = text;
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
        return new SpigotText(this, cText, true);
    }

    @Override
    public void send(@NotNull CommandSender sender, @NotNull Function<String, Object> placeholderMapper, int delay) {
        // renderer.
        var components = new LinkedList<BaseComponent>();
        components.addAll(List.of(render(placeholderMapper)));
        for (Text concatedText : concatedTexts) {
            components.addAll(List.of(concatedText.render(placeholderMapper)));
        }
        sender.spigot().sendMessage(components.toArray(new BaseComponent[0]));
    }

    @Override
    public BaseComponent[] render(@NotNull Function<String, Object> placeholderMapper) {
        return new BaseComponent[]{render(text, placeholderMapper)};
    }

    private BaseComponent render(String text, @NotNull Function<String, Object> placeholderMapper) {
        var ts = new MDCompiler(text).toTokenStream();
        boolean italic = false;
        boolean bold = false;
        boolean quote = false;
        var components = new LinkedList<BaseComponent>();
        for (MDToken<?> t : ts) {
            if (t instanceof Bold) bold = t == Bold.BEGIN;
            if (t instanceof Italic) italic = t == Italic.BEGIN;
            if (t instanceof Quote) quote = t == Quote.BEGIN;
            if (t instanceof Literal l) {
                components.add(renderText(italic, bold, quote, l.getData()));
            }
        }
        return components.stream().reduce((a, b) -> {
            a.addExtra(b);
            return a;
        }).orElseThrow();
    }

    private BaseComponent renderText(boolean italic, boolean bold, boolean quote, String data) {
        var component = new TextComponent();
        component.setText(data);
        component.setBold(bold);
        component.setItalic(italic || quote);
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
        }
        return component;
    }
}
