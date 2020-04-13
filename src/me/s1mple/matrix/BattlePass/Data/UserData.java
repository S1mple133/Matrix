package me.s1mple.matrix.BattlePass.Data;

import com.hm.achievement.api.AdvancedAchievementsAPI;
import com.hm.achievement.api.AdvancedAchievementsAPIFetcher;
import me.s1mple.matrix.BattlePass.BattlePass;
import me.s1mple.matrix.Matrix;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserData {
    private static List<UserData> userDataList = new ArrayList<UserData>();

    private List<String> achievementsCompletedAtActualLevel;
    private Level actLevel;
    private String uuid;
    private boolean isPremium;

    public UserData(List<String> achievementsCompleted, Level actLevel, String uuid, boolean isPremium) {
        if(achievementsCompleted.contains("full") && actLevel.getNextLevel() != null) {
            achievementsCompletedAtActualLevel = completedAtLevel(actLevel.getNextLevel(), Matrix.getPlugin().getServer().getPlayer(uuid));
            this.actLevel = actLevel.getNextLevel();
        }
        else {
            this.actLevel = actLevel;
            this.achievementsCompletedAtActualLevel = achievementsCompleted;
        }
        this.isPremium = isPremium;
        this.uuid = uuid;
    }

    public Level getLevel() {
        return actLevel;
    }

    public String getUUID() {
        return uuid;
    }

    public int getAchievementCount() {
        return achievementsCompletedAtActualLevel.size();
    }

    public static List<String> completedAtLevel(Level level, Player player) {
        List<String> levelAchievements = level.getAchievements();
        List<String> returnVal = new ArrayList<>();
        UUID name = player.getUniqueId();

        for (String achievement : levelAchievements) {
            if(BattlePass.getAdvancedAchievementsAPI().get().hasPlayerReceivedAchievement(name, achievement)) {
                returnVal.add(achievement);
            }
        }

        return  returnVal;
    }

    public static List<String> achievementListFromString(String data) {
        List<String> returnVal = new ArrayList<>();

        if(data == "none")
            return  returnVal;

        String[] split = data.split(",");

        for (String actData : split) {
            returnVal.add(actData);
        }

        return returnVal;
    }

    public static String achievementListToString(List<String> data) {
        StringBuilder returnVal = new StringBuilder();

        if(data.size() == 0) {
            return "none";
        }

        for (int i = 0; i < data.size(); i++) {
            if(!data.get(i).isEmpty()) {
                returnVal.append(data.get(i));

                if(i != data.size()-1) {
                    returnVal.append(",");
                }
            }
        }

        return returnVal.toString();
    }

    /**
     * loads user from database (on join)
     * @param player
     */
    public static void LoadUser(Player player) {
        try {
            Connection db = Matrix.getPlugin().getDbManager().getDatabase();
            PreparedStatement preparedStatement = db.prepareStatement("SELECT * from users WHERE UUID='"+player.getName() + "'");
            ResultSet rs = preparedStatement.executeQuery();

            UserData actUserData;

            if(!rs.next()) {
                PermissionsEx.getUser(player).addGroup(Level.getLevel(1).getRank());
                actUserData = new UserData(completedAtLevel(Level.getLevel(1), player), Level.getLevel(1), player.getName().toLowerCase(), false);
            }
            else {
                actUserData = new UserData(achievementListFromString(rs.getString(4)), Level.getLevel(rs.getInt(2)), rs.getString(3), rs.getBoolean(5));
            }

            userDataList.add(actUserData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves player to the database
     * @param player
     */
    public static void SaveUser(Player player) {
        try {
            Connection db = Matrix.getPlugin().getDbManager().getDatabase();
            PreparedStatement preparedStatement = db.prepareStatement("SELECT * from users WHERE UUID='"+player.getName().toLowerCase() + "'");
            ResultSet rs = preparedStatement.executeQuery();

            UserData actUserData = GetUserData(player);

            if(actUserData == null)
                return;

            if(!rs.next()) {
                preparedStatement = db.prepareStatement("INSERT INTO users(uuid, level, achievements, premium) VALUES(?, ?, ?, ?)");
                preparedStatement.setString(1, actUserData.uuid);
                preparedStatement.setInt(2, actUserData.actLevel.getId());
                preparedStatement.setString(3, achievementListToString(actUserData.achievementsCompletedAtActualLevel));
                preparedStatement.setBoolean(4, actUserData.isPremium);

                preparedStatement.executeUpdate();
            }
            else {
                preparedStatement = db.prepareStatement("UPDATE users SET level = ?, achievements = ?, premium = ? WHERE uuid=?");

                preparedStatement.setInt(1, actUserData.actLevel.getId());
                preparedStatement.setString(2, achievementListToString(actUserData.achievementsCompletedAtActualLevel));
                preparedStatement.setBoolean(3, actUserData.isPremium);

                preparedStatement.setString(4, actUserData.uuid);

                preparedStatement.executeUpdate();            }

            userDataList.add(actUserData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAchievements() {
        List<String> ach = new ArrayList<>();
        ach.addAll(achievementsCompletedAtActualLevel);
        return ach;
    }

    public boolean isPremium() {
        return isPremium;
    }

    /**
     * Returns UserData object of user
     * @param user
     */
    public static UserData GetUserData(Player user) {
        for(UserData actUserData : userDataList) {
            if(actUserData.uuid.equalsIgnoreCase(user.getName())) {
                return actUserData;
            }
        }

        return null;
    }

    /**
     * Adds achievement to player
     * @param achievementName
     * @return True if the achievement has been added
     */
    public boolean addAchievement(String achievementName) {
        if(actLevel.getAchievements().contains(achievementName)) {
            achievementsCompletedAtActualLevel.add(achievementName);
            return true;
        }
        return false;
    }

    public void rankUp() {
        achievementsCompletedAtActualLevel.clear();
        if(actLevel.getNextLevel() != null) {
            actLevel = actLevel.getNextLevel();
        }
        else {
            achievementsCompletedAtActualLevel.add("full");
        }
    }
}
