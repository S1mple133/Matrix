package me.s1mple.matrix.Tournament;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.ArenaManager.Data.Arena;
import me.s1mple.matrix.Tournament.CommandHandler.TournamentCommandHandler;
import me.s1mple.matrix.Tournament.Data.PlayerData;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.json.JSONObject;

import de.schlichtherle.io.FileWriter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TournamentHandler {
    private static File player_data;
    private static File arena_data;

    private static List<Arena> arenas;

    /**
     * Loads player data to memory
     * if player has no data, empty playerdata is loaded
     */
    public static PlayerData loadPlayerData(Player player) {
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
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(actFile.getPath(), true));
            writer.write(output);
            writer.close();
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
                    arenas.add(new Gson().fromJson(sc.next(), Arena.class));
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
    public static void saveArena(Arena toSave) {
        String output = new Gson().toJson(toSave);
        File actFile = new File(arena_data, toSave.getName() + ".json");

        try{
            if(!actFile.exists())
                actFile.createNewFile();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(actFile.getPath(), true));
            writer.write(output);
            writer.close();
        }
        catch(Exception ex) {
            Matrix.getPlugin().getLogger().info("Could not save arena " + toSave.getName());
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
        loadArenas();
    }

}
