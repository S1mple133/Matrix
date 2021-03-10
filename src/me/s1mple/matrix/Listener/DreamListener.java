package me.s1mple.matrix.Listener;

import me.s1mple.matrix.MatrixMethods;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.inventory.ItemStack;


public class DreamListener implements Listener{
	
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
