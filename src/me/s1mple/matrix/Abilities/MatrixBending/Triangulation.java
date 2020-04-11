// Make a triangle between the bender and the 2 nearest players to him
// Put the 2 players in a cone and slow them
// Protect the bender inside a power ball

package me.s1mple.matrix.Abilities.MatrixBending;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent;
import com.projectkorra.projectkorra.util.ParticleEffect;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Triangulation extends CoreAbility implements AddonAbility {

    public Triangulation(Player player) {
        super(player);
        start();
        progress();
    }

    public void progress() {
        if(player.isDead() || !player.isOnline()) {
            remove();
            return;
        }
        List<Player> targetedPlayers = new ArrayList<Player>();

        for (Entity targetEntity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), Util.getAbilityManager().getInt(this.getName(), "Radius"))) {
            if((targetEntity instanceof Player) && ((Player) targetEntity != player)) {
                targetedPlayers.add((Player) targetEntity);
            }
        }
        long ticks = getStartTick();

        // Slow targets
        for(Player target : targetedPlayers) {
            slowPlayer(target);
        }

        while(player.isSneaking()) {
            for(Player target : targetedPlayers) {
                spawnEffects(target, player);
            }
            spawnEffects(targetedPlayers.get(0), targetedPlayers.get(1));

            if(getCurrentTick() - getStartTick() == Util.getAbilityManager().getInt(this.getName(), "ShiftingTime")) {
                for(Player target : targetedPlayers) {
                    damageTargets(target);
                    slowPlayer(target);
                }
                break;
            }
        }

        spawnBall(player, 10);
        addPlayerEffects(player);
    }

    // Protect player through effects
    private void addPlayerEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Util.getAbilityManager().getInt(this.getName(), "ShiftingTime")+20, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Util.getAbilityManager().getInt(this.getName(), "ShiftingTime")+20, 3));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, Util.getAbilityManager().getInt(this.getName(), "ShiftingTime")+20, 1));
    }

    // Spawn a ball with matrix's particles around player
    private void spawnBall(Player player, double radius) {
        double p = 0;
        Location loc = player.getLocation();

        p+= Math.PI /10;
        for(double t = 0; t<=360; t+=Math.PI/40){
            double r =1.5;
            double x = r*cos(t)*sin(p);
            double y = r*cos(p)+1.5;
            double z = r*sin(t)*sin(p);
            loc.add(x,y,z);
            MatrixElement.getParticle().display(loc, 1 );
            loc.subtract(x, y, z);
        }
    }


    // Slow a player down
    private void slowPlayer(Player target) {
    }

    // Damage targets a configurable amount of health
    private void damageTargets(Player target) {
    }

    // Create Triangle between players
    private void spawnEffects(Player target, Player player) {
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
        return "Triangulation";
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
