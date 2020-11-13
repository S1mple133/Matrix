package me.s1mple.matrix.Tournament.Data;

import me.s1mple.matrix.Matrix;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerData implements Comparable<PlayerData> {
    private int roundsWon;
    private int tournamentsWon;
    private int roundsLost;
    private UUID player;
    private int participatedInTournaments;
    private boolean isInTournament;

    public PlayerData(Player player, int roundsLost, int tournamentsWon, int roundsWon, int participatedInTournaments) {
        this.roundsLost = roundsLost;
        this.tournamentsWon = tournamentsWon;
        this.roundsWon = roundsWon;
        this.player = player.getUniqueId();
        this.participatedInTournaments = participatedInTournaments;
        isInTournament = false;
    }

    public void wonTournament() {
        this.tournamentsWon++;
    }

    public void joinedTournament() {
        this.participatedInTournaments++;
        isInTournament = true;
    }

    public void lostRound() {
        this.roundsLost++;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public int getTournamentsWon() {
        return tournamentsWon;
    }

    public int getRoundsLost() {
        return roundsLost;
    }

    public Player getPlayer() {
        return Matrix.getPlugin().getServer().getPlayer(player);
    }

    private double killDeathRate() {
        return roundsWon / (double)roundsLost;
    }

    /**
     * Returns amount of tournaments this player participated in.
     * @return
     */
    public int getParticipatedInTournaments() {
        return participatedInTournaments;
    }

    public void wonRound() {
        this.roundsWon++;
    }

    @Override
    public int compareTo(@NotNull PlayerData o) {
        return (int) Math.round(killDeathRate() - o.killDeathRate());
    }

    public boolean inTournament() {
        return isInTournament;
    }

    public void leftTournament() {
        isInTournament = false;
    }
}
