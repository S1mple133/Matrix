package me.s1mple.matrix.Tournament.CommandHandler;

import me.s1mple.matrix.Tournament.Data.Tournament;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TournamentCommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
        /tournament help
        /tournament players list tournament.admin lists players from tournament
        /tt arena create <Name> tournament.admin create new tournament arena
        /tt arena setspawnpoint1 <Arena> tournament.admin set spawnpoint of arena
        /tt arena setspawnpoint2 <Arena> tournament.admin set 2nd spawnpoint of arena
        /tt arena setspectatorspawnpoint <Arena> tournament.admin set spectator spawnpoint of arena
        /tt arena list tournament.admin list arenas
        /tt prepare <Arena> <TournamentName> tournament.admin Prepares arena (should be used before adding players)
        /tt start tournament.admin starts tournament, sends “Do /tt accept to join the tournament” to the first set of players
        FOR LATER - /tt prize add <TournamentsWon> <Command> %winner% as placeholder for winner name
        /tt leave Leave arena
         */

        /*
        /tt prepare AirBending1,FireBending1 BEGINNER

           ----- SIGN -----

              BEGINNER

            [ 0 Players ]
          -----------------

        /tt prepare AirBending2,EarthBending1 INTERMEDIATE

           ----- SIGN -----

             INTERMEDIATE

            [ 0 Players ]
          -----------------
         */
        return false;
    }
}
