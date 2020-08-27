package me.s1mple.matrix.Tournament.Data;

import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tournament {
    private static List<Player> fightingPlayers = new ArrayList<>();

    private List<Arena> arenas;

    private List<PlayerData> participators;
    private List<PlayerData[]> orderedPairs;
    private List<PlayerData> actRoundWinners;
    private List<PlayerData> actRoundLosers;

    public Tournament() {
        participators = new ArrayList<>();
        orderedPairs = new ArrayList<>();
        actRoundLosers = new ArrayList<>();
        actRoundWinners = new ArrayList<>();
    }

    /**
     * Starts round
     */
    public void startRound() {
        // If first round, order participators and start more rounds (as long as arenas are free)
        throw new NotImplementedException();
    }

    /**
     * Called when round is finished
     * aka a player gets killed
     * @param winner
     * @param looser
     */
    public void finishRound(Player winner, Player looser) {
        // if it was last round, re-order participators
        throw new NotImplementedException();
    }

    /**
     * Orders partivipators in pairs
     */
    private void orderPairs(List<PlayerData> toOrder) {
        // Order the given list and save it to @{orderedPairs}
        throw new NotImplementedException();
    }

    /**
     * Adds participator to tournament
     * @param participator
     */
    public void addParticipator(UUID participator) {
        // make sure round has an even amount of participators
        throw new NotImplementedException();
    }

    /**
     * When a participator does /tt leave
     * @param participator
     */
    public void removeParticipator(Player participator) {
        throw new NotImplementedException();
    }

    /**
     * Searches for a free arena and returns it.
     */
    public Arena searchFreeArena() {
        throw new NotImplementedException();
    }


}
