package me.s1mple.matrix.BattlePass;

import Utils.Glow;
import com.hm.achievement.api.AdvancedAchievementsAPI;
import me.s1mple.matrix.BattlePass.Data.Level;
import me.s1mple.matrix.BattlePass.Data.UserData;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import skinsrestorer.bukkit.SkinsRestorer;

import javax.naming.Name;
import javax.swing.*;
import java.util.*;

public class GUIUtil {
    private static Inventory rewardGuiTemplate = getRewardGuiTemplate();

    public static ItemStack getItem(Material material, String name, List<String> lore, int amount) {
        return getItem(material, name, lore, amount, false);
    }

    public static Inventory getBattlePassInv(int page, UserData data) {
        if(1 + ((page-1)*8) > Level.getLevelCount()+1 || page <= 0) {
            return null;
        }

        Inventory inv = Bukkit.createInventory(null, 54, ((data.isPremium()) ? "Premium" : "Free") +" Battlepass");
        HashMap<String, String> achievementsAndDesc = data.getLevel().getAchievements();
        Set<String> achievements = achievementsAndDesc.keySet();

        String skinName = "CrashCringle12";
        skinName = skinName == null ? data.getPlayer().getName() : skinName;
        inv.addItem(generateSkullItem(skinName, data));

        // Add Missions
        int cnt = 0;
        for(String achievement : data.getAchievements()) {
            achievements.remove(achievement);
            inv.setItem(2+(cnt++), getItem(Material.ENCHANTED_BOOK,
                    ChatColor.GREEN + "" + ChatColor.STRIKETHROUGH + BattlePass.getAdvancedAchievementsAPI().get().getDisplayNameForName(achievement),
                    new ArrayList<>(), 1));
        }

        for(String achievement : achievements) {
            inv.setItem(2+(cnt++), getItem(Material.WRITABLE_BOOK,
                    ChatColor.DARK_RED + BattlePass.getAdvancedAchievementsAPI().get().getDisplayNameForName(achievement),
                    Arrays.asList("", ChatColor.RED + achievementsAndDesc.get(achievement)),
                    1));
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
            inv.setItem(cnt++, (data.getLevel().getId() > cnt-19) ?
                    getItem(Material.YELLOW_STAINED_GLASS_PANE,
                    "Level " + actLevel,
                    new ArrayList<String>(), actLevel++, true) :
                    getItem(Material.BLACK_STAINED_GLASS_PANE,
                            "Level " + actLevel,
                            new ArrayList<String>(), actLevel++));
        }

        // Misc
        inv.setItem(27, getItem(Material.IRON_BLOCK,
                "Free Battlepass",
                new ArrayList<String>(), 1));
        inv.setItem(36, getItem(Material.GOLD_BLOCK,
                ChatColor.YELLOW + "" + ChatColor.GOLD + "Premium Battlepass",
                new ArrayList<String>(), 1));
        inv.setItem(53, getItem(Material.SPECTRAL_ARROW,
                "Page " + (page+1),
                new ArrayList<String>(), 1));

        if(page > 1) {
            inv.setItem(45, getItem(Material.SPECTRAL_ARROW,
                    "Page " + (page-1),
                    new ArrayList<String>(), 1));
        }

        return inv;
    }

    private static ItemStack generateSkullItem(String skinName, UserData data) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta(); // no arguments needed
        skullMeta.setOwner(skinName);
        skullMeta.setDisplayName(ChatColor.GOLD + data.getPlayer().getName());
        skullMeta.setLore(Arrays.asList("",
                ChatColor.YELLOW + "Level: " + ChatColor.GOLD + data.getLevel().getId(),
                "",
                ChatColor.YELLOW + "On the right you can see",
                ChatColor.YELLOW + "a list of missions.",
                "",
                ChatColor.GREEN + "" + ChatColor.ITALIC + "" + ChatColor.STRIKETHROUGH + "Completed missions",
                ChatColor.RED + "" + ChatColor.ITALIC + "Remaining missions"));
        skull.setItemMeta(skullMeta);
        return skull;
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

    public static Inventory getRewardGui(Player player) {
        Inventory inv = Bukkit.createInventory(player, 54, Util.color("&0 Level Up! Battle Pass Rewards"));
        createBackgroundRewardGui(inv, UserData.GetUserData(player));
        return inv;
    }

    public static Inventory getRewardGuiTemplate() {
        ItemStack whiteGlass = getItem(Material.WHITE_STAINED_GLASS_PANE, " ", new ArrayList<>(), 1);
        ItemStack blackGlass = getItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>(), 1);
        ItemStack grayGlass = getItem(Material.GRAY_STAINED_GLASS_PANE, " ", new ArrayList<>(), 1);
        Inventory inventory = Bukkit.createInventory(null, 54, Util.color("&0 Level Up! Battle Pass Rewards"));

        int cnt = addLineRewardGui(inventory, whiteGlass, blackGlass, grayGlass, 0);
        for (int i = 0; i < 3; i++) {
            cnt = addLineWithGrayRewardGui(inventory, whiteGlass, blackGlass, grayGlass, cnt);
        }

        cnt = addLineRewardGui(inventory, whiteGlass, blackGlass, grayGlass, cnt);

        while(cnt < 54) {
            inventory.setItem(cnt++, grayGlass.clone());
        }

        return inventory;
    }

    private static void createBackgroundRewardGui(Inventory inventory, UserData userData) {
        // Generate background
        ItemStack[] contents = rewardGuiTemplate.getContents();
        for(int i = 0; i < inventory.getContents().length; i++) {
            inventory.setItem(i, contents[i]);
        }

        // Put base items
        ItemStack goldItem = getItem(Material.GOLD_BLOCK, Util.color("&6&lPremium Battlepass Rewards"),
                new ArrayList<>(), 1);
        ItemStack ironItem = getItem(Material.IRON_BLOCK, Util.color("&fFree Battle Pass"),
                Arrays.asList(), 1);
        ItemStack goldItemMenu;
        ItemStack oakDoor;
        ItemStack netherStar;
        ItemStack sign;
        ItemStack premiumReward;
        ItemStack freeReward = userData.getLevel().getMenuItem();

        if(userData.isPremium()) {
            premiumReward = userData.getLevel().getMenuItemPremium();
            goldItemMenu =  getItem(Material.GOLD_BLOCK, Util.color("&6Premium Battle Pass"),
                    new ArrayList<>(), 1, true);
            oakDoor = getItem(Material.DARK_OAK_DOOR, Util.color("&eClick here to view"),
                    Arrays.asList(Util.color("&eyour &6Premium Battle Pass&e!")), 1);
            netherStar = getItem(Material.NETHER_STAR, Util.color("&e&l&ki&f&l&ki&e&l&ki&e New Level - &6&" + userData.getLevel().getId() + " &e&l&ki&f&l&ki&e&l&ki&e"),
                    new ArrayList<>(), userData.getLevel().getId());
            sign = getItem(Material.BIRCH_SIGN, Util.color("&6Congratulations"),
                    Arrays.asList(Util.color("&eYou have reached level"), Util.color("&6" + userData.getLevel().getId() + " &eand have received"), Util.color("&ethe rewards from your"), Util.color("&6Premium Battle Pass&e!")), 1);
        }
        else {
            premiumReward = getItem(Material.BARRIER, Util.color("&4&lLocked"),
                    new ArrayList<>(), 1);
            goldItemMenu =  getItem(Material.GOLD_BLOCK, Util.color("&6Buy Premium Battle Pass"),
                    Arrays.asList(Util.color("&eYou can click here"), Util.color("&eto go to the store"), Util.color("&eand purchase the"), Util.color("&6Premium Battle Pass")), 1);
            oakDoor = getItem(Material.DARK_OAK_DOOR, Util.color("&7Click here to view"),
                    Arrays.asList(Util.color("&7your &fFree Battle Pass&7!")), 1);
            netherStar = getItem(Material.NETHER_STAR, Util.color("&f&l&ki&7&l&ki&f&l&ki&7 New Level - &f&l" + userData.getLevel().getId() + " &f&l&ki&7&l&ki&f&l&ki&7"),
                    new ArrayList<>(), 1);
            sign = getItem(Material.BIRCH_SIGN, Util.color("&fCongratulations"),
                    Arrays.asList(Util.color("&7You have reached level"), Util.color("&f" + userData.getLevel().getId() + " &7and have received"), Util.color("&7the rewards from your"), Util.color("&fFree Battle Pass&7!")), 1);
        }
        inventory.setItem(12, ironItem);
        inventory.setItem(21, freeReward);
        inventory.setItem(30, ironItem.clone());
        inventory.setItem(14, goldItem.clone());
        inventory.setItem(23, premiumReward);
        inventory.setItem(32, goldItem.clone());
        inventory.setItem(47, goldItemMenu);
        inventory.setItem(49, oakDoor);
        inventory.setItem(51, netherStar);
        inventory.setItem(52, sign);
        inventory.setItem(53, getItem(Material.RED_DYE, Util.color("&cClose"), new ArrayList<>(), 1));
    }

    private static int addLineRewardGui(Inventory inventory, ItemStack whiteGlass, ItemStack blackGlass, ItemStack grayGlass, int cnt) {
        for (int i = 0; i < 2; i++) {
            inventory.setItem(cnt++, whiteGlass.clone());
        }

        for (int i = 0; i < 5; i++) {
            inventory.setItem(cnt++, blackGlass.clone());
        }

        for (int i = 0; i < 2; i++) {
            inventory.setItem(cnt++, whiteGlass.clone());
        }

        return cnt;
    }

    private static int addLineWithGrayRewardGui(Inventory inventory, ItemStack whiteGlass, ItemStack blackGlass, ItemStack grayGlass, int cnt) {
        for (int i = 0; i < 2; i++) {
            inventory.setItem(cnt++, whiteGlass.clone());
        }

        inventory.setItem(cnt++, blackGlass.clone());
        cnt++;
        inventory.setItem(cnt++, grayGlass.clone());
        cnt++;
        inventory.setItem(cnt++, blackGlass.clone());

        for (int i = 0; i < 2; i++) {
            inventory.setItem(cnt++, whiteGlass.clone());
        }

        return cnt;
    }
}

