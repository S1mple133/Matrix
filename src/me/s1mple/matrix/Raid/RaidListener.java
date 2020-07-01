package me.s1mple.matrix.Raid;

import org.bukkit.entity.Raider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidSpawnWaveEvent;

public class RaidListener implements Listener {

    @EventHandler
    public void onRaid(RaidSpawnWaveEvent event) {
        for(Raider raider : event.getRaiders()) {
            raider.setCustomName("Servant of Chaos");
        }
    }
}
