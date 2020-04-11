package me.s1mple.matrix.BattlePass;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.listener.AbilityListener;

public class BattlePass {
    private static Matrix plugin;
    private static AchievementHandler achievementHandler;

    public static void LoadBattlePass(Matrix matrixPlugin) {
        plugin = matrixPlugin;
        plugin.getServer().getPluginManager().registerEvents(new BattlePassListener(plugin), Matrix.plugin);
        achievementHandler = new AchievementHandler(plugin);
    }


    public static AchievementHandler getAchievementHandler() {
        return achievementHandler;
    }
}
