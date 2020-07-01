package me.s1mple.matrix.Abilities.MatrixBending;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.TimeUnit;

public class Knight extends CoreAbility implements AddonAbility {

    public Knight(Player player) {
        super(player);
        start();
        progress();
    }

    public void progress() {
        if(player.isDead() || !player.isOnline()) {
            remove();
            return;
        }

        summonKnight();
    }

    public void summonKnight() {
        int range = Util.getAbilityManager().getInt(this.getName(),"TargetPlayerRadius");
        Entity targetEntity = GeneralMethods.getTargetedEntity(player, range);
        Player target;

        if(!(targetEntity instanceof Player)) {
            remove();
            return;
        }

        target = (Player) targetEntity;

        // Spawn Knight
        Skeleton knight = target.getLocation().getWorld().spawn(target.getLocation().add(1.0D, 0.0D, 0.0D), Skeleton.class);
        knight.setCustomName(ChatColor.YELLOW + player.getName() + "'s Knight");

        equipKnight(player, knight);

        knight.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 900, 2));

        // Set knights target
        knight.damage(1.0D, target);
        knight.setHealth(knight.getHealth() + 1.0);

        ParticleEffect.VILLAGER_ANGRY.display(target.getEyeLocation(), 7,(float)Math.random(), (float)Math.random(), (float)Math.random());
    }
    
    public void equipKnight(Player player, Skeleton knight) {
        if (BendingPlayer.getBendingPlayer(player).isAvatarState()) {
            knight.getEquipment().getBoots().setType(Material.IRON_BOOTS);
            knight.getEquipment().getChestplate().setType(Material.IRON_CHESTPLATE);
            knight.getEquipment().getHelmet().setType(Material.IRON_HELMET);
            knight.getEquipment().getLeggings().setType(Material.IRON_LEGGINGS);
        }
        else {
            knight.getEquipment().getBoots().setType(Material.LEATHER_BOOTS);
            knight.getEquipment().getChestplate().setType(Material.LEATHER_CHESTPLATE);
            knight.getEquipment().getHelmet().setType(Material.LEATHER_HELMET);
            knight.getEquipment().getLeggings().setType(Material.LEATHER_LEGGINGS);
        }
    }

    public void killKnight(Player player, Skeleton knight) {
        try {
            TimeUnit.SECONDS.sleep(Util.getAbilityManager().getInt(this.getName(), "KillKnight"));
        } catch (InterruptedException e) {
            Util.getPlugin().getLogger().info(e.getMessage());
        }

        if(knight.isDead()) {
            remove();
        }

        knight.setHealth(0);
        remove();
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
        return "Knight";
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
