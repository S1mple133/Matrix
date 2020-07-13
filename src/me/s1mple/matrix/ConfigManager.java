package me.s1mple.matrix;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.Config;
import me.s1mple.matrix.Matrix;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigManager {
    private final Matrix plugin;
    private final ProjectKorra pk;
    private final FileConfiguration config;
    private final File configFile;

    public ConfigManager(Matrix plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "bending.yml");
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
        this.pk = ProjectKorra.plugin;

        createDefaultConfigFile();
        //changePKConfig();
    }

    /**
     * Return the config file
     * @return
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Save the config file
     */
    public void saveConfig() {
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            plugin.getLogger().info("CONFIGURATION COULD NOT BE SAVED !");
        }
    }

    /**
     * Reload the config without resetting it
     */
    public void reloadConfig(JavaPlugin plugin) {
        InputStream configStream = plugin.getResource("bending.yml");

        if(configStream != null) {
            config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, StandardCharsets.UTF_8)));
        }
    }


    /**
     * Create bending.yml file
     */
    private void createDefaultConfigFile() {
        config.options().copyDefaults(true);

        config.addDefault("Color", "YELLOW");
        config.addDefault("Prefix", "[MatrixBender]");
        config.addDefault("Abilities.HeartRegen.Instructions", "Left-Click to regenerate 2 HP.");
        config.addDefault("Abilities.HeartRegen.Enabled", true);
        config.addDefault("Abilities.HeartRegen.Description", "HeartRegen is a powerful move for the MatrixBender. It regenerates 2 Hearts. It can be used one time per life, which means that if you die you can use the ability again.");
        config.addDefault("Abilities.HeartRegen.Cooldown", 40);
        config.addDefault("Abilities.PhotonicBoom.Enabled", true);
        config.addDefault("Abilities.PhotonicBoom.Cooldown", 99);
        config.addDefault("Abilities.PhotonicBoom.SpectatorCooldown", 10);
        config.addDefault("Abilities.Escape.Instructions", "Shift and left click to blind everybody in a radius of 10 blocks and teleport yourself away.");
        config.addDefault("Abilities.Escape.Description", "Escape is MatrixBender's last hope move. Teleport yourself away and blind your enemies for 5 seconds to escape.");
        config.addDefault("Abilities.Escape.Enabled", true);
        config.addDefault("Abilities.Escape.Cooldown", 180);
        config.addDefault("Abilities.Escape.Radius", 20);
        config.addDefault("Abilities.Knight.Instructions", "Left click on a Player to summon the Knight");
        config.addDefault("Abilities.Knight.Description", "You can summon the almighty knight by left clicking a player. It will help you kill your enemies. If it happens for you to be in AvatarState or MatrixState while summoning the knight, it will grow significantly in size. But beware! If the Knight killed its opponent and it doesn't like you, it will begin attacking you!");
        config.addDefault("Abilities.Knight.TargetPlayerRadius", 20);
        config.addDefault("Abilities.Knight.Enabled", true);
        config.addDefault("Abilities.Knight.KillKnight", 60);
        config.addDefault("Abilities.Knight.Range", 20);
        config.addDefault("Abilities.Knight.Cooldown", 600);
        config.addDefault("Abilities.MatrixBlast.Instructions", "Left click whilst looking at a player to launch a blast of wisdom in thier face.");
        config.addDefault("Abilities.MatrixBlast.Description", "With the knowledge gathered throughout the millions of lifetimes spent on Earth, you are able to direct a beam of concentraded wisdom that will damage other players' capacity to function upon impact.");
        config.addDefault("Abilities.MatrixBlast.Range", 20);
        config.addDefault("Abilities.MatrixBlast.Enabled", true);
        config.addDefault("Abilities.MatrixBlast.Cooldown", 3);
        config.addDefault("Abilities.MatrixBlast.Damage", 2);
        config.addDefault("Abilities.MatrixState.Instructions", "Left clicking will grant you superior knowledge of everyone and everything.");
        config.addDefault("Abilities.MatrixState.Description", "MatrixState will allow you to see the world as it really is; Without Limmitations; With only Flow~.");
        config.addDefault("Abilities.MatrixState.TargetPlayerRadius", 20);
        config.addDefault("Abilities.MatrixState.Enabled", true);
        config.addDefault("Abilities.MatrixState.Cooldown", 3);
        config.addDefault("Abilities.MatrixState.EffectDuration", 200);
        config.addDefault("Abilities.MatrixState.EffectList", Arrays.asList("HEAL","JUMP"));
        config.addDefault("Abilities.MatrixPassive.Instructions", "Shifting will grant you the vision of your ex lifes on this world.");
        config.addDefault("Abilities.MatrixPassive.Description", "This passive protects you from the demons (damage) makes you luckier and grants you an other view of this world.");
        config.addDefault("Abilities.MatrixPassive.Enabled", true);
        saveConfig();
    }

    /**
     * Change ProjectKorra config
     */
    /*private void changePKConfig() {
        Config pConf = com.projectkorra.projectkorra.configuration.ConfigManager.languageConfig;
        FileConfiguration pkConf = pConf.get();
        String bendingColor = pkConf.getString("Chat.Colors.Matrix");
        String bendingPrefix = pkConf.getString("Chat.Prefixes.Matrix");
        String matrixBendingColor = getBendingColor();
        String matrixBendingPrefix = getBendingPrefix();

        if(((bendingColor.length() > 0) && (bendingPrefix.length() > 0) && (bendingColor.equals(matrixBendingColor) && bendingPrefix.equals(matrixBendingPrefix)))) {
                pkConf.options().copyDefaults(true);
                pkConf.addDefault("Chat.Colors.Matrix", matrixBendingColor);
                pkConf.addDefault("Chat.Prefixes.Matrix", matrixBendingPrefix);
                pConf.save();
                pConf.reload();
        }
    }
    */

    /**
     * @return Chat Color of a MatrixBender
     */
    public String getBendingColor() {
        return config.getString("Matrix.Color");
    }

    /**
     * @return Prefix of a MatrixBender
     */
    public String getBendingPrefix() {
        return  config.getString("Matrix.Prefix");
    }


}
