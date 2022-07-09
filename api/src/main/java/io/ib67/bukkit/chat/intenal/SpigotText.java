package io.ib67.bukkit.chat.intenal;

import io.ib67.bukkit.chat.ClickAction;
import io.ib67.bukkit.chat.PlaceHolder;
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
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.inlambda.kiwi.RandomHelper;
import org.inlambda.kiwi.Stack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

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

    private Map<CommandSender, String> clickCallbackKey = new WeakHashMap<>(Math.min(Bukkit.getMaxPlayers(), 32));

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
        var cText = new ArrayList<>(concatedTexts);
        if (withSpace) {
            cText.add(isolateColor ? SPACER_ISOLATE_COLOR : SPACER);
        } else if (isolateColor) {
            cText.add(ISOLATE_COLOR);
        }
        cText.add(anotherText);
        return new SpigotText(defaultColor, this, cText, true, theme);
    }

    @Override
    public void send(@NotNull CommandSender sender, @NotNull PlaceHolder placeholderMapper, int delay) {
        // renderer.
        Bukkit.getScheduler().runTaskLater(Linguee.getInstance().getPlugin(), () -> {
            var components = new LinkedList<>(List.of(render(sender, theme, placeholderMapper)));
            components.addAll(concatedTexts.stream()
                    .map(e -> e.render(sender, theme, placeholderMapper))
                    .flatMap(Stream::of)
                    .toList());
            sender.spigot().sendMessage(components.toArray(new BaseComponent[0]));
        }, Math.min(1, delay));
    }

    @Override
    public BaseComponent[] render(@NotNull CommandSender sender, @NotNull TextTheme theme, @NotNull PlaceHolder placeholderMapper) {
        return new BaseComponent[]{render(sender, new MDCompiler(text).toTokenStream(), placeholderMapper, theme, defaultColor)};
    }

    private BaseComponent render(CommandSender sender, Stack<MDToken<?>> ts, @NotNull PlaceHolder placeholderMapper, TextTheme theme, ChatColor defaultColor) {
        boolean italic = false;
        boolean bold = false;
        boolean quote = false;
        boolean placeholder = false;
        ChatColor backColor = defaultColor;
        var components = new LinkedList<BaseComponent>();
        for (MDToken<?> t : ts.toList()) {
            if (t instanceof Bold) bold = t == Bold.BEGIN;
            if (t instanceof Italic) italic = t == Italic.BEGIN;
            if (t instanceof Quote) quote = t == Quote.BEGIN;
            if (t instanceof Placeholder) placeholder = t == Placeholder.BEGN;
            if(t instanceof Color c){
                backColor = c.getColor();
            }
            if (t instanceof Link link) {
                var component = render(sender, link.getData().display(), placeholderMapper, theme,backColor);
                component = theme.getTextFormatter().formatLink(component);
                var url = processUrl(sender, link.getData().url(), placeholderMapper);
                if (url.startsWith("/")) {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, url));
                } else {
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
                }
                components.add(component);
                continue;
            }
            if (t instanceof Literal l) {
                components.add(renderText(sender, italic, bold, quote, l.getData(), theme, placeholder, placeholderMapper,backColor));
            }
        }
        return new TextComponent(components.toArray(new BaseComponent[0]));
    }

    private String processUrl(CommandSender sender, Stack<MDToken<?>> url, PlaceHolder mapper) {
        boolean placeholder = false;
        var sb = new StringBuilder();
        for (MDToken<?> mdToken : url.toList()) {
            if (mdToken == Placeholder.BEGN) placeholder = true;
            if (mdToken == Placeholder.END) placeholder = false;
            var data = mdToken.getData();
            if (data instanceof String s) {
                s = ChatColor.stripColor(s);
                sb.append(placeholder ? mapper.apply(sender, s.trim()) : s);
            }
        }
        return sb.toString();
    }

    private static final BaseComponent EMPTY = new TextComponent("");

    private BaseComponent renderText(CommandSender sender,
                                     boolean italic,
                                     boolean bold,
                                     boolean quote,
                                     String data,
                                     TextTheme theme,
                                     boolean isPlaceholder,
                                     @NotNull PlaceHolder placeholderMapper,
                                     ChatColor backColor) {
        if (data.length() == 0) {
            return EMPTY;
        }
        var component = new TextComponent();
        component.setText(isPlaceholder ? orDefault(placeholderMapper.apply(sender, data.trim()), "{{ " + data + " }}") : data);
        component = (TextComponent) theme.getTextFormatter().formatRegular(component);
        if (backColor != null) {
            component.setColor(backColor);
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
        } else if (clickAction != null) {
            var key = clickCallbackKey.computeIfAbsent(sender, s -> RandomHelper.string(32));
            var info = Linguee.getInstance().getCallbackInfo(key);
            if (info == null) {
                Linguee.getInstance().registerCallback(key, clickAction);
            }
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/linguee-handler " + key));
        }
        if (isPlaceholder) {
            var comp = new TextComponent();
            comp.setColor(ChatColor.RESET);
            component.addExtra(comp);
        }
        return component;
    }

    private String orDefault(String apply, String s) {
        return apply == null ? s : apply;
    }

    @Override
    public String str() {
        return text;
    }
}
