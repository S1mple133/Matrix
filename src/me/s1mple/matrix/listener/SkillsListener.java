package me.s1mple.matrix.listener;

import com.clanjhoo.vampire.event.EventVampirePlayerVampireChange; 
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.skills.api.SkillsAPI;
import org.skills.types.SkillManager;

public class SkillsListener implements Listener{
	
	
	   @EventHandler
		public void vampCmdListener(EventVampirePlayerVampireChange event) {
 
			if(event.isVampire()) {
				SkillsAPI.getSkilledPlayer(event.getUplayer().getOfflinePlayer()).setSkill(SkillManager.getSkill("Vampire"));
				event.getUplayer().getPlayer().sendRawMessage("§cYou have unlocked the §4Vampire§c skill tree. §4Do /skills improve");
			}
			else {
				SkillsAPI.getSkilledPlayer(event.getUplayer().getOfflinePlayer()).setSkill("None");
				event.getUplayer().getPlayer().sendRawMessage("§cYou have been removed from the Vampire skill tree!");

			}
		}

}
