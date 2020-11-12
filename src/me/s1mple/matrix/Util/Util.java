package me.s1mple.matrix.Util;

import me.s1mple.matrix.ConfigManager;
import me.s1mple.matrix.Matrix;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;


public class Util {

    /**
     * Check if a word or phrase begins with a or an.
     * @param string The String which contains the word or Phrase.
     * @return a or an
     */
    static public String aAn(String string) {
        String list = string.split("")[0];
        return list.equalsIgnoreCase("a") || list.equalsIgnoreCase("e") ? "an" : "a";
    }

    /**
     * @return Config Manager
     */
    static public me.s1mple.matrix.ConfigManager getConfigManager() {
        return Matrix.configManager;
    }

    /**
     * @return Matrix plugin
     */
    static public Matrix getPlugin() { return Matrix.plugin;}

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static File getSchematicFile(String schemFileName) {
        File schem = (new File("." + File.separator + Matrix.getPlugin().getWorldEditPlugin().getDataFolder(), "schematics/" + File.separator + schemFileName + ".schem"));
        if(!schem.exists())
            schem =  (new File("." + File.separator +Matrix.getPlugin().getWorldEditPlugin().getDataFolder(), "schematics/" + File.separator + schemFileName + ".schematic"));
        if(!schem.exists())
            schem =  (new File("." + File.separator +Matrix.getPlugin().getWorldEditPlugin().getDataFolder(), "schematics/" + File.separator + schemFileName ));

        return schem;
    }
}