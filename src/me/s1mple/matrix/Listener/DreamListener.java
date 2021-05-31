package me.s1mple.matrix.Listener;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.MatrixMethods;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange;

import net.minecraft.server.v1_16_R3.PacketPlayOutGameStateChange.a;
import com.massivecraft.factions.AccessStatus;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPerm;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.Rank;
import com.massivecraft.massivecore.ps.PS;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import org.skills.data.managers.SkilledPlayer;

import com.clanjhoo.vampire.entity.UPlayerColl;
import com.massivecraft.factions.Factions;


public class DreamListener implements Listener{

    @EventHandler 
    public void onBellUse(PlayerInteractEvent event) {
    	Action action = event.getAction();
    	if (action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) {
        	Location location = event.getClickedBlock().getLocation();
    		if (event.getClickedBlock().getType() == Material.BELL) {
    			   Player player = event.getPlayer();
    				ItemStack stack = player.getInventory().getItemInMainHand();
    				ItemStack stack1 = player.getInventory().getItemInOffHand();
    				boolean item1 = false;
    				boolean item2 = false;
    				try {
    					item1 = stack.getType() == Material.NAUTILUS_SHELL && stack.getAmount() >= 10 && stack.getItemMeta().getLore().get(1).contains("a collectible of some sort") && stack.getItemMeta().getDisplayName().contains("Hardened Gel");
    					item2 = stack1.getType() == Material.NAUTILUS_SHELL && stack1.getAmount() >= 10 && stack1.getItemMeta().getLore().get(1).contains("a collectible of some sort") && stack1.getItemMeta().getDisplayName().contains("Hardened Gel");
    				} catch (Exception e) {
    				
    				}
    				if (item1)
						stack.setAmount(stack.getAmount()-5);
    				if (item2)
    					stack1.setAmount(stack.getAmount()-5);
    				if (item1 || item2) {
    					location.getWorld().playSound(location, Sound.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.NEUTRAL, 100, (float) 1.5);
    					player.sendMessage("§3§oPrime hears your call and rings the grand bell in your name...");
    					for (Entity e : location.getWorld().getNearbyEntities(location, 40, 40, 40)) {
    						if (e instanceof Player && e.getEntityId() != player.getEntityId()) {
    						
    							if (MPlayer.get(player).getFaction() != MPlayer.get((Player) e).getFaction()) {
    								
	    							((Player) e).sendMessage("§3The Primordial Bell Rings for thee");
	    							MatrixMethods.ConsoleCmd("god off " + ((Player) e).getName());
	    							((LivingEntity) e).removePotionEffect(PotionEffectType.HEALTH_BOOST);
	    							((LivingEntity) e).removePotionEffect(PotionEffectType.REGENERATION);
	    							((LivingEntity) e).removePotionEffect(PotionEffectType.DOLPHINS_GRACE);
	    							((LivingEntity) e).removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
	    							((LivingEntity) e).removePotionEffect(PotionEffectType.SPEED);
	    							((LivingEntity) e).removePotionEffect(PotionEffectType.NIGHT_VISION);
	
	    							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 600, 2));
	    							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 8));
	    							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 4));
	    							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 8));
	    							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 10));
	
	    							((Player) e).setFlying(false);
	    							((Player) e).setSprinting(false);
	    							((Player) e).setInvisible(false);
	    							
	    							player.attack(e);
	    							player.attack(e);
	
	    							if (SkilledPlayer.getSkilledPlayer((Player) e).getLevel() >= 50) {
	        							((Player) e).setVelocity(new Vector(-10, -20, 10));   
	        							((Player) e).setHealth(5);
	        							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 500, 5));
	        							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 4));
	        							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 10));
	        							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 10));
	    							} else {
	        							((LivingEntity) e).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 4));
	    							}
    							}
    						}
    					}
    				}
    				
    					
    		}
    	}
    }
	   @EventHandler
		public void bedListener(PlayerBedEnterEvent event) {
		   Player player = event.getPlayer();
			ItemStack stack = player.getInventory().getItemInMainHand();
			ItemStack stack1 = player.getInventory().getItemInOffHand();
			boolean item1 = false;
			boolean item2 = false;
			try {
				item1 = stack.getItemMeta().getCustomModelData() == 2200035 && stack.getItemMeta().getLore().get(1).contains("CHAOSSSS") && stack.getItemMeta().getDisplayName().contains("Rune of Madness");
				item2 = stack1.getItemMeta().getCustomModelData() == 2200035 && stack1.getItemMeta().getLore().get(1).contains("CHAOSSSS") && stack1.getItemMeta().getDisplayName().contains("Rune of Madness");		
			} catch (Exception e) {
			
			}

			if (player.getLocation().getY() < 80 && (item1 || item2)) {
				player.sendMessage("§dThe rune requests you ascend to greater heights...");
			} else {
				if (item1 && event.getBedEnterResult().equals(BedEnterResult.OK)) {
					stack.setAmount(stack.getAmount()-1);
					player.sendMessage("§dThe rune plunges your mind into a dream....");
					MatrixMethods.ConsoleCmd("execute in minecraft:dream_dimension run teleport "+ player.getName()
					+ " ~ ~2 ~" );
					Block block = player.getLocation().subtract(0, 1, 0).getBlock();
					block.setType(Material.PINK_BED);
					block = player.getLocation().subtract(0, 1, 1).getBlock();
					block.setType(Material.PINK_BED);

					
				} else if (item2 && event.getBedEnterResult().equals(BedEnterResult.OK)) {
					stack1.setAmount(stack1.getAmount()-1);
					player.sendMessage("§dThe rune plunges your mind into a dream....");
					MatrixMethods.ConsoleCmd("execute in minecraft:dream_dimension run teleport "+ player.getName()
					+ " ~ ~ ~" );
					Block block = player.getLocation().subtract(0, 1, 0).getBlock();
					block.setType(Material.MELON);
				} else if (!event.getBedEnterResult().equals(BedEnterResult.OK) && (item1 || item2)) {
					player.sendMessage("§5The rune demands silence...");
				}

			}

	   }
}
