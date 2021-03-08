package me.s1mple.matrix.Raid;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.MatrixMethods;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.Material;
import org.bukkit.Raid;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Raider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidSpawnWaveEvent;
import org.bukkit.event.raid.RaidStopEvent;
import org.bukkit.event.raid.RaidTriggerEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.skills.data.managers.SkilledPlayer;

import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

public class RaidListener implements Listener {
	int num = 1;
	public static boolean forceStart = false;
	Raid pirateRaid;
	Location pirateLocation;

	@EventHandler
	public void onStartRaid(RaidTriggerEvent event) {

		// Check if player with Bad Omen is above level 60
		if (SkilledPlayer.getSkilledPlayer(event.getPlayer()).getLevel() >= 60 || forceStart) {
			// If the player is above level 60 then there is a 1/5 chance that a Pirate Raid
			// occurs
			num = forceStart ? 5 : (int) (Math.random() * 100) + 1;

			System.out.println(num);
			if (num % 5 == 0) {

				// Depending on the result of the player's previous raid, broadcast a message
				// accordingly.
				if (event.getPlayer().hasPermission("quest.raid.Pirate.Lose"))
					Bukkit.broadcastMessage("&ePirates have returned to finish what they started &b"
							+ event.getPlayer().getName() + "&e!");
				else if (event.getPlayer().hasPermission("quest.raid.Pirate.Win"))
					Bukkit.broadcastMessage("&eThe Pirates have returned with a vengeance for &b"
							+ event.getPlayer().getName() + "&e!");
				else
					Bukkit.broadcastMessage("&ePirates are invading &b" + event.getPlayer().getName() + "&e!");

				// We want max levels for the raid so that all possible entities spawn
				event.getRaid().setBadOmenLevel(5);

				// If the custom raid was force started we can now turn this flag off as the
				// raid is already beginning
				forceStart = false;

				pirateRaid = event.getRaid();
				pirateLocation = event.getRaid().getLocation();

				// They need some tunes for the raid
				playPigStep(event.getRaid(), event.getPlayer());
				BukkitScheduler scheduler = Matrix.getPlugin().getServer().getScheduler();
				scheduler.scheduleSyncDelayedTask(Matrix.getPlugin(), new Runnable() {
					@Override
					public void run() {
						playPigStep(event.getRaid(), event.getPlayer());
					}
				}, 2980L);
			}
		}
	}

	/**
	 * This method plays pigstep for everyone in a 40 block radius of the player who
	 * initiated the raid
	 * 
	 * @param raid
	 * @param player
	 */
	public void playPigStep(Raid raid, Player player) {
		player.playSound(raid.getLocation(), Sound.MUSIC_DISC_PIGSTEP, SoundCategory.MUSIC, 100, 1);
		for (Entity e : player.getNearbyEntities(40, 40, 40)) {
			if (e instanceof Player) {
				((Player) e).playSound(raid.getLocation(), Sound.MUSIC_DISC_PIGSTEP, SoundCategory.MUSIC, 100, 1);
			}
		}

	}

	@EventHandler
	public void onEndRaid(RaidStopEvent event) {
		if (num % 5 == 0) {
			if (event.getRaid().getStatus() == Raid.RaidStatus.VICTORY) {
				for (UUID apple : event.getRaid().getHeroes()) {
					Player player = Bukkit.getPlayer(apple);
					if (player != null) {
						player.sendMessage("You have survived the Pirate Invasion");
						if (((Math.random() * 100) + 1) > 50)
							MatrixMethods.ConsoleCmd("mgive " + player.getName() + " elondshead 1");
						MatrixMethods.ConsoleCmd(
								"mgive " + player.getName() + " FadedSoul " + (int) (((Math.random() * 100) + 1) / 4));
						MatrixMethods.ConsoleCmd(
								"mgive " + player.getName() + " chaojuice " + (int) (((Math.random() * 200) + 0) / 4));
						MatrixMethods.ConsoleCmd("mgive " + player.getName() + " Enchanted_Golden_Apple "
								+ (int) (((Math.random() * 260) + 0) / 3));
						MatrixMethods.ConsoleCmd("mgive " + player.getName() + " ChemicalX 1");
						MatrixMethods.ConsoleCmd("mgive " + player.getName() + " BadDayArrow "
								+ (int) (((Math.random() * 100) + 0) / 4));
						MatrixMethods.ConsoleCmd("mgive " + player.getName() + " LuckyBlock 1");
						MatrixMethods.ConsoleCmd(
								"mgive " + player.getName() + " ParrotEgg " + (int) (((Math.random() * 100) + 0) / 4));
						MatrixMethods.addPermission(player, "quest.raid.Pirate.Win");
						MatrixMethods.removePermission(player, "quest.raid.Pirate.Lose");
					}
				}
			} else {
				Bukkit.broadcastMessage("Pirates have successfully pillaged our heroes!");
				for (UUID apple : event.getRaid().getHeroes()) {
					Player player = Bukkit.getPlayer(apple);
					if (player != null) {
						player.sendMessage("The Pirates have stolen your cheese!");
						MatrixMethods.removePermission(player, "quest.raid.Pirate.Win");
						MatrixMethods.addPermission(player, "quest.raid.Pirate.Lose");

						MatrixMethods.ConsoleCmd("eco take " + player.getName() + " 15%");
					}

				}
			}
			num = 1;
		}
	}

	@EventHandler
	public void onRaid(RaidSpawnWaveEvent event) {
		System.out.println(num);
		if (num % 5 == 0) {
			if (pirateRaid == event.getRaid()) {
				System.out.println("Raids are Equal");
			}
			if (pirateLocation == event.getRaid().getLocation()) {
				System.out.println("Locations are equal");
			}
			PlayerDisguise playerDisguise;
			for (Raider raider : event.getRaiders()) {
				if (raider.getMaxHealth() <= 40) {
					raider.setMaxHealth(100);
					raider.setHealth(100);

				}
				switch (raider.getType()) {
				case VINDICATOR:
					MatrixMethods.ConsoleCmd(
							"mmob spawn Pirate " + raider.getLocation().getX() + " " + raider.getLocation().getY() + " "
									+ raider.getLocation().getZ() + " " + raider.getLocation().getWorld().getName());
					playerDisguise = new PlayerDisguise("_Tommy");
					playerDisguise.setName("Pirate Runt");
					playerDisguise.setEntity(raider);
					playerDisguise.startDisguise();
					break;
				case PILLAGER:
					System.out.println("mm m spawn Pirate 1" + raider.getLocation().getWorld().getName() + ","
							+ raider.getLocation().getX() + "," + raider.getLocation().getY() + ","
							+ raider.getLocation().getZ());
					MatrixMethods.ConsoleCmd("mm m spawn Pirate 1 Episode1," + raider.getLocation().getX() + ","
							+ raider.getLocation().getY() + "," + raider.getLocation().getZ());
					playerDisguise = new PlayerDisguise("PirateGirl");
					playerDisguise.setName("Pirate Archer");
					playerDisguise.setEntity(raider);
					playerDisguise.startDisguise();
					break;
				case EVOKER:
					MatrixMethods.ConsoleCmd("mmob spawn PirateWizard " + raider.getLocation().getX() + " "
							+ raider.getLocation().getY() + " " + raider.getLocation().getZ() + " "
							+ raider.getLocation().getWorld().getName());
					playerDisguise = new PlayerDisguise("PirateGhost");
					playerDisguise.setName("Pirate Evoker");
					playerDisguise.setEntity(raider);
					playerDisguise.startDisguise();
					break;
				case WITCH:
					MatrixMethods.ConsoleCmd("mmob spawn PirateWizard " + raider.getLocation().getX() + " "
							+ raider.getLocation().getY() + " " + raider.getLocation().getZ() + " "
							+ raider.getLocation().getWorld().getName());
					playerDisguise = new PlayerDisguise("FemalePirate");
					playerDisguise.setName("Alchemist");
					playerDisguise.setEntity(raider);
					FlagWatcher watcher = playerDisguise.getWatcher();
					watcher.setItemInMainHand(new ItemStack(Material.POTION));
					watcher.setItemInOffHand(new ItemStack(Material.LINGERING_POTION));
					playerDisguise.startDisguise();
					break;
				case ILLUSIONER:
					playerDisguise = new PlayerDisguise("PirateGhost");
					playerDisguise.setName("Pirate Ghost");
					playerDisguise.setEntity(raider);
					playerDisguise.startDisguise();
					break;
				case RAVAGER:
					MatrixMethods.ConsoleCmd("mmob spawn PirateGhost " + raider.getLocation().getX() + " "
							+ raider.getLocation().getY() + " " + raider.getLocation().getZ() + " "
							+ raider.getLocation().getWorld().getName());
					MatrixMethods.ConsoleCmd("mmob spawn PirateLurker " + raider.getLocation().getX() + " "
							+ raider.getLocation().getY() + " " + raider.getLocation().getZ() + " "
							+ raider.getLocation().getWorld().getName());
					break;
				default:
					playerDisguise = new PlayerDisguise("_Tommy");
					playerDisguise.setName("Pirate Runt");
					playerDisguise.setEntity(raider);
					playerDisguise.startDisguise();
					break;
				}
			}
		}
	}
}
