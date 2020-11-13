package me.s1mple.matrix.Tournament.Listener;

import me.s1mple.matrix.Tournament.Data.PlayerData;
import me.s1mple.matrix.Tournament.Data.Round;
import me.s1mple.matrix.Tournament.Data.Tournament;
import me.s1mple.matrix.Tournament.TournamentHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

public class TournamentListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(TournamentHandler.playersInGame.containsKey(event.getEntity())) {
            TournamentHandler.playersInGame.get(event.getEntity()).finishRound(event.getEntity());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        ArrayList<PlayerData> data = new ArrayList<>(TournamentHandler.getPlayerDatas());

        for (PlayerData pd : data) {
            if (pd.getPlayer().equals(event.getPlayer())) {
                TournamentHandler.removeParticipatorFromTournament(event.getPlayer());
                TournamentHandler.getPlayerDatas().remove(pd);
                break;
            }
        }
    }
}
