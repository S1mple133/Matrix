package me.s1mple.matrix.Abilities.MatrixBending;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class HeartRegen extends CoreAbility implements AddonAbility {

    public HeartRegen(Player player) {
        super(player);
        start();
        progress();
    }

    public void progress() {
        if(player.isDead() || !player.isOnline() || MatrixElement.heartRegenUsed.contains(player)) {
            remove();
            return;
        }

        player.setHealth(player.getHealth()+4);
        player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 20);
        MatrixElement.heartRegenUsed.add(player);
    }

    public boolean isSneakAbility() {
        return false;
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
        return Util.getAbilityManager().getCooldown(getName());
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
        return "HeartRegen";
    }

    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public boolean isHiddenAbility() { return false; }

    @Override
    public void load() {
        return;
    }

    @Override
    public void stop() {
        return;
    }

    @Override
    public String getAuthor() {
        return Matrix.author;
    }

    @Override
    public String getVersion() {
        return Matrix.version;
    }
}
