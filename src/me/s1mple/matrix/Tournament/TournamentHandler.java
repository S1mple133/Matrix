package me.s1mple.matrix.Tournament;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Tournament.CommandHandler.TournamentCommandHandler;
import me.s1mple.matrix.Tournament.Data.*;

import net.minecraft.server.v1_16_R1.Items;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TournamentHandler {
    private static File player_data;
    private static File arena_data;

    private static List<Arena> arenas;
    private static TreeSet<PlayerData> playerDatas;
    private static List<Tournament> tournaments;

    private static HashMap<Player, Arena> arenasInCreation;

    public static HashMap<Player, Tournament> playersInGame;

    /**
     * Loads player data to memory
     * if player has no data, empty playerdata is loaded
     */
    public static PlayerData loadPlayerData(Player player) {
        for(PlayerData actdata : playerDatas)
            if(actdata.getPlayer().equals(player))
                return actdata;

        File actFile = new File(player_data, player.getUniqueId().toString() + ".json");

        if(!actFile.exists())
            return new PlayerData(player, 0, 0, 0, 0);

        try {
            Scanner sc = new Scanner(actFile);
            sc.useDelimiter("\\Z");
            PlayerData data = new Gson().fromJson(sc.next(), PlayerData.class);
            sc.close();
            return data;
        } catch(Exception ex) {}

        return new PlayerData(player, 0, 0, 0, 0);
    }

    public static List<Arena> getArenas() { return arenas; }

    public static boolean existsTournament(String t) {
        return getTournament(t) != null;
    }

    public static List<PlayerData> getPlayerDatas() { return new ArrayList<>(playerDatas); }

    public static PlayerData getPlayerData(Player p) {
        for (PlayerData data : playerDatas) {
            if(data.getPlayer().equals(p)) {
                return data;
            }
        }

        return loadPlayerData(p);
    }

    public static void createTournament(String name, List<Arena> arenas) {
        for (Arena a : arenas) {
            a.reserve();
        }
        tournaments.add(new Tournament(name, arenas));
    }

    /**
     * Save player data to disk
     * @param data
     */
    public static void savePlayerData(PlayerData data) {
        String output = new Gson().toJson(data);
        File actFile = new File(player_data, data.getPlayer().getUniqueId().toString() + ".json");

        try{
            if(!actFile.exists())
                actFile.createNewFile();

            Files.write(actFile.toPath(), output.getBytes());
        }
        catch(Exception ex) {
            Matrix.getPlugin().getLogger().info("Could not save tournament Player data of " + data.getPlayer().getName());
        }
    }

    /**
     * Loads arenas from disk
    */
    public static void loadArenas() {
        for(File f : arena_data.listFiles()) {
            if(!f.isDirectory()) {
                try {
                    Scanner sc = new Scanner(f);
                    sc.useDelimiter("\\Z");
                    arenas.add(Arena.fromSaveArena(new Gson().fromJson(sc.next(), SaveArena.class)));
                    sc.close();
                } catch(Exception ex) {
                    Matrix.getPlugin().getLogger().info("Could not load " + f.getName());
                }
            }
        }
    }

    /**
     * Saves arena to disk
     * @param toSave
     */
    public static boolean saveArena(Arena toSave, Player saver) {
        if(toSave.getSpawnPoint1() == null || toSave.getSpawnPoint2() == null || toSave.getSpectatorPoint() == null) {
            return false;
        }

        String output = new Gson().toJson(Arena.toSaveArena(toSave));
        File actFile = new File(arena_data, toSave.getName() + ".json");

        try{
            if(!actFile.exists())
                actFile.createNewFile();

            Files.write(actFile.toPath(), output.getBytes());
            arenas.add(toSave);
            arenasInCreation.remove(saver);
            return true;
        }
        catch(Exception ex) {
            Matrix.getPlugin().getLogger().info("Could not save arena " + toSave.getName());
            return false;
        }
    }

    /**
     * Called when the plugin is loaded
     */
    public static void init(Matrix plugin) {
        plugin.getCommand("tournament").setExecutor(new TournamentCommandHandler());

        player_data = new File(Matrix.getPlugin().getDataFolder(), "tournaments");

        if(!player_data.exists())
            player_data.mkdir();

        arena_data = new File(player_data, "arena_data");
        player_data = new File(player_data, "player_data");

        if(!player_data.exists())
            player_data.mkdir();

        if(!arena_data.exists())
            arena_data.mkdir();

        arenas = new ArrayList<>();
        tournaments = new ArrayList<>();
        playerDatas = new TreeSet<>();
        playersInGame = new HashMap<>();
        arenasInCreation = new HashMap<>();
        loadArenas();
    }

    public static Arena getArenaOfPlayerInCreation(Player p) {
        return arenasInCreation.containsKey(p) ? arenasInCreation.get(p) : null;
    }

    public static void createArenaInCreation(Player p, Arena a) { arenasInCreation.put(p, a); }

    public static List<Tournament> getTournaments() {
        return tournaments;
    }

    public static void removeTournament(Tournament toStop) {
        tournaments.remove(toStop);
    }

    public static Tournament getTournament(String arg) {
        for(Tournament t : tournaments) {
            if(t.getName().equalsIgnoreCase(arg))
                return t;
        }

        return null;
    }

    /**
     * When a participator does /tt leave
     * @param participator
     */
    public static void removeParticipatorFromTournament(Player participator) {
        PlayerData pd = TournamentHandler.loadPlayerData(participator);
        pd.leftTournament();
        Round round;

        for(Tournament t : TournamentHandler.getTournaments()) {
            round = t.getRoundOfPlayerData(pd);

            if(round != null) {
                t.finishRound(participator);
                break;
            }
        }
    }

    public static Arena getArena(String arg) {
        for(Arena ar : arenas) {
            if(ar.getName().equals(arg))
                return ar;
        }

        return null;
    }

    public static Tournament getTournamentOfPlayer(Player sender) {
        for (Tournament t : tournaments) {
            if(t.hasParticipator(sender))
                return t;
        }

        return null;
    }
}
