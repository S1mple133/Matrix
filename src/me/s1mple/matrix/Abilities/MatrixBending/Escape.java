package me.s1mple.matrix.Abilities.MatrixBending;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.AbilityManager;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Escape extends CoreAbility implements AddonAbility {

    public Escape(Player player) {
        super(player);
        start();
        progress();
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

        escape();
    }

    public void escape() {
        int blocks = Util.getAbilityManager().getInt(this.getName(),"blocks");
        int effectDuration = Util.getAbilityManager().getInt(this.getName(),"effectduration");
        Player target;

        for(Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), (double)blocks)) {
            if(!(entity instanceof  Player)) {
                continue;
            }

            // Players within range
            target = (Player) entity;
            Util.createHelix(target);
            MatrixElement.playSound(target, target.getLocation());
            target.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(effectDuration, 2));
        }

        player.teleport(GeneralMethods.getTargetedLocation(player, blocks));
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
        return "Escape";
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
