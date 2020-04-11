package me.s1mple.matrix.Abilities.MatrixBending;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class MatrixState extends CoreAbility implements AddonAbility {

    public MatrixState(Player player) {
        super(player);
        start();
        progress();
    }

    public void progress() {
        if(player.isDead() || !player.isOnline()) {
            remove();
            return;
        }

        addEffects();
        slowEnemies();
        stop();
    }

    private void addEffects() {
        for(String effectName : (List<String>)Util.getAbilityManager().getList(this.getName(), "EffectList")) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effectName), Util.getAbilityManager().getInt(this.getName(), "EffectDuration"), 3));
        }
    }

    private void slowEnemies() {
        for(Entity target : GeneralMethods.getPlayersAroundPoint(player.getLocation(), Util.getAbilityManager().getInt(this.getName(), "TargetPlayerRadius"))) {
            if((target == player)) {
                continue;
            }

            Player playerTarget = (Player) target;
            playerTarget.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Util.getAbilityManager().getInt(this.getName(), "EffectDuration"), 1));
            Util.coneEffect(playerTarget);
        }
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
        return "MatrixState";
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
