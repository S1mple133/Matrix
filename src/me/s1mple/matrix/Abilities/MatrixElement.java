package me.s1mple.matrix.Abilities;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.ParticleEffect;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MatrixElement {
    public static Element MatrixBending = new Element("Matrix");

    public static ParticleEffect getParticle() {
        return ParticleEffect.DRAGON_BREATH;
    }

    public static void playSound(Player player, Location loc) {
        player.playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 1.0F, 1.0F);
    }


    public static boolean isMatrixBender(Player player) {
        return BendingPlayer.getBendingPlayer(player).isElementToggled(MatrixBending);
    }


    public static boolean isMatrixBender(String playerName) {
        return BendingPlayer.getBendingPlayer(Util.getPlugin().getServer().getPlayer(playerName)).isElementToggled(MatrixBending);
    }

    public static List<Player> heartRegenUsed = new ArrayList<Player>();


    /**
     * @return Chat Color of a MatrixBender
     */
    public String getBendingColor() {
        return Util.getConfigManager().getConfig().getString("Matrix.Color");
    }

    /**
     * @return Prefix of a MatrixBender
     */
    public String getBendingPrefix() {
        return  Util.getConfigManager().getConfig().getString("Matrix.Prefix");
    }



}
