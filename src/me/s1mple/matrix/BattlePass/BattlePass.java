package me.s1mple.matrix.BattlePass;

import com.hm.achievement.api.AdvancedAchievementsAPI;
import com.hm.achievement.api.AdvancedAchievementsAPIFetcher;
import me.s1mple.matrix.BattlePass.command.Battlepass;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.listener.AbilityListener;

import java.util.Optional;

public class BattlePass {
    private static Matrix plugin;
    private static AchievementHandler achievementHandler;

    private static Optional<AdvancedAchievementsAPI> advancedAchievementsAPI;


    public static void LoadBattlePass(Matrix matrixPlugin) {
        plugin = matrixPlugin;
        advancedAchievementsAPI = AdvancedAchievementsAPIFetcher.fetchInstance();
        plugin.getServer().getPluginManager().registerEvents(new BattlePassListener(plugin), Matrix.plugin);
        achievementHandler = new AchievementHandler(plugin);

        plugin.getCommand("battlepass").setExecutor(new Battlepass());
    }


    public static AchievementHandler getAchievementHandler() {
        return achievementHandler;
    }

    public static Optional<AdvancedAchievementsAPI> getAdvancedAchievementsAPI() {
        return advancedAchievementsAPI;
    }
}
