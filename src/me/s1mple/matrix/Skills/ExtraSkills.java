package me.s1mple.matrix.Skills;


import org.skills.types.Skill; 
import org.skills.types.SkillManager;

import me.s1mple.matrix.Skills.Abilities.VampireBatSwarm;
import me.s1mple.matrix.Skills.Abilities.WerewolfPassive;
public class ExtraSkills {
    public ExtraSkills() {
    	new VampireBatSwarm("Vampire", "BatSwarm", true);
    	new WerewolfPassive();
    	Skill skill = new Skill("Werewolf");
    	SkillManager.registerScalings(skill);
    	skill.register();
    }
}