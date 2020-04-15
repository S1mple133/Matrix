package me.s1mple.matrix.BattlePass;

import Utils.Glow;
import com.hm.achievement.AdvancedAchievements;
import com.hm.achievement.db.AbstractDatabaseManager;
import me.s1mple.matrix.BattlePass.Data.Level;
import me.s1mple.matrix.BattlePass.Data.UserData;
import me.s1mple.matrix.Matrix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIUtil {

    public static ItemStack getItem(Material material, String name, List<String> lore, int amount) {
        return getItem(material, name, lore, amount, false);
    }

    public static Inventory getBattlePassInv(int page, UserData data) {
        if(1 + ((page-1)*8) > Level.getLevelCount()+1 || page <= 0) {
            return null;
        }
        Inventory inv = Bukkit.createInventory(null, 54, "Battlepass " + page + " Lvl " + data.getLevel().getId());
        List<String> achievements = data.getLevel().getAchievements();

        inv.addItem(getItem(Material.NETHER_STAR, "Missions", new ArrayList<>(), data.getLevel().getId()));

        // Add stats
        int cnt = 0;
        for(String achievement : data.getAchievements()) {
            achievements.remove(achievement);
            inv.setItem(2+(cnt++), getItem(Material.ENCHANTED_BOOK,
                    ChatColor.STRIKETHROUGH + BattlePass.getAdvancedAchievementsAPI().get().getDisplayNameForName(achievement),
                    new ArrayList<String>(), 1));
        }

        for(String achievement : achievements) {
            inv.setItem(2+(cnt++), getItem(Material.WRITABLE_BOOK,
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
                    new ArrayList<String>(), actLevel++, true));
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

    private static ItemStack getItem(Material material, String name, List<String> lore, int amount, boolean glow) {
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();

        if(glow)
            meta.addEnchant(new Glow(new NamespacedKey( Matrix.plugin, "glow_ench")), 1, true);

        meta.setLore(lore);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}

