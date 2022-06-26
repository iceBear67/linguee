package io.ib67.bukkit.mcup;

import io.ib67.bukkit.mcup.token.*;
import net.md_5.bungee.api.ChatColor;
import org.inlambda.kiwi.Kiwi;

import java.util.Stack;

public final class DSLCompiler {
    private int i = 0;
    private StringBuilder collector = new StringBuilder();
    private Stack<TokenType> mode = new Stack<>();
    private Stack<MDToken<?>> tokens = new Stack<>();

    public DSLCompiler() {

    }

    public static void main(String[] args) {
        var compiler = new DSLCompiler();
        compiler.toTokenStream("""
                &a*aa[Hello! [Your *NAME*\\] ](https://github.cpm)*
                """.trim()).stream().forEach(System.out::println);
    }

    private static String orDefault(String s1, String d) {
        if (s1 == null || s1.isEmpty()) {
            return d;
        }
        return s1;
    }

    public Stack<MDToken<?>> toTokenStream(String text) {
        var chars = text.toCharArray();
        boolean escaping = false;
        mode.push(TokenType.PLAIN);

        String display = null;
        String url;
        boolean urlPhase = false;

        int itali_start = -1;
        for (i = 0; i < chars.length; i++) {
            var eof = i == chars.length - 1;
            var now = chars[i];
            var hasLast = i > 0;
            var last = hasLast ? chars[i - 1] : ' ';
            var hasNext = i < chars.length - 1;
            var next = hasNext ? chars[i + 1] : ' ';
            if (escaping) {
                collector.append(now);
                escaping = false;
                continue;
            }
            switch (now) {
                case '\\':
                    escaping = true;
                    break;
                case '*':
                    if (mode.peek() == TokenType.LINK) {
                        collector.append('*');
                        break;
                    }
                    if (mode.peek() == TokenType.ITALIC) {
                        // end of this italic.
                        mode.pop();
                        var result = getAndCleanResult();
                        if (result.isEmpty()) { // Sit: ** -> Bold.
                            // can be a link or something above..
                            if (i - itali_start > 1) {
                                // it's a link or color. and we 're end
                                tokens.push(new Italic(result));
                            } else {
                                mode.push(TokenType.BOLD); // enter BOLD mode.
                            }
                        } else {
                            tokens.push(new Italic(result));
                        }
                        break;
                    } else if (mode.peek() == TokenType.BOLD) {
                        if (hasNext && next == '*') {
                            mode.pop();
                            // end this bold.
                            tokens.push(new Bold(getAndCleanResult()));
                            i++; // skip next *
                        } else { // ** a * <- or ** *<-a* **
                            if (eof) {
                                collector.append(now);
                                tokens.push(new Bold(getAndCleanResult()));
                                mode.pop();
                            } else {
                                tokens.push(new Bold(getAndCleanResult()));
                                mode.push(TokenType.ITALIC);
                            }
                        }
                        break;
                    } else {
                        // save lasts
                        //   tokens.push(new Plain(getAndCleanResult()));
                        saveLast();
                        //mode.pop();
                        mode.push(TokenType.ITALIC);
                        itali_start = i;
                        break;
                    }
                case '`':
                    if (mode.peek() == TokenType.LINK) {
                        collector.append('`');
                        break;
                    }
                    if (mode.peek() == TokenType.QUOTE) {
                        // end.
                        mode.pop();
                        tokens.push(new Quote(getAndCleanResult()));
                    } else {
                        // save lasts
                        tokens.push(new Plain(getAndCleanResult()));
                        // start.
                        mode.push(TokenType.QUOTE);
                    }
                    break;
                case '[':
                    if (mode.peek() != TokenType.LINK) {
                        saveLast();
                        mode.push(TokenType.LINK);
                        break;
                    } // left it to default
                    collector.append(now);
                    break;
                case ']':
                    if (mode.peek() == TokenType.LINK) {
                        if (hasNext && next == '(') {
                            display = getAndCleanResult();
                        } else { // not a [XX]() -> save as [XX]
                            mode.pop(); // back.
                            collector.insert(0, '[');
                            collector.append(']');
                            saveLast();
                            break;
                        }
                        break;
                    }
                    collector.append(now);
                    break;
                // left it to default
                case '(':
                    if (mode.peek() == TokenType.LINK) {
                        // load as URL.
                        urlPhase = true;
                        break;
                    } // else: default
                    collector.append(now);
                    break;
                case ')':
                    if (mode.peek() == TokenType.LINK && urlPhase) {
                        urlPhase = false;
                        url = getAndCleanResult();
                        mode.pop();
                        // clone stack.
                        var stack = new Stack<TokenType>();
                        stack.addAll(mode);
                        tokens.push(new Link(new Link.LinkData(display, url, stack), TokenType.LINK));
                        break;
                    }
                    break;
                case '&':
                    if (hasNext) {
                        if (next == '<') {
                            Kiwi.todo("Colour DSL");
                        } else {
                            i++;
                            tokens.push(new ColorBegin(new ColorBegin.ColorData(ChatColor.getByChar(next))));
                        }
                        break;
                    }
                    collector.append(now);
                    break;
                default:
                    collector.append(now);
            }
        }
        saveLast();
        {
            var it = tokens.iterator();
            while (it.hasNext()) {
                var p = it.next();
                if (p.data instanceof String s && s.isEmpty()) {
                    it.remove();
                }
            }
        }
        return tokens;
    }

    private void saveLast() {
        tokens.push(switch (mode.peek()) {
            case ITALIC -> new Italic(orDefault(getAndCleanResult(), "*")); // a * on the end.
            case BOLD -> new Bold(orDefault(getAndCleanResult(), "**"));
            case QUOTE -> new Quote(orDefault(getAndCleanResult(), "`"));
            case LINK, COLOR_BEGIN -> throw new IllegalStateException("Unexcepted End of File");
            case PLAIN -> new Plain(getAndCleanResult());
        });
    }

    private String getAndCleanResult() {
        var result = collector.toString();
        collector = new StringBuilder();
        return result;
    }
}
