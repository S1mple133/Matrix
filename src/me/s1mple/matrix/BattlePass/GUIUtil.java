package me.s1mple.matrix.BattlePass;

import com.hm.achievement.AdvancedAchievements;
import com.hm.achievement.db.AbstractDatabaseManager;
import me.s1mple.matrix.BattlePass.Data.Level;
import me.s1mple.matrix.BattlePass.Data.UserData;
import me.s1mple.matrix.Matrix;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIUtil {

    public static ItemStack getItem(Material material, String name, List<String> lore, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory getBattlePassInv(int page, UserData data) {
        if(1 + ((page-1)*8) > Level.getLevelCount() || page <= 0) {
            return null;
        }
        Inventory inv = Bukkit.createInventory(null, 54, "Battlepass " +page);
        List<String> lore = new ArrayList<String>();
        lore.add(data.getLevel().getDescription());
        List<String> achievements = data.getLevel().getAchievements();

        inv.addItem(getItem(Material.EXPERIENCE_BOTTLE, "Level " + data.getLevel().getId(), lore, data.getLevel().getId()));

        // Add stats
        int cnt = 0;
        for(String achievement : data.getAchievements()) {
            achievements.remove(achievement);
            inv.setItem(2+(cnt++), getItem(Material.YELLOW_STAINED_GLASS_PANE,
                    BattlePass.getAdvancedAchievementsAPI().get().getDisplayNameForName(achievement),
                    new ArrayList<String>(), 1));
        }

        for(String achievement : achievements) {
            inv.setItem(2+(cnt++), getItem(Material.BLACK_STAINED_GLASS_PANE,
                    BattlePass.getAdvancedAchievementsAPI().get().getDisplayNameForName(achievement),
                    new ArrayList<String>(), 1));
        }

        // Reward preview
        int actLevel = 1 + ((page-1)*8);
        cnt = 19;
        while(cnt < 27) {
            if(Level.getLevel(actLevel) == null)
                break;
            Level level = Level.getLevel(actLevel);

            inv.setItem(cnt+9, level.getMenuItem());
            inv.setItem(cnt+18, level.getMenuItemPremium());
            inv.setItem(cnt++, getItem(Material.YELLOW_STAINED_GLASS_PANE,
                    "Level " + actLevel,
                    new ArrayList<String>(), actLevel++));
        }

        inv.setItem(27, getItem(Material.IRON_BLOCK,
                "Battlepass",
                new ArrayList<String>(), 1));
        inv.setItem(36, getItem(Material.GOLD_BLOCK,
                "Premium Battlepass",
                new ArrayList<String>(), 1));
        inv.setItem(45, getItem(Material.ARROW,
                "Previous",
                new ArrayList<String>(), 1));
        inv.setItem(53, getItem(Material.ARROW,
                "Next",
                new ArrayList<String>(), 1));

        return inv;
    }
}
