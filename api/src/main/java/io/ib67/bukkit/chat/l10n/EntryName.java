package io.ib67.bukkit.chat.l10n;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * Serialize field to another name
 */
@ApiStatus.AvailableSince("0.1.0")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EntryName {
    String value();
}
