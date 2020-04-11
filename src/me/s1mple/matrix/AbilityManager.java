package me.s1mple.matrix;


import me.s1mple.matrix.Util.Util;

import java.util.List;

public class AbilityManager {
    private static Matrix plugin;
    private static ConfigManager configManager;

    public AbilityManager(Matrix plugin) {
        this.plugin = plugin;
        this.configManager = Util.getConfigManager();
    }

    /**
     * @param abilityName Name of ability
     * @return Description of ability
     */
    public String getDescription(String abilityName) {
        return configManager.getConfig().getString("Abilities." + abilityName + ".Description");
    }

    /**
     * @param abilityName Name of ability
     * @return Instructions of ability
     */
    public String getInstructions(String abilityName) {
        return configManager.getConfig().getString("Abilities." + abilityName + ".Instructions");
    }

    /**
     * @param abilityName Name of ability
     * @return Cooldown of ability
     */
    public int getCooldown(String abilityName) {
        return configManager.getConfig().getInt("Abilities.Matrix." + abilityName + ".Cooldown");
    }

    /**
     * Return an Integer
     * @param abilityName Name of ability
     * @param property Name of property
     * @return Value
     */
    public int getInt(String abilityName, String property) {
        return configManager.getConfig().getInt("Abilities." + abilityName + "." + property);
    }

    /**
     * Return a List
     * @param abilityName Name of ability
     * @param property Name of property
     * @return Value
     */
    public List<?> getList(String abilityName, String property) {
        return configManager.getConfig().getList("Abilities." + abilityName + "." + property);
    }
    
    /**
     * @param abilityName Name of ability
     * @return State of ability
     */
    public boolean getEnabledState(String abilityName) {
        return configManager.getConfig().getBoolean("Abilities." + abilityName + ".Enabled");
    }

}
