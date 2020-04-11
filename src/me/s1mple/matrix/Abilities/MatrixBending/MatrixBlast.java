package me.s1mple.matrix.Abilities.MatrixBending;

import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.ability.ElementalAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class MatrixBlast extends CoreAbility implements AddonAbility {

    public MatrixBlast(Player player) {
        super(player);
        start();
        progress();
    }

    public void progress() {
        if(player.isDead() || !player.isOnline()) {
            remove();
            return;
        }

        shoot();
        stop();
    }

    private void shoot() {
        final Entity target = GeneralMethods.getTargetedEntity(player, Util.getAbilityManager().getInt(this.getName(), "Range"));

        if(!(target instanceof LivingEntity)) {
            remove();
            return;
        }

        Vector playerToTarget = target.getLocation().toVector().subtract(player.getLocation().toVector());
        final double length = playerToTarget.length();

        final int[] i = {0};

        BukkitTask run = new BukkitRunnable() {
            @Override
            public void run() {
                Location putParticle = player.getLocation().toVector().add(target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(i[0] *1.5)).toLocation(player.getWorld());
                MatrixElement.getParticle().display(putParticle, 1);

                i[0] += 1;

                if(target.getLocation().distance(putParticle) <= 1) {
                    ((LivingEntity)target).damage(Util.getAbilityManager().getInt(getName(), "Damage"));
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Matrix.getPlugin(), 0, 1);

        // Damage
    }

    public boolean isSneakAbility() {
        return false;
    }

    public boolean isHarmlessAbility() {
        return false;
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
        return "MatrixBlast";
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
