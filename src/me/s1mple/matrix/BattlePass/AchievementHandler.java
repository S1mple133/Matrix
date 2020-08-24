package me.s1mple.matrix.BattlePass;

import me.s1mple.matrix.BattlePass.Data.Level;
import me.s1mple.matrix.BattlePass.Data.UserData;
import me.s1mple.matrix.Matrix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.File;
import java.util.ArrayList;

public class AchievementHandler {
    private Matrix plugin;

    public AchievementHandler(Matrix plugin) {
        this.plugin = plugin;

        LoadLevels();
    }

    /**
     * Handles an Achievement
     */
    public boolean HandleAchievement(String achievementName, Player player) {
        UserData playerData = UserData.GetUserData(player);
        if(playerData == null)
            return false;

        if(playerData.addAchievement(achievementName)) {
            int cnt = playerData.getAchievementCount();
            int amount = playerData.getLevel().getAchievements().size();
            player.sendMessage(ChatColor.AQUA + "Congrats! You completeed " + cnt + " out of " + amount + " achievements for your Battlepass Level.");
            if(cnt >= amount) {
                GivePrizeToUser(player, playerData);
                playerData.rankUp();
                player.sendMessage(ChatColor. GREEN + "Congrats! You've completed your battlepass level.");
            }

            return true;
        }

        return false;
    }

    /**
     * Loads Levels into memory
     */
    private void LoadLevels() {
        File customConfigFile = new File(plugin.getDataFolder(), "battlepass.yml");
        if (!customConfigFile.exists()) {
            plugin.saveResource("battlepass.yml", false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(customConfigFile);
        int levels = config.getInt("BattlePass.Lvls");

        Level.addLevel(new Level(new ArrayList<>(), null, null, null, 0, null, null, null, null, null, null, null, new ArrayList<String>()));
        for (int i = 1; i <= levels; i++) {
            String actSection = "Lvl." + i + ".";

            Level.addLevel(new Level(config.getStringList(actSection+"Achievements"),
                    PermissionsEx.getPermissionManager().getGroup(config.getString(actSection+"Rank")),
                    config.getString(actSection+"Command"),
                    config.getString(actSection+"Description"), i,
                    config.getString(actSection+"PremiumCommand"),
                    config.getString(actSection+"Menu.Item"),
                    config.getString(actSection+"Menu.ItemPremium"),
                    config.getString(actSection+"Menu.Name"),
                    config.getString(actSection+"Menu.NamePremium"),
                    config.getString(actSection+"Menu.Lore"),
                    config.getString(actSection+"Menu.LorePremium"),
                    config.getStringList(actSection+"Descriptions")));
        }

        Level.SetNextLevels();
    }

    /**
     * Shows a GUI and gives the user the prize
     * @param d
     */
    private void GivePrizeToUser(Player p, UserData d) {
        Level nextLevel = d.getLevel().getNextLevel();

        if(nextLevel != null) {
            PermissionsEx.getUser(p).removeGroup(d.getLevel().getRank());
            PermissionsEx.getUser(p).addGroup(nextLevel.getRank());
        }

        nextLevel.resetAchievementsForPlayer(p);

        if(d.getLevel().getId() != 0)
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), (d.isPremium()) ?d.getLevel().getPremiumCommand().replace("%user%", p.getName()) : d.getLevel().getCommand().replace("%user%", p.getName()));
    }
}
