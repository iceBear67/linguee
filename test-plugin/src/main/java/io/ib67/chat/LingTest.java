package io.ib67.chat;

import io.ib67.bukkit.chat.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LingTest extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Text.of(event.getMessage()).send(event.getPlayer());
    }
}
