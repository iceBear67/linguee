package io.ib67.bukkit.chat.intenal.handler;

import io.ib67.bukkit.chat.intenal.Linguee;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public final class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncChat(PlayerCommandPreprocessEvent event) {
        var message = event.getMessage();
        if(message.startsWith("/linguee-handler ")){
            message = message.replaceFirst("/linguee-handler ","");
            var info = Linguee.getInstance().getCallbackInfo(message);
            if (info == null) {
                return;
            }
            event.setCancelled(true);
            var result = info.onClick(event.getPlayer());
            if (result) Linguee.getInstance().invalidateCallback(message);
        }
    }
}
