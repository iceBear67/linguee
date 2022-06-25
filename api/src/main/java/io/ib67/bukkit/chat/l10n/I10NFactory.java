package io.ib67.bukkit.chat.l10n;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.AvailableSince("0.1.0")
public interface I10NFactory<T> {
    /**
     * Create a I10N Record from given locale, otherwise fallback to default.
     *
     * @param locale locale code
     * @return I10N Record
     */
    T create(@Nullable String locale);

    default T create() {
        return create(null);
    }
}
