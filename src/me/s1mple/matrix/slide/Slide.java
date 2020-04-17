package me.s1mple.matrix.slide;


import me.s1mple.matrix.Matrix;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Slide implements Listener {
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if (event.getPlayer().isSprinting()&&event.isSneaking()){
            event.getPlayer().setWalkSpeed(0.6f);
            BukkitTask run = new BukkitRunnable() {
                @Override
                public void run() {
                    if (event.getPlayer().isSneaking()) {
                        if (event.getPlayer().getWalkSpeed()>0) {
                            event.getPlayer().setWalkSpeed((float)(event.getPlayer().getWalkSpeed() - 0.2));
                        }
                        else {
                            event.getPlayer().setWalkSpeed(0.2f);
                            Bukkit.getServer().getScheduler().cancelTask(this.getTaskId());
                        }
                    }

                    else {
                        event.getPlayer().setWalkSpeed(0.2f);
                        Bukkit.getServer().getScheduler().cancelTask(this.getTaskId());

                    }
                }
            }.runTaskTimer(Matrix.getPlugin(),5,5);

        }
    }
}
