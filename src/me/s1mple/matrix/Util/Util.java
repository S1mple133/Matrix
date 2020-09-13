package me.s1mple.matrix.Util;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.airbending.Suffocate;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.MovementHandler;
import com.projectkorra.projectkorra.waterbending.blood.Bloodbending;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.AbilityManager;
import me.s1mple.matrix.Matrix;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;


public class Util {

    /**
     * Get a normal string from the configuration.
     * @param path The path to the string.
     * @return The string.
     */
    static public String getString(String path) {return ConfigManager.getConfig().getString(path);}

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
     * Get the color of the element of a BendingPlayer.
     * @param player The BendingPlayer.
     * @return The Color.
     */
    static public ChatColor elementColor(BendingPlayer player) {
        return !player.getElements().isEmpty() ? player.getElements().get(1).getColor() : ChatColor.WHITE;
    }

    /**
     * Spawn a cone effect around a player.
     * @param player The player that you want to spawn the cone to.
     */
    static public void coneEffect(final Player player){
        new BukkitRunnable(){
            double phi = 0;
            public void run(){
                phi = phi + Math.PI/8;
                double x, y, z;

                Location location1 = player.getLocation();
                for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){
                    for (double i = 0; i <= 1; i = i + 1){
                        x = 0.4*(2*Math.PI-t)*0.5*Math.cos(t + phi + i*Math.PI);
                        y = 0.5*t;
                        z = 0.4*(2*Math.PI-t)*0.5*Math.sin(t + phi + i*Math.PI);
                        location1.add(x, y, z);
                        MatrixElement.getParticle().display(location1, 1);
                        location1.subtract(x,y,z);
                    }

                }

                if(phi > Math.PI){
                    cancel();
                }
            }
        }.runTaskTimer(getPlugin(), 0, 3);

    }

    /**
     * Create a Helix around a player.
     * @param player The player around whom you want to create a Helix.
     */
    static public void createHelix(Player player) {
        Location loc = player.getLocation();
        int radius = 1;
        for(double y = 0; y <= 50; y+=0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            MatrixElement.getParticle().display(loc.add(x,y,z), 1);
        }}

    /**
     * Set a cooldown for a player.
     * @param ability The ability that you want to set a cooldown to.
     * @param player The player that you want to set a cooldown to.
     */
    static public void setCooldown(String ability, Player player) {BendingPlayer.getBendingPlayer(player).addCooldown(ability, (Integer.valueOf(getConfigManager().getConfig().getString("Abilities.me.s1mple.matrix.Matrix." + ability + ".Cooldown")) * 1000L));;}

    /**
     * Check if the player has a Knight.
     * @param player The player to check.
     * @return True or False
     */
    static public boolean hasKnight(Player player) {return getConfigManager().getConfig().getStringList("Abilities.me.s1mple.matrix.Matrix.Knight.PlayersWithKnight").contains(player.getName());}

    /**
     * Return core abilitiy if it can be bent (null if it can't be bent)
     * @param player
     * @return
     */
    static public CoreAbility getCoreAbility(Player player) throws NullPointerException {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if(bPlayer.getBoundAbility() == null) {
            return null;
        }

        CoreAbility ability = bPlayer.getBoundAbility();

        if(!bPlayer.canBend(ability)) {
            return null;
        }

        if(GeneralMethods.isRegionProtectedFromBuild(player, ability.getName(), player.getLocation())) {
            return null;
        }

        if(bPlayer == null) {
            return null;
        }

        if(Suffocate.isBreathbent(player) || bPlayer.isChiBlocked()) {
            return null;
        }

        if(Bloodbending.isBloodbent(player) || MovementHandler.isStopped(player)) {
            return null;
        }

        if(ability == null) {
            return null;
        }

        if(!bPlayer.canBendIgnoreCooldowns(ability) && bPlayer.isOnCooldown(ability)) {
            return null;
        }

        return ability;
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

    /**
     * @return me.s1mple.matrix.AbilityManager
     */
    static public AbilityManager getAbilityManager() { return Matrix.abilityManager;}

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static File getSchematicFile(String schemFileName) {
        File schem = (new File("." + File.separator + Matrix.getPlugin().getWorldEditPlugin(), "schematics/" + File.separator + schemFileName + ".schem"));
        if(!schem.exists())
            schem =  (new File("." + File.separator +Matrix.getPlugin().getWorldEditPlugin().getDataFolder(), "schematics/" + File.separator + schemFileName + ".schematic"));
        if(!schem.exists())
            schem =  (new File("." + File.separator +Matrix.getPlugin().getWorldEditPlugin().getDataFolder(), "schematics/" + File.separator + schemFileName ));

        return schem;
    }
}