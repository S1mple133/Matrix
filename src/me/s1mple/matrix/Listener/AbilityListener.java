package me.s1mple.matrix.Listener;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.CoreAbility;
import me.s1mple.matrix.Abilities.MatrixBending.*;
import me.s1mple.matrix.Abilities.MatrixElement;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
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
        if(event.getAction() == Action.LEFT_CLICK_AIR) {
            CoreAbility coreAbility = Util.getCoreAbility(event.getPlayer());

            if(coreAbility == null) {
                return;
            }

            // MATRIX ABILITIES
            if((coreAbility.getElement().equals(MatrixElement.MatrixBending)) && (BendingPlayer.getBendingPlayer(event.getPlayer()).isElementToggled(MatrixElement.MatrixBending))) {
                if(coreAbility.getName().toLowerCase().equals("escape")) {
                    new Escape(event.getPlayer());
                }
                else if(coreAbility.getName().toLowerCase().equals("knight") && (GeneralMethods.getTargetedEntity(event.getPlayer(), Util.getAbilityManager().getInt("Knight", "Range")) instanceof Player)) {
                    new Knight(event.getPlayer());
                }
                else if(coreAbility.getName().toLowerCase().equals("heartregen")) {
                    new HeartRegen(event.getPlayer());
                }
                else if(coreAbility.getName().toLowerCase().equals("matrixblast")) {
                    new MatrixBlast(event.getPlayer());
                }
                else if(coreAbility.getName().toLowerCase().equals("matrixstate")) {
                    new MatrixState(event.getPlayer());
                }

            }
        }
    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        MatrixElement.heartRegenUsed.remove(event.getEntity());
    }

}
