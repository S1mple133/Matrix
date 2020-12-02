package me.s1mple.matrix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.Node;

public class MatrixMethods {
	static LuckPerms api;
	
	/**
	 * @author CrashCringle
	 * 
	 * @Description This method is a simplified way of removing
	 * Permissions to players via LuckPerms
	 * 
	 * @param player Player who will lose permission
	 * @param permission Permission to remove from the player as a string
	 */
	public static void removePermission(Player player, String permission) {
		api.getUserManager().getUser(player.getUniqueId()).data()
		.remove(Node.builder(permission).build());
		api.getUserManager().saveUser(api.getUserManager().getUser(player.getUniqueId()));

	}
	/**
	 * @author CrashCringle
	 * 
	 * @Description This method is a simplified way of adding 
	 * Permissions to players via LuckPerms
	 * 
	 * @param player Player who will receive permission
	 * @param permission Permission to give to the player as a string
	 */
	public static void addPermission(Player player, String permission) {
		api.getUserManager().getUser(player.getUniqueId()).data()
		.add(Node.builder(permission).build());
		api.getUserManager().saveUser(api.getUserManager().getUser(player.getUniqueId()));

	}
	
	/**
	 * @author CrashCringle
	 * 
	 * @Description Shortened way for sending Console commands
	 * 
	 * @param command The command to send from console as a String
	 */
	public static void ConsoleCmd(String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
}
