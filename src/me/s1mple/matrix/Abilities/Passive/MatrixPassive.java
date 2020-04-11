package me.s1mple.matrix.Abilities.Passive;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.PassiveAbility;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MatrixPassive extends CoreAbility implements PassiveAbility {

    public MatrixPassive(Player player) {
        super(player);
    }

    public void progress() {
        if(player.isDead() || !player.isOnline()) {
            remove();
            return;
        }

        if(!player.isSneaking()) {
            remove();
            return;
        }

        addEffects();
    }

    private void addEffects() {
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 500, 1));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 60, 1));
        this.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 500, 1));
    }

    @Override
    public void remove() {
        this.player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        this.player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }

    public boolean isSneakAbility() {
        return true;
    }

    public boolean isHarmlessAbility() {
        return true;
    }

    @Override
    public boolean isIgniteAbility() {
        return false;
    }

    @Override
    public boolean isExplosiveAbility() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return Util.getAbilityManager().getEnabledState(this.getName());
    }

    public long getCooldown() {
        return 0L;
    }

    @Override
    public String getDescription() {
        return Util.getAbilityManager().getDescription(this.getName());
    }

    @Override
    public Element getElement() {
        return MatrixElement.MatrixBending;
    }

    @Override
    public String getInstructions() {
        return Util.getAbilityManager().getInstructions(this.getName());
    }

    public String getName() {
        return "MatrixPassive";
    }

    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public boolean isHiddenAbility() { return false; }

    public boolean isInstantiable() {
        return true;
    }

    public boolean isProgressable() {
        return true;
    }
}
