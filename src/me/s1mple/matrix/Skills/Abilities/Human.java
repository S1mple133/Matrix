package me.s1mple.matrix.Skills.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.skills.abilities.Ability;
import org.skills.abilities.AbilityListener;
import org.skills.abilities.AbilityManager;
import org.skills.api.events.SkillToggleAbilityEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
public class Human extends Ability {
	Player player;
	

	
	public Human(String skill, String name) {
		super(skill, name);
		
		//setElementChi();
		AbilityListener listen = new AbilityListener();
		//listen.onSkillActivate(arg0);
		AbilityManager.register(this);
		
	}
	@EventHandler
	public void setElementChi(PlayerInteractEvent event) {

	        //BendingPlayer bPlayer = BendingPlayer.getBendingPlayer();
	        //bPlayer.setElement(Element.CHI);
	}
}
	
