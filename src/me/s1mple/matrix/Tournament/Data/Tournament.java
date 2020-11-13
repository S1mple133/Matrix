package me.s1mple.matrix.Tournament.Data;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Tournament.Messages;
import me.s1mple.matrix.Tournament.TournamentHandler;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class Tournament {
    private static Server server = Matrix.getPlugin().getServer();

    private int round;

    private List<Arena> freeArenas;

    private List<PlayerData> participators;
    private List<Round> rounds;
    private List<PlayerData> actRoundWinners;
    private List<PlayerData> losers;

    private String Name;

    public Tournament(String name, List<Arena> arenas) {
        participators = new ArrayList<>();
        losers = new ArrayList<>();
        actRoundWinners = new ArrayList<>();
        this.freeArenas = arenas;
        this.rounds = new ArrayList<Round>();
        round = -1;
        this.Name = name;
    }

    /**
     * Starts round
     * @return true if round could be started, playerdata amount should be even
     */
    public boolean startRound() {
        // If first round, order participators and start more rounds (as long as arenas are free)
        if(round <= 0) {
            if (participators.size() % 2 != 0) return false;
            orderPairs(participators);
        }

        Arena actArena;
        while((actArena = searchFreeArena()) != null && round < rounds.size()) {
            rounds.get(round).start(actArena, this);
            round++;
        }

        return true;
    }

    public int getRound() { return round; }


    /**
     * Returns torunament state
     * @return
     */
    @Override
    public String toString() {
        StringBuilder participatorPl = new StringBuilder();
        StringBuilder arenasPl = new StringBuilder();

        for (PlayerData p : participators) {
            participatorPl.append(ChatColor.DARK_GREEN + " * " + ChatColor.GREEN).append(p.getPlayer().getName()).append("\n");
        }

        for(Arena a : freeArenas) {
            arenasPl.append(ChatColor.DARK_GREEN + " * " + ChatColor.GREEN).append(a.getName()).append("\n");
        }

        return ChatColor.DARK_GREEN + "===== " + ChatColor.GREEN +getName() + ChatColor.DARK_GREEN + " =====\n" +
                ChatColor.DARK_GREEN + "Participators\n" +
                participatorPl +
                ChatColor.DARK_GREEN + "Arenas\n" +
                arenasPl;
    }

    /**
     * Called when round is finished
     * aka a player gets killed
     * @param looser
     */
    public void finishRound(Player looser) {
        Round toRemove = getRoundOfPlayerData(TournamentHandler.loadPlayerData(looser));
        PlayerData winnerData = toRemove.getPlayerData1().getPlayer().equals(looser) ? toRemove.getPlayerData2() : toRemove.getPlayerData1();
        Player winner = toRemove.getPlayerData1().getPlayer().equals(looser) ? toRemove.getPlayerData2().getPlayer() : toRemove.getPlayerData1().getPlayer();

        server.broadcastMessage(String.format(Messages.ROUND_WON, winner.getName(), looser.getName()));
        looser.sendMessage(Messages.LOOSER_MESSAGE);

        actRoundWinners.add(winnerData);
        losers.add(TournamentHandler.loadPlayerData(looser));
        winnerData.wonRound();

        TournamentHandler.playersInGame.remove(winner);
        TournamentHandler.playersInGame.remove(looser);

        // Free arena
        if(toRemove != null) {
            toRemove.getArena().freeArena();
            freeArenas.add(toRemove.getArena());
            rounds.remove(toRemove);
        }

        // Set tournament winner
        if(round == rounds.size() && actRoundWinners.size() == 0) {
            TournamentHandler.loadPlayerData(winner).wonTournament();
            server.broadcastMessage(String.format(Messages.TOURNAMENT_WON, winner.getName()));
            // Start losers tournament
            server.broadcastMessage(String.format(Messages.STARTING_LOSERS_TOURNAMENT));
            orderPairs(losers);
        }
        // Start new layer of battles
        else if(round == rounds.size()) {
            orderPairs(actRoundWinners);
            actRoundWinners.clear();
        }

        TournamentHandler.savePlayerData(winnerData);
        TournamentHandler.savePlayerData(TournamentHandler.getPlayerData(looser));
    }

    public Round getRoundOfPlayerData(PlayerData winnerData) {
        for (Round round : rounds) {
            if (round.getPlayerData1().equals(winnerData) || round.getPlayerData2().equals(winnerData)) {
                return round;
            }
        }

        return null;
    }

    /**
     * Orders partivipators in pairs
     */
    private void orderPairs(List<PlayerData> list) {
        // Order the given list and save it to @{orderedPairs}
        rounds.clear();
        round = 0;
        List<PlayerData> toOrder = new ArrayList<>(list);
        Collections.sort(toOrder);

        for (int i = 0; i < toOrder.size(); i += 2) {
            rounds.add(new Round(round, toOrder.get(i), toOrder.get(i+1)));
        }
    }

    /**
     * Adds participator to tournament
     * @param participator
     * @return bool if the player could be added
     */
    public boolean addParticipator(Player participator) {
        PlayerData data = TournamentHandler.loadPlayerData(participator);
        data.joinedTournament();

        if(freeArenas.size() != 0)
            participator.teleport(freeArenas.get(0).getSpectatorPoint());

        if(participators.contains(data))
            return false;

        participators.add(data);
        return true;
    }

    /**
     * Searches for a free arena and returns it.
     */
    public Arena searchFreeArena() {
        Arena a = null;

        for (int i = 0; i < freeArenas.size(); i++) {
            if(!freeArenas.get(i).isOccupied()) {
                a = freeArenas.get(i);
                break;
            }
        }

        freeArenas.remove(a);
        return a;
    }


    public String getName() {
        return Name;
    }

    public boolean started() {
        return getRound() != -1;
    }

    public boolean hasParticipator(Player sender) {
        for(PlayerData p : participators) {
            if(p.getPlayer().equals(sender))
                return true;
        }

        return false;
    }
}
