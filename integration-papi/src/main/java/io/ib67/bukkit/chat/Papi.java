package io.ib67.bukkit.chat;

import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class Papi implements PlaceHolder {
    public static final Papi IT = new Papi();

    @Override
    public String apply(CommandSender sender, String s) {
        if (!s.startsWith("papi:") || s.length() == "papi:".length()) {
            return null;
        }
        if (sender instanceof OfflinePlayer player) {
            var p = s.split(":");

            var arr = p[1].split("_");
            if (arr.length != 2) {
                return null;
            }
            var identifier = arr[0];
            var parameter = arr[1];
            return PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().findExpansionByIdentifier(identifier)
                    .map(e -> e.onRequest(player, parameter))
                    .orElse(null);
        }
        return null; // Unsupported sender type
    }
}
