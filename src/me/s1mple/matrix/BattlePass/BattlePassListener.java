package me.s1mple.matrix.BattlePass;

import com.hm.achievement.utils.PlayerAdvancedAchievementEvent;
import fr.xephi.authme.events.LoginEvent;
import me.s1mple.matrix.BattlePass.Data.UserData;
import me.s1mple.matrix.Matrix;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class BattlePassListener implements Listener {
    private Matrix plugin;

    public BattlePassListener(Matrix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        UserData.LoadUser(event.getPlayer());
    }

    @EventHandler
    public void onLogin(PlayerQuitEvent event) {
        UserData.SaveUser(event.getPlayer());
    }

    @EventHandler
    public void onPrize(PlayerAdvancedAchievementEvent event) {
        BattlePass.getAchievementHandler().HandleAchievement(event.getName(), event.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().contains("Battlepass"))
            return;

        if(event.getCurrentItem() != null && event.getCurrentItem().getType().equals(Material.ARROW)) {
            int page = Integer.valueOf(event.getView().getTitle().split(" ")[1]);
            Inventory inv = GUIUtil.getBattlePassInv(page, UserData.GetUserData((Player)event.getWhoClicked()));

            if(inv != null) {
                event.getWhoClicked().closeInventory();
                event.setCancelled(true);
                event.getWhoClicked().openInventory(inv);
            }

        }

        event.setCancelled(true);
    }
}
