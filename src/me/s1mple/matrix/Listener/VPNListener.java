package me.s1mple.matrix.Listener;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class VPNListener implements Listener {
    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        Util.isUsingVPN(e.getPlayer(), (hasVPN) -> {
            if (hasVPN) {
                Bukkit.getScheduler().runTask(Matrix.getPlugin(), () -> {
                    e.getPlayer().kickPlayer(ChatColor.RED + "Using VPNs or Proxies is not allowed!");
                });
            }
        });
    }
}
