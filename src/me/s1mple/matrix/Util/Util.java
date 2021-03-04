package me.s1mple.matrix.Util;

import com.google.gson.JsonParser;
import me.s1mple.matrix.Matrix;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Objects;


public class Util {
    public static JsonParser parser = new JsonParser();

    /**
     * Check if a word or phrase begins with a or an.
     *
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
    static public Matrix getPlugin() {
        return Matrix.plugin;
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static File getSchematicFile(String schemFileName) {
        File schem = (new File("." + File.separator + Matrix.getPlugin().getWorldEditPlugin(), "schematics/" + File.separator + schemFileName + ".schem"));
        if (!schem.exists())
            schem = (new File("." + File.separator + Matrix.getPlugin().getWorldEditPlugin().getDataFolder(), "schematics/" + File.separator + schemFileName + ".schematic"));
        if (!schem.exists())
            schem = (new File("." + File.separator + Matrix.getPlugin().getWorldEditPlugin().getDataFolder(), "schematics/" + File.separator + schemFileName));

        return schem;
    }

    /**
     * Sends GET Request to URL and returns response
     *
     * @param url
     * @return
     */
    public static String getRestJsonString(String url) {
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //optional default is GET
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("X-Key", "NDczNjpvSXM1alppeDlJVkVMMVpQR3FJNm8zVWxWTjFQUHRsTw==");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();

        } catch (IOException e) {
            return "{\"block\":0}";
        }
    }

    public static void isUsingVPN(Player player, VPNUser returnmethod) {
        Thread t = new Thread(() -> {
            returnmethod.check(parser.parse(getRestJsonString("http://v2.api.iphub.info/ip/" + Objects.requireNonNull(player.getAddress()).getHostName())).getAsJsonObject().get("block").getAsInt() == 1);
        });
        t.start();
    }
}