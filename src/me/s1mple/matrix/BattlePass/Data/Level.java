package me.s1mple.matrix.BattlePass.Data;

import com.hm.achievement.api.AdvancedAchievementsAPI;
import com.hm.achievement.category.Category;
import com.hm.achievement.db.data.DBAchievement;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionGroup;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private static List<Level> levels = new ArrayList<Level>();

    private Level nextLevel;
    private List<String> achievements;
    private PermissionGroup rank;
    private String command;
    private String description;
    private int id;

    public Level(List<String> achievements,
                 PermissionGroup rank, String command, String description, int id) {
        this.achievements = achievements;
        this.rank = rank;
        this.command = command;
        this.description = description;
        this.id = id;
    }

    public static void SetNextLevels() {
        for (Level level : levels) {
            level.nextLevel = Level.getLevel(level.id +1);
        }
    }


    /**
     * Returns the prize
     * @return
     */
    public String getDescription() {
        return description;
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
    public List<String> getAchievements() {
        return achievements;
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
}
