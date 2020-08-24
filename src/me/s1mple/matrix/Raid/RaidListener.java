package me.s1mple.matrix.Raid;


import me.s1mple.matrix.Matrix;
import net.luckperms.api.node.Node;
import net.luckperms.api.LuckPerms;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.skills.data.managers.SkilledPlayer;
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

import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;

public class RaidListener implements Listener {
	LuckPerms api;
	int num = 1;
	public static boolean forceStart = false;
	Raid pirateRaid;
	Location pirateLocation;
	@EventHandler
	public void onStartRaid(RaidTriggerEvent event) {
		//Check if player with Bad Omen is above level 60
		if (SkilledPlayer.getSkilledPlayer(event.getPlayer()).getLevel() >= 60 || forceStart) {
				//If the player is above level 60 then there is a 1/5 chance that a Pirate Raid occurs
				num = forceStart ? 5 : (int) (Math.random() * 100) + 1;
				System.out.println(num);
				if (num % 5 == 0){
		       if (event.getPlayer().hasPermission("quest.raid.Pirate.Lose")) {
		           Bukkit.broadcastMessage("Pirates have returned to finish what they started " + event.getPlayer().getName() + "!");
		         } else if (event.getPlayer().hasPermission("quest.raid.Pirate.Lose")) {
		           Bukkit.broadcastMessage("Pirates are invading " + event.getPlayer().getName() + "!");
		         } 					
		       	//Message broadcasted to the server.
					Bukkit.broadcastMessage("§eThe Pirates are Invading!");
					//We want max levels for the raid so that all possible entities spawn
					event.getRaid().setBadOmenLevel(5);
					forceStart = false;
					pirateRaid = event.getRaid();
					pirateLocation = event.getRaid().getLocation();
					//They need some tunes for the raid
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
	 * This method plays pigstep for everyone in a 40 block radius of the player who initiated the raid
	 * @param raid
	 * @param player
	 */
	public void playPigStep(Raid raid, Player player) {
		player.playSound(raid.getLocation(), Sound.MUSIC_DISC_PIGSTEP, SoundCategory.MUSIC , 100, 1);
		for (Entity e : player.getNearbyEntities(40, 40, 40)) {
			if(e instanceof Player) {
				((Player) e).playSound(raid.getLocation(), Sound.MUSIC_DISC_PIGSTEP, SoundCategory.MUSIC , 100, 1);
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
					       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " elondshead 1");
			       	 	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " FadedSoul " + (int) (((Math.random() * 100) + 1)/4));
				       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " chaojuice " + (int) (((Math.random() * 200) + 0)/4));
				       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " Enchanted_Golden_Apple " + (int) (((Math.random() * 260) + 0)/4));
				       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " ChemicalX 1");
				       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " BadDayArrow " + (int) (((Math.random() * 100) + 0)/4));
				       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " LuckyBlock 1");
				       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mgive " + player.getName() + " ParrotEgg " + (int) (((Math.random() * 100) + 0)/4));
						api.getUserManager().getUser(player.getUniqueId()).data().add(Node.builder("quest.raid.Pirate.Win").build());
						api.getUserManager().getUser(player.getUniqueId()).data().remove(Node.builder("quest.raid.Pirate.Lose").build());
					    api.getUserManager().saveUser(api.getUserManager().getUser(player.getUniqueId()));
			    	}
		    	}
			} else
			{
				Bukkit.broadcastMessage("Pirates have successfully pillaged our heroes!");
		    	for (UUID apple : event.getRaid().getHeroes()) {
		    		Player player = Bukkit.getPlayer(apple);
		    		if (player != null) {		
			       	 	player.sendMessage("The Pirates have stolen your cheese!");
						api.getUserManager().getUser(player.getUniqueId()).data().remove(Node.builder("quest.raid.Pirate.Win").build());
						api.getUserManager().getUser(player.getUniqueId()).data().add(Node.builder("quest.raid.Pirate.Lose").build());
					    api.getUserManager().saveUser(api.getUserManager().getUser(player.getUniqueId()));
				       	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco take " + player.getName() + " 25%");
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
	         for(Raider raider : event.getRaiders()) {
	        	if (raider.getMaxHealth() <= 40) {
	        		raider.setMaxHealth(100);
	        		raider.setHealth(100);
	        		
	        	}
	        	switch (raider.getType()) {
	        	case VINDICATOR:
		            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mmob spawn Pirate " + raider.getLocation().getX() + " " + raider.getLocation().getY() + " " 
		            		+ raider.getLocation().getZ() + " " + raider.getLocation().getWorld().getName());
	            	playerDisguise = new PlayerDisguise("_Tommy");
	            	playerDisguise.setName("Pirate Runt");
	            	playerDisguise.setEntity(raider);
	            	playerDisguise.startDisguise();
	            	break;
	        	case PILLAGER:
	        		System.out.println("mm m spawn Pirate 1" + raider.getLocation().getWorld().getName() +
	        				"," + raider.getLocation().getX() + "," + raider.getLocation().getY() + "," + raider.getLocation().getZ());
	        		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mm m spawn Pirate 1 Episode1," + raider.getLocation().getX() + "," + raider.getLocation().getY() + "," 
		            		+ raider.getLocation().getZ());
	            	playerDisguise = new PlayerDisguise("PirateGirl");
	            	playerDisguise.setName("Pirate Archer");
	            	playerDisguise.setEntity(raider);
	            	playerDisguise.startDisguise();
	            	break;
	        	case EVOKER:
		            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mmob spawn PirateWizard " + raider.getLocation().getX() + " " + raider.getLocation().getY() + " " 
		            		+ raider.getLocation().getZ() + " " + raider.getLocation().getWorld().getName());
	            	playerDisguise = new PlayerDisguise("PirateGhost");
	            	playerDisguise.setName("Pirate Evoker");
	            	playerDisguise.setEntity(raider);
	            	playerDisguise.startDisguise();
	            	break;
	        	case WITCH:
		            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mmob spawn PirateWizard " + raider.getLocation().getX() + " " + raider.getLocation().getY() + " " 
		            		+ raider.getLocation().getZ() + " " + raider.getLocation().getWorld().getName());
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
		            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mmob spawn PirateGhost " + raider.getLocation().getX() + " " + raider.getLocation().getY() + " " 
		            		+ raider.getLocation().getZ() + " " + raider.getLocation().getWorld().getName());
		            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mmob spawn PirateLurker " + raider.getLocation().getX() + " " + raider.getLocation().getY() + " " 
		            		+ raider.getLocation().getZ() + " " + raider.getLocation().getWorld().getName());
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
