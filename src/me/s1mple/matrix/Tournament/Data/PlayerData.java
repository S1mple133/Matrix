package me.s1mple.matrix.Tournament.Data;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerData {
    private int roundsWon;
    private int tournamentsWon;
    private int roundsLost;
    private Player player;
    private int participatedInTournaments;

    public PlayerData(Player player, int roundsLost, int tournamentsWon, int roundsWon, int participatedInTournaments) {
        this.roundsLost = roundsLost;
        this.tournamentsWon = tournamentsWon;
        this.roundsWon = roundsWon;
        this.player = player;
        this.participatedInTournaments = participatedInTournaments;
    }

    public void wonTournament() {
        this.tournamentsWon++;
    }

    public void joinedTournament() {
        this.participatedInTournaments++;
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
        return player;
    }

    /**
     * Returns amount of tournaments this player participated in.
     * @return
     */
    public int getParticipatedInTournaments() {
        return participatedInTournaments;
    }
}
