package io.ib67.bukkit.chat.l10n;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * Annotations for classes that are language records
 */
@ApiStatus.AvailableSince("0.1.0")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Localized {
    /**
     * Preferred language code for falling-back
     *
     * @return preferred language
     */
    String value() default "";

    StorageType format() default StorageType.PROPERTIES;

    /**
     * Such as kebab-i-m-love-in
     *
     * @return use kebab
     */
    boolean useKebabCase() default true;

    enum StorageType {
        JSON, PROPERTIES, YAML
    }
}
