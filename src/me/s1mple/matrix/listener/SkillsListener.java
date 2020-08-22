package me.s1mple.matrix.listener;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.skills.api.SkillsAPI;
import org.skills.types.SkillManager;
import com.clanjhoo.vampire.event.VampireTypeChangeEvent;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.node.Node;
public class SkillsListener implements Listener{
		LuckPerms api;
	
	   @EventHandler
		public void vampCmdListener(VampireTypeChangeEvent event) { 
		   RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		   Player player = event.getUplayer().getPlayer();
		   if (provider != null) {
		       api = provider.getProvider();
		   }
		   
			if(event.isVampire()) {
				SkillsAPI.getSkilledPlayer(player).setActiveSkill(SkillManager.getSkill("Vampire"));
				event.getUplayer().getPlayer().sendRawMessage("§cYou have unlocked the §4Vampire§c skill tree. §4Do /skills improve");
				DataMutateResult result = api.getUserManager().getUser(player.getUniqueId()).data().add(Node.builder("-skills.select.*").build());
			}
			else {
				SkillsAPI.getSkilledPlayer(player).setActiveSkill(SkillManager.getSkill("Arbalist"));
				event.getUplayer().getPlayer().sendRawMessage("§cYou have been removed from the Vampire skill tree!");
				DataMutateResult result = api.getUserManager().getUser(player.getUniqueId()).data().add(Node.builder("-skills.select.*").build());
			}
			
			
			
			
			
			
		}
	  
}
