package me.s1mple.matrix.Skills.Abilities;

import org.bukkit.entity.Player;
import org.skills.abilities.AbilityManager;
import org.skills.abilities.ActiveAbility;

import com.clanjhoo.vampire.entity.UPlayer;

public class VampireBatSwarm extends ActiveAbility {
	String Vampire;
	String batSwarm;
	public VampireBatSwarm(String skill, String name, boolean activateOnReady) {
		super(skill, name, activateOnReady);
		AbilityManager.register(this);
		
	}
	
	@Override
	public void useSkill(Player player) {
		if (UPlayer.get(player) != null & UPlayer.get(player).isNosferatu()) {
			player.performCommand("v batusi");
		}
		else {
			player.sendMessage("§cYou are not a Nosferatu Vampire!");
		}
	}
	
	
	
	
	

}
