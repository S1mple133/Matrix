package me.s1mple.matrix.Skills;

import org.skills.abilities.Ability;
import org.skills.types.Skill;
import org.skills.types.SkillManager;

import me.s1mple.matrix.Skills.Abilities.VampireBatSwarm;

public class Werewolf {
    public Werewolf() {
    	new VampireBatSwarm("Werewolf", "batSwarm", true);
    	Skill skill = new Skill("Werewolf");
    	SkillManager.registerScalings(skill);
    	skill.register();
    }
}