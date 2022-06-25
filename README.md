# Linguee

Text Processing solution which alternatives to Adventure API.

# Features

Linguee has a lot of features that help you to deal with texts.

## Texts are Colorful.

Linguee supports old-fashion chat color style in Minecraft.

`&a Green &b Aqua &c Red &r Reset ....`

we also support gradient colors.

`&<gr:aqua-0xFFFF>Texts which are colored from aqua to red`

## In-Text DSL

Similar to Markdown.

`You can click this [link](....) for help.` -> `You can click this &nlink&r for help.` (clickable)

``Use a `Sticky Wand` to attack them!`` -> `Use a &oSticky Wand&r to attack them!`

`*Sorry!* But you cannot build in *others' residences.` -> `&lSorry! &rBut you cannot build in &bothers' rediences.`

## Themed Texts

With a themed text, you can focus on what you should emphasize to players instead of how do you organize colors.

``Use a `Sticky Wand` to attack them!`` -> `Use a &oSticky Wand&r to attack them!`
-> `&bUse a &e&oSticky Wand&r&b to attack them!`

## Localization

A type-safe localization is so easy.

```java
import io.ib67.bukkit.chat.i10n.Localized;

@Localized
class MyLanguageRecord {
    public final Text noItemRepresent;
}

//...

assert record.noItemRepresent=="There are no items.";
```

## Templates

You can put placeholders into your texts.

`I think {{name}} is abusing his sword.` -> (With `{name = "nullcat_"}`) `I think nullcat_ is abusing his sword.`

### Integrations

Placeholder API is also available and you can inject your post-processors.

`Welcome back! {{papi:player_name}}` -> (With `{name = "nullcat_"}`) `Welcome back! nullcat_`