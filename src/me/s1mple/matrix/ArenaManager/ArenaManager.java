package me.s1mple.matrix.ArenaManager;

import me.s1mple.matrix.ArenaManager.Commands.AMCommand;
import me.s1mple.matrix.ArenaManager.Data.Arena;
import me.s1mple.matrix.Matrix;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenaManager {
    private static Matrix plugin;
    private static FileConfiguration config;
    private static File configFile;

    public static void init(Matrix matrix) {
        plugin = matrix;

        plugin.getCommand("arenamanager").setExecutor(new AMCommand(plugin));
        LoadArenas();
    }

    /**
     * Loads Arenas from the config
     */
    private static void LoadArenas() {
        configFile = new File(plugin.getDataFolder(), "arenamanager.yml");
        if (!configFile.exists()) {
            plugin.saveResource("arenamanager.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        try {
            int cnt = 0;
            while(true) {
                Arena.addArena(new Arena(config.getString("Arenas."+cnt+".Name"), config.getString("Arenas."+cnt+".Schematic"),
                        new Location(plugin.getServer().getWorld(config.getString("Arenas."+cnt+".World")), config.getDouble("Arenas."+cnt+".X"), config.getDouble("Arenas."+cnt+".Y"), config.getDouble("Arenas."+cnt+".Z"))));
                cnt++;
            }
        } catch (Exception ex) {
            plugin.getLogger().info("Loaded Arenas!");
        }
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
