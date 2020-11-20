package me.s1mple.matrix.Tournament.Data;

import com.sk89q.worldedit.entity.Player;
import me.s1mple.matrix.Tournament.TournamentHandler;
import org.bukkit.ChatColor;

public class Round {
    private PlayerData playerData1;
    private PlayerData playerData2;
    private Arena arena;
    int roundId;

    public Round(int id, PlayerData playerData1, PlayerData playerData2) {
        this.playerData1 = playerData1;
        this.playerData2 = playerData2;
        this.roundId = id;
    }

    public PlayerData getPlayerData1() {
        return playerData1;
    }

    public PlayerData getPlayerData2() {
        return playerData2;
    }

    public Arena getArena() {
        return arena;
    }

    public void start(Arena arena, Tournament t) {
        arena.occupyArena();
        this.arena = arena;
        TournamentHandler.playersInGame.put(playerData1.getPlayer(), t);
        TournamentHandler.playersInGame.put(playerData2.getPlayer(), t);

        TournamentHandler.teleportPlayerWithMsg(playerData1.getPlayer(), arena.getSpawnPoint1(), ChatColor.AQUA + "You will fight " + playerData2.getPlayer().getName());
        TournamentHandler.teleportPlayerWithMsg(playerData2.getPlayer(), arena.getSpawnPoint2(), ChatColor.AQUA + "You will fight " + playerData1.getPlayer().getName());
    }
}
