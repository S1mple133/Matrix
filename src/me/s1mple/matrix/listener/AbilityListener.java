package me.s1mple.matrix.listener;

import com.projectkorra.projectkorra.BendingPlayer; 
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.event.PlayerChangeElementEvent;
import me.s1mple.matrix.Abilities.MatrixBending.*;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener{
    private Matrix plugin;

    public AbilityListener(Matrix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        Entity target = GeneralMethods.getTargetedEntity(player, 20.0);


        // LEFT CLiCK
        if(event.getAction() == Action.LEFT_CLICK_AIR) {
            CoreAbility coreAbility = Util.getCoreAbility(player);

            if(coreAbility == null) {
                return;
            }

            // MATRIX ABILITIES
            if((coreAbility.getElement().equals(MatrixElement.MatrixBending)) && (bPlayer.isElementToggled(MatrixElement.MatrixBending))) {
                if(coreAbility.getName().toLowerCase().equals("escape")) {
                    new Escape(player);
                }
                else if(coreAbility.getName().toLowerCase().equals("knight") && (GeneralMethods.getTargetedEntity(player, Util.getAbilityManager().getInt("Knight", "Range")) instanceof Player)) {
                    new Knight(player);
                }
                else if(coreAbility.getName().toLowerCase().equals("heartregen")) {
                    new HeartRegen(player);
                }
                else if(coreAbility.getName().toLowerCase().equals("matrixblast")) {
                    new MatrixBlast(player);
                }
                else if(coreAbility.getName().toLowerCase().equals("matrixstate")) {
                    new MatrixState(player);
                }

            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(MatrixElement.heartRegenUsed.contains(player))
            MatrixElement.heartRegenUsed.remove(player);
    }

}
