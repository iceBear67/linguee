package io.ib67.bukkit.mcup;

import io.ib67.bukkit.mcup.token.*;

import java.util.Stack;

public final class MDCompiler {
    private final StringBuilder collector = new StringBuilder();
    private final Stack<MDToken<?>> tokens = new Stack<>();
    private final String context;

    public MDCompiler(String context) {
        this.context = context;
    }

    public static void main(String[] args) {
        var compiler = new MDCompiler("""
                **bold text***italic text* [TextData](/command) a [aTextData](/acommand)
                """.trim());
        compiler.toTokenStream().stream().forEach(System.out::println);
    }

    public Stack<MDToken<?>> toTokenStream() {
        if (!tokens.empty()) {
            return tokens;
        }
        var chars = context.toCharArray();
        var escape = false;

        boolean link = false;
        boolean display = false;
        String displayT = null;
        for (int i = 0; i < chars.length; i++) {
            var now = chars[i];
            var hasNext = i < chars.length - 1;
            var next = hasNext ? chars[i + 1] : ' ';
            var hasLast = i != 0;
            var last = hasLast ? chars[i - 1] : ' ';
            if (escape) {
                collector.append(now);
                continue;
            }
            switch (now) {
                case '\\':
                    escape = true;
                    continue;
                case '*':
                    if (display || link) {
                        collector.append(now);
                        continue;
                    }
                    if (hasNext && next == '*') { // look forward: **
                        // bold mode.
                        i++; // move pointer
                        saveLit();
                        lastOrPush(Bold.BEGIN, Bold.END);
                    } else {
                        saveLit();
                        lastOrPush(Italic.BEGIN, Italic.END);
                    }
                    break;
                case '[':
                    if (display) {
                        collector.append(now);
                    } else {
                        // suspect it.
                        saveLit();
                        display = true;
                    }
                    break;
                case ']':
                    if (display) {
                        // in display mode and capt.
                        if (hasNext && next == '(') {
                            link = true;
                            i++; // skip (
                            displayT = collector.toString();
                            collector.setLength(0);
                        }
                        break;
                    } else {
                        collector.append(now);
                    }
                    break;
                case ')':
                    if (link) {
                        var url = collector.toString();
                        collector.setLength(0);
                        tokens.push(new Link(new LinkData(new MDCompiler(displayT).toTokenStream(), url)));
                        display = false;
                        link = false;
                    } else {
                        collector.append(now);
                    }
                    break;
                default:
                    collector.append(now);
            }
        }
        saveLit();
        return tokens;
    }

    private void saveLit() {
        var lit = collector.toString();
        collector.setLength(0);
        tokens.push(new Literal(lit));
    }

    private void lastOrPush(MDToken<?> a, MDToken<?> b) {
        for (int i1 = tokens.size() - 1; i1 >= 0; i1--) {
            var tk = tokens.get(i1);
            if (tk == b) {
                // B E <-
                tokens.push(a);
                return;
            } else if (tk == a) {
                tokens.push(b);
                return;
            }
        }
        tokens.push(a);
    }
}
