package io.ib67.bukkit.mcup;

public abstract class MDToken<T> {
    protected final T data;
    protected final TokenType type;


    protected MDToken(T data, TokenType type) {
        this.data = data;
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + (data == null || (data instanceof CharSequence s && s.toString().isEmpty()) ? "" : " -> " + data);
    }

    public static abstract class Flag extends MDToken<Flag> {

        protected Flag(TokenType type) {
            super(null, type);
        }
    }
}
