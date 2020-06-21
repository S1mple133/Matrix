package me.s1mple.matrix.listener;

import com.clanjhoo.vampire.event.EventVampirePlayerVampireChange;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.skills.api.SkillsAPI;
import org.skills.types.SkillManager;

public class SkillsListener implements Listener{
	
	
	   @EventHandler
		public void vampCmdListener(EventVampirePlayerVampireChange event) {
 
			if(event.isVampire()) {
				SkillsAPI.getSkilledPlayer(event.getUplayer().getPlayer()).setSkill(SkillManager.getSkill("Vampire"));
				event.getUplayer().getPlayer().sendMessage("§cYou have unlocked the §4Vampire§c skill tree. §4Do /skills improve");
	            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + event.getUplayer().getPlayer().getName() + " add -skills.select.*");
			}
			else {
				SkillsAPI.getSkilledPlayer(event.getUplayer().getPlayer()).setSkill(SkillManager.getSkill("Arbalist"));
				event.getUplayer().getPlayer().sendMessage("§cYou have been removed from the Vampire skill tree!");
	            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + event.getUplayer().getPlayer().getName() + " remove -skills.select.*");

			}
		}

}
