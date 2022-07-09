package io.ib67.chat;

import io.ib67.bukkit.chat.Papi;
import io.ib67.bukkit.chat.Text;
import io.ib67.bukkit.chat.theme.TextThemes;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LingTest extends JavaPlugin implements Listener {
    private Text text;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        TextThemes.DEFAULT = TextThemes.worldguard;
        text = Text.of("Hello! *{{papi:player_name}}*, You have {{ mailcount }} mails, ") // papi and your custom placeholders...
                .concat(
                        Text.of("[Click Here](/mailbox) to check your mailbox.")
                            /*    .withClickAction(sender -> {
                                    sender.sendMessage("Please click the underlined text.");
                                    return true;
                                })*/
                );
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        text.send(event.getPlayer(), Papi.IT.and(this::myPlaceHolder));
    }

    private String myPlaceHolder(CommandSender sender, String s) {
        if (s.equals("mailcount")) {
            return "114514";
        }
        return null; // left it to next placeholder...;
    }
}
