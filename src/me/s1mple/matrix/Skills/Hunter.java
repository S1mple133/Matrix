package me.s1mple.matrix.Skills;

import me.s1mple.matrix.Skills.Abilities.Human;

import java.util.HashMap;
import java.util.Map;

import org.skills.abilities.Ability;
import org.skills.types.Skill;
import org.skills.types.SkillManager;

public class Hunter {
    public Hunter() {
    	//Map<String, Ability> maps = new HashMap<>();
        Skill skill = new Skill("Hunter");
    	//maps.put("Human", new Human(skill.getName(), "Human"));
        //skill.setAbilities(maps); 
        new Human(skill.getName(), "Human");
        SkillManager.registerScalings(skill);
        skill.register();
        
    }
    
}