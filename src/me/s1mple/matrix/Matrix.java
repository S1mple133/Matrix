package me.s1mple.matrix;

import com.clanjhoo.vampire.VampireRevamp;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.libraryaddict.disguise.LibsDisguises;
import me.s1mple.matrix.ArenaManager.ArenaManager;
import me.s1mple.matrix.Listener.PermsListener;
import me.s1mple.matrix.Listener.SkillsListener;
import me.s1mple.matrix.Listener.VPNListener;
import me.s1mple.matrix.Raid.RaidListener;
import me.s1mple.matrix.Skills.Werewolf;
import me.s1mple.matrix.Tournament.TournamentHandler;
import me.s1mple.matrix.Util.Glow;
import me.s1mple.matrix.Util.Util;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.skills.main.SkillsPro;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;
import com.clanjhoo.vampire.VampireRevamp;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.massivecraft.factions.Factions;
import com.massivecraft.massivecore.MassiveCore;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.libraryaddict.disguise.LibsDisguises;
import me.s1mple.matrix.Listener.DreamListener;
import me.s1mple.matrix.Listener.PermsListener;
import me.s1mple.matrix.Listener.SkillsListener;
import me.s1mple.matrix.Raid.RaidListener;
import me.s1mple.matrix.Skills.Werewolf;
import me.s1mple.matrix.Util.Glow;
import me.s1mple.matrix.Util.Util;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange.a;

public class Matrix extends JavaPlugin {
	public static Matrix plugin;
	public static ConfigManager configManager;
	public static String version = "1.10";
	public static String author = "S1mpleCrash";
	private WorldEditPlugin worldEditPlugin;
	private SkillsPro skillsapi;
	private VampireRevamp revamp;
	private LibsDisguises disguise;
	private LuckPerms api;
	private Factions factions;
	private MassiveCore massivecore;
	private ProtocolManager protocolManager;
	@Override
	public void onEnable() {

		this.plugin = this;
		this.configManager = new ConfigManager(this);

		this.worldEditPlugin = ((WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit"));
		this.skillsapi = ((SkillsPro) plugin.getServer().getPluginManager().getPlugin("SkillsPro"));
		this.revamp = ((VampireRevamp) plugin.getServer().getPluginManager().getPlugin("VampireRevamp"));
		this.disguise = ((LibsDisguises) plugin.getServer().getPluginManager().getPlugin("LibsDisguises"));
		this.api = LuckPermsProvider.get();
		this.factions = ((Factions) plugin.getServer().getPluginManager().getPlugin("Factions"));
		this.massivecore = ((MassiveCore) plugin.getServer().getPluginManager().getPlugin("MassiveCore"));
		protocolManager = ProtocolLibrary.getProtocolManager();
		registerGlow();
		new Werewolf();

		ArenaManager.init(this);
		// BattlePass.init(this);
		TournamentHandler.init(this);
		plugin.getServer().getPluginManager().registerEvents(new DreamListener(), Matrix.plugin);
		plugin.getServer().getPluginManager().registerEvents(new SkillsListener(), Matrix.plugin);
		plugin.getServer().getPluginManager().registerEvents(new VPNListener(), Matrix.plugin);
		plugin.getServer().getPluginManager().registerEvents(new PermsListener(), Matrix.plugin);
		plugin.getServer().getPluginManager().registerEvents(new RaidListener(), Matrix.plugin);
	}

	@Override
	public void onDisable() {
		getLogger().info("MatrixPlugin is stopping...");
		this.plugin.saveConfig();
	}

	public void displayCredits(Player p) {
		CraftPlayer craft = (CraftPlayer) p;
		EntityPlayer nms = craft.getHandle();
		nms.viewingCredits = true;

		((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutGameStateChange(new a(4), 1));
		/*
		 * PacketContainer credits = ProtocolLibrary.getProtocolManager()
		 * .createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
		 * credits.getBytes().write(0, (byte) 4); credits.getFloat().write(1, 0.0F); try
		 * { ProtocolLibrary.getProtocolManager().sendServerPacket(p, credits); } catch
		 * (InvocationTargetException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	/**
	 * Add glow effect
	 */
	public void registerGlow() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Glow glow = new Glow(new NamespacedKey(Matrix.plugin, "glow_ench"));
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String helpMessage = ChatColor.AQUA + "/matrix vpn add: " + ChatColor.BLUE + "Creates vpn key\n";
		String staffHelpMessage = ChatColor.AQUA + "/matrix vpn getip <name>: " + ChatColor.BLUE
				+ "Returns the real ip of the player.\n" + ChatColor.AQUA + "/matrix premium <name>: " + ChatColor.BLUE
				+ "Makes player's battlepass premium." + ChatColor.AQUA + "/matrix startRaid: " + ChatColor.BLUE
				+ "Toggles whether the next raid is a Pirate Raid";
		// VPN perm: matrix.vpn
		// Staff perm: matrix.staff

		if (cmd.getName().equalsIgnoreCase("matrix")) {
			if (args.length == 0) {
				if (sender.hasPermission("matrix.staff")) {
					sender.sendMessage(staffHelpMessage);
				}
				// sender.sendMessage(helpMessage);
				return true;
			} else if (args.length >= 1) {
				if (args[0].equalsIgnoreCase("help")) {
					// sender.sendMessage(helpMessage);
					if (sender.hasPermission("matrix.staff")) {
						sender.sendMessage(staffHelpMessage);
					}
					return true;
				} else if (args[0].equalsIgnoreCase("commandspy") || (args[0].equalsIgnoreCase("cmdspy"))) {
					if (sender.hasPermission("matrix.commandspy") && sender instanceof Player) {
						if (PermsListener.playerList.contains(sender)) {
							PermsListener.playerList.remove(sender);
							sender.sendMessage(Util.color("&4Matrix Network &7>> &cCommand spy &4disabled&c!"));
						} else {
							PermsListener.playerList.add((Player) sender);
							sender.sendMessage(Util.color("&4Matrix Network &7>> &cCommand spy &4enabled&c!"));
						}
						return true;
					}
				} else if (args[0].equalsIgnoreCase("startRaid")) {
					if (sender.hasPermission("matrix.commandspy") && sender instanceof Player) {
						if (RaidListener.forceStart) {
							RaidListener.forceStart = false;
							sender.sendMessage(
									Util.color("&4Matrix Network &7>> &cNext Raid will not be a Pirate Raid"));
						} else {
							RaidListener.forceStart = true;
							sender.sendMessage(Util.color("&4Matrix Network &7>> &cNext Raid will be a Pirate Raid"));
						}
						return true;
					}
				} else if (args[0].equalsIgnoreCase("forceCredits")) {
					if (sender instanceof Player && sender instanceof Player && args.length >= 2
							&& sender.hasPermission("matrix.forceCredits")) {
						displayCredits((Player) Bukkit.getPlayer(args[1]));
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "Must be a player.");
						return false;
					}
				} else if (args[0].equalsIgnoreCase("vpn")) {
					if (args.length == 3) {
						if (sender.hasPermission("matrix.vpn")) {
							if (args[1].equalsIgnoreCase("getip")) {
								sender.sendMessage(getIpOfVpnPlayer(args[2]));
								return true;
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Not enough permissions!");
							return false;
						}
					} else if (args.length == 2) {
						if (args[1].equalsIgnoreCase("add")) {
							sender.sendMessage(addVpnKeyForPlayer(plugin.getServer().getPlayer(args[2])));
							return true;
						}
					}
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Unknown command! Use /matrix help");
		return false;
	}

	private String getIpOfVpnPlayer(String arg) {
		return "Unimplemented!";
	}

	private String addVpnKeyForPlayer(Player player) {
		UUID actUUID = UUID.randomUUID();
		try {
			Runtime.getRuntime()
					.exec("/etc/openvpn/client/gen_client.sh " + player.getName() + " " + actUUID.toString());
			player.sendMessage(ChatColor.GREEN
					+ "Open this link, download the .zip and check the README.txt file out! Link: https://matrixnetwork.org/vpn_keys/index.php?id="
					+ actUUID.toString());
			return ChatColor.GREEN + "Success!";
		} catch (IOException e) {
			return ChatColor.RED + "Failed! Try again.";
		}
	}

	/**
	 * @return Plugin instance
	 */
	public static Matrix getPlugin() {
		return plugin;
	}

	public VampireRevamp getRevamp() {
		return revamp;
	}

	public LibsDisguises getDisguises() {
		return disguise;
	}

	public SkillsPro getSkillsApi() {
		return skillsapi;
	}

	public WorldEditPlugin getWorldEditPlugin() {
		return worldEditPlugin;
	}

	public LuckPerms getLuckPerms() {
		return api;
	}

}
