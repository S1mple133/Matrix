package me.s1mple.matrix.BattlePass.Data;

import com.hm.achievement.api.AdvancedAchievementsAPI;
import com.hm.achievement.category.Category;
import com.hm.achievement.db.data.DBAchievement;
import me.s1mple.matrix.BattlePass.GUIUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Level {
    private static List<Level> levels = new ArrayList<Level>();

    private Level nextLevel;
    private HashMap<String, String> achievements;
    private List<String> achievementTypes;
    private PermissionGroup rank;
    private String command;
    private int id;
    private String premiumCommand;
    private ItemStack menuItem;
    private ItemStack menuItemPremium;

    public Level(List<String> achievements,
                 PermissionGroup rank, String command,String description, int id,
                 String premiumCommand, String menuItem, String menuItemPremium,
                 String menuName, String menuNamePremium, String menuLore, String menuLorePremium,
                 List<String> descriptions) {
        this.achievements = new HashMap<>();
        this.achievementTypes = new ArrayList<>();

        for(int i = 0; i < achievements.size(); i++) {
            String[] achievementData = achievements.get(i).split(";");
            this.achievements.put(achievementData[0], descriptions.get(i));
            this.achievementTypes.add(achievementData[1]);
        }

        this.rank = rank;
        this.command = command;
        this.id = id;
        this.premiumCommand = premiumCommand;
        if(menuName != null) {
            this.menuItemPremium = GUIUtil.getItem(Material.valueOf(menuItemPremium),
                    ChatColor.translateAlternateColorCodes('&', menuNamePremium),
                    Arrays.asList(ChatColor.translateAlternateColorCodes('&', menuLorePremium).split("\n")),
                    1);
            this.menuItem = GUIUtil.getItem(Material.valueOf(menuItem),
                    ChatColor.translateAlternateColorCodes('&', menuName),
                    Arrays.asList(ChatColor.translateAlternateColorCodes('&', menuLore).split("\n")),
                    1);
        }
    }

    public static void SetNextLevels() {
        for (Level level : levels) {
            level.nextLevel = Level.getLevel(level.id +1);
        }
    }

    public static int getLevelCount() {
        return levels.size();
    }

    /**
     * Returns the next level
     * @return
     */
    public Level getNextLevel() {
        return nextLevel;
    }

    public int getId() {
        return id;
    }

    /**
     * - returns the achievements needed to be completed
     * @return
     */
    public HashMap<String, String> getAchievements() {
        if(nextLevel == null)
            return new HashMap<>();

        return new HashMap<>(nextLevel.achievements);
    }

    /**
     * Returns the rank
     * @return
     */
    public PermissionGroup getRank() {
        return rank;
    }

    /**
     * Gets level by id
     * @param id
     * @return
     */
    public static Level getLevel(int id) {
        for (Level actLevel : levels) {
            if(actLevel.id == id)
                return actLevel;
        }

        return null;
    }

    /**
     * Adds new level to the level list
     * @param level
     */
    public static void addLevel(Level level) {
        levels.add(level);
    }

    public String getCommand() {
        return command;
    }

    public String getPremiumCommand() {
        return premiumCommand;
    }

    public ItemStack getMenuItem() {
        return menuItem;
    }

    public ItemStack getMenuItemPremium() {
        return menuItemPremium;
    }

    public void resetAchievementsForPlayer(Player p) {
        if(nextLevel == null)
            return;

        for(String toReset : nextLevel.achievementTypes) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "aach reset " + toReset + " " + p.getName());
        }

        for(String toReset : nextLevel.achievements.keySet()) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "aach delete " + toReset + " " + p.getName());
        }
    }
}
