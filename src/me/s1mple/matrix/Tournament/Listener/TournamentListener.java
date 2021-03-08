package me.s1mple.matrix.Tournament.Listener;

import me.s1mple.matrix.Tournament.TournamentHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class TournamentListener implements Listener {
    HashMap<Player, Location> respawnLoc = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (TournamentHandler.playersInGame.containsKey(event.getEntity())) {
            respawnLoc.put(event.getEntity(), TournamentHandler.getArenaOfPlayer(event.getEntity()).getSpectatorPoint());
            TournamentHandler.playersInGame.get(event.getEntity()).finishRound(event.getEntity());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (respawnLoc.containsKey(event.getPlayer())) {
            event.setRespawnLocation(respawnLoc.get(event.getPlayer()));
            respawnLoc.remove(event.getPlayer());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        TournamentHandler.removeParticipatorFromTournament(event.getPlayer());

        TournamentHandler.getPlayerDatas().remove(TournamentHandler.getPlayerData(event.getPlayer()));
    }

    @EventHandler
    public void onTP(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.PLUGIN && TournamentHandler.getTournamentOfPlayer(event.getPlayer()) != null)
            event.setCancelled(true);
    }
}
