package io.ib67.bukkit.mcup;

import io.ib67.bukkit.mcup.token.*;

import java.util.Stack;

public final class DSLCompiler {
    private int i = 0;
    private StringBuilder collector = new StringBuilder();
    private Stack<MDToken<?>> tokens = new Stack<>();

    public DSLCompiler() {

    }

    public static void main(String[] args) {
        var compiler = new DSLCompiler();
        compiler.toTokenStream("""
                **bold text***italic text* [TextData](/command)
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
                        tokens.push(new Link(new LinkData(new DSLCompiler().toTokenStream(displayT), url)));
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

    private String getAndCleanResult() {
        var result = collector.toString();
        collector = new StringBuilder();
        return result;
    }
}
