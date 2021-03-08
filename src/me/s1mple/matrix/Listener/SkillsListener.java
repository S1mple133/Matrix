package me.s1mple.matrix.Listener;


import com.clanjhoo.vampire.event.VampireTypeChangeEvent;
import me.s1mple.matrix.Matrix;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.skills.api.SkillsAPI;
import org.skills.types.SkillManager;

public class SkillsListener implements Listener {
    LuckPerms api = Matrix.getPlugin().getLuckPerms();

    @EventHandler
    public void vampCmdListener(VampireTypeChangeEvent event) {
        Player player = event.getUplayer().getPlayer();

        if (event.isVampire()) {
            SkillsAPI.getSkilledPlayer(player).setActiveSkill(SkillManager.getSkill("Vampire"));
            event.getUplayer().getPlayer().sendRawMessage("§cYou have unlocked the §4Vampire§c skill tree. §4Do /skills improve");
            api.getUserManager().getUser(player.getUniqueId()).data().add(Node.builder("-skills.select.*").build());
        } else {
            SkillsAPI.getSkilledPlayer(player).setActiveSkill(SkillManager.getSkill("Arbalist"));
            event.getUplayer().getPlayer().sendRawMessage("§cYou have been removed from the Vampire skill tree!");
            api.getUserManager().getUser(player.getUniqueId()).data().remove(Node.builder("-skills.select.*").build());
        }

        api.getUserManager().saveUser(api.getUserManager().getUser(player.getUniqueId()));
    }

}
