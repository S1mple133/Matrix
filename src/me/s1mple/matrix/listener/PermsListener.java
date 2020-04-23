package me.s1mple.matrix.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.tehkode.permissions.PermissionEntity;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.permissions.events.PermissionEntityEvent;
import ru.tehkode.permissions.events.PermissionEvent;

public class PermsListener implements Listener {

    @EventHandler
    public void onPex(PermissionEntityEvent event) {
        if(event.getEntity().has("*") && Bukkit.getServer().getPlayer(event.getEntity().getName()) != null
        && !event.getEntity().getName().equalsIgnoreCase("CrashCringle12")  && !event.getEntity().getName().equalsIgnoreCase("S1mple133")
        && !event.getEntity().getName().equalsIgnoreCase("epiclol101") && !event.getEntity().getName().equalsIgnoreCase("TristanReturns")) {
            event.getEntity().removePermission("*");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + event.getEntity().getName() + " tried giving himself *");
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().startsWith("/op") && Bukkit.getServer().getPlayer(event.getMessage().split(" ")[1]) != null &&
        !event.getMessage().split(" ")[1].equalsIgnoreCase("CrashCringle12") && !event.getMessage().split(" ")[1].equalsIgnoreCase("S1mple133")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to do tha!");
        }
    }

}
