package me.s1mple.matrix.Skills.Abilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.skills.abilities.AbilityManager;
import org.skills.abilities.ActiveAbility;
import org.skills.api.events.SkillActiveStateChangeEvent;
import org.skills.api.events.SkillToggleAbilityEvent;
import org.skills.data.managers.SkilledPlayer;
import org.skills.types.SkillManager;

import com.clanjhoo.vampire.VampireRevamp;
import com.clanjhoo.vampire.entity.UPlayer;

public class VampireBatSwarm extends ActiveAbility {
	String Vampire;
	String batSwarm;
	public VampireBatSwarm(String skill, String name, boolean activateOnReady) {
		super(skill, name, activateOnReady);
		AbilityManager.register(this);
	}

   @EventHandler
	public void toggleBatSwarm(SkillToggleAbilityEvent event) {
	   if (event.getAbility() == this) {
			event.getPlayer().sendRawMessage("§cThis ability can't exactly be toggled yet. Sorry!");
	   }
	}
   @EventHandler
	public void testingThing(SkillActiveStateChangeEvent event) {
		Player player = event.getPlayer();
		if (SkilledPlayer.getSkilledPlayer(player).getSkill() != SkillManager.getSkill("Vampire"))
		{
			
		} else if (UPlayer.get(player) != null && UPlayer.get(player).isNosferatu()) {
			player.performCommand("v batusi");
		}
   }
	@Override
	public void useSkill(Player player) {
	    SkilledPlayer info = activeCheckup(player);
	    if (info == null) 
	    	return;
	    System.out.println("Use Skill successfully triggered");
		if (UPlayer.get(player) != null && UPlayer.get(player).isNosferatu()) {
			player.performCommand("v batusi");
			
		}
		else {
			player.sendRawMessage("§cYou are not a Nosferatu Vampire!");
		}
    	
	}
	public SkilledPlayer activeCheckup(Player arg0) {
		return SkilledPlayer.getSkilledPlayer(arg0);
	}
	
	@Override
	public String getTitle(SkilledPlayer info) {
		return "&4Bat Swarm";
	}
	@Override
	public String getDescription(SkilledPlayer info) {
		return "&3Transform into a swarm of bats";
		
	}
	

	
}
