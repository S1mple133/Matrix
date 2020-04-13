package me.s1mple.matrix.BattlePass.command;

import me.s1mple.matrix.BattlePass.Data.UserData;
import me.s1mple.matrix.BattlePass.GUIUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Battlepass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("battlepass")) {
            if(sender instanceof Player) {
                ((Player) sender).openInventory(GUIUtil.getBattlePassInv(1, UserData.GetUserData((Player)sender)));
            }
            else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            }
        }
        return false;
    }
}
