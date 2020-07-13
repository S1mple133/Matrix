package me.s1mple.matrix.Skills.Abilities;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.skills.abilities.AbilityManager;
import org.skills.abilities.ActiveAbility;
import org.skills.api.events.SkillActiveStateChangeEvent;
import org.skills.data.managers.SkilledPlayer;

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
	public void testingThing(SkillActiveStateChangeEvent event) {
		Player player = event.getPlayer();
		System.out.println("Testeroni");
		if (UPlayer.get(player) != null && UPlayer.get(player).isNosferatu()) {
			player.performCommand("v batusi");
			System.out.println("Success");
			player.sendRawMessage("§cYou transform!");
		}
		else {
			player.sendRawMessage("§cYou are not a Nosferatu Vampire!");
		}
	}
	@Override
	public void useSkill(Player player) {
		System.out.println("Testeroni");
		if (UPlayer.get(player) != null && UPlayer.get(player).isNosferatu()) {
			player.performCommand("v batusi");
			System.out.println("Success");
			player.sendRawMessage("§cYou transform!");
		}
		else {
			player.sendRawMessage("§cYou are not a Nosferatu Vampire!");
		}
	}
	public SkilledPlayer activeCheckup(Player arg0) {
		return SkilledPlayer.getSkilledPlayer(arg0);
	}
	@Override
	public String getActivationKey(SkilledPlayer info) {
		return "SSL";
	}
	@Override
	public String getTranslatedScaling(SkilledPlayer info, String arg1, Object... arg2) {
		return "1";
	}
	@Override
	public double getAbsoluteScaling(SkilledPlayer info, String arg1, Object... arg2) {
		return 1;
	}
	@Override
	public double getCooldown(SkilledPlayer info) {
		return 1;
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
