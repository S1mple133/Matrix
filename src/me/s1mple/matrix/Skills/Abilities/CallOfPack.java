package me.s1mple.matrix.Skills.Abilities;

import org.bukkit.entity.Player;
import org.skills.abilities.Ability;
import org.skills.abilities.AbilityManager;

public class CallOfPack extends Ability {
	Player player;
	
	public CallOfPack(String skill, String name) {
		super(skill, name);
		
		AbilityManager.register(this);
		
		/*
		//setElementChi();
		//listen.onSkillActivate(arg0);
		AbilityManager.register(this);
		*/
	}
}
	
