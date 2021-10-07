package me.s1mple.matrix.Tournament.CommandHandler;

import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Tournament.Data.PlayerData;
import me.s1mple.matrix.Tournament.Data.Tournament;
import me.s1mple.matrix.Tournament.Data.Arena;
import me.s1mple.matrix.Tournament.TournamentHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TournamentCommandHandler implements CommandExecutor {
    private String helpMessage = ChatColor.translateAlternateColorCodes('&', "&a ===== Tournament Commands (alias: /tt) =====\n" +
            "&a * /tournament info <TournamentName> &b Shows tournament info\n" +
            "&a * /tournament help &b Shows the help page\n" +
            "&a * /tournament leave &b Leave the tournament\n" +
            "&a * /tournament stats &b Shows you your stats\n" +
//            "&a * To join a tournament, wait until an admin starts one and then do /warp tournament.\n"+
            "&a * /tournament join <TournamentName> &b joins tournament");
    private String helpMessageAdmin = ChatColor.translateAlternateColorCodes('&', "&a * /tt arena create <Name> <SchemFileName> &b Starts an arena creation session. You need to be on the place where the schem file will be pasted. \n" +
            "&a * /tt arena setspawnpoint1 &bSets first spawnpoint\n" +
            "&a * /tt arena setspawnpoint2 &bSets second spawnpoint\n" +
            "&a * /tt arena setspectatorpoint &bSets spectator spawnpoint\n" +
            "&a * /tt arena save &bSaves arena\n" +
            "&a * /tt arena list &bLists arenas\n" +
            "&a * /tt setlobby &bSets lobby for tournament\n" +
            "&a * /tt prepare <Arenas> <TournamentName> &bStarts tournament, waiting for people to join ex. /tt prepare MyArena1,MyArena2 MyTournamentName\n" +
            "&a * /tt start <TournamentName> &bStarts tournament.");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
        ##/tournament help
        ##/tournament info TournamentName tournament.admin lists players from tournament
        ##/tt arena create <Name> <SchemFileName> tournament.admin start session to create new tournament arena, creator has to be on the location where to paste the SchemFileName
        ##/tt arena setspawnpoint1 tournament.admin set spawnpoint of arena, needs to be in session
        ##/tt arena setspawnpoint2 tournament.admin set 2nd spawnpoint of arena, needs to be in session
        ##/tt arena setspectatorpoint tournament.admin set spectator spawnpoint of arena, needs to be in session
        ##/tt arena save tournament.admin saves arena
        ##/tt arena list tournament.admin list arenas
        ##/tt prepare <Arena> <TournamentName> tournament.admin Prepares arena (should be used before adding players)
        /tt setlobby
        ## /tt start TournamentName tournament.admin starts tournament, sends &Do /tt accept to join the tournament& to the first set of players
        /tt prize add <TournamentsWon> <Command> %winner% as placeholder for winner name
        ##/tt leave Leave arena
        ##/tt join <TournamentName>
         */

        if(!command.getName().equalsIgnoreCase("tournament"))
            return false;

        if(args.length == 0) {
            sender.sendMessage(helpMessage);

            if(sender.hasPermission("tournament.admin"))
                sender.sendMessage(helpMessageAdmin);

            return true;
        }
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(helpMessage);

                if(sender.hasPermission("tournament.admin"))
                    sender.sendMessage(helpMessageAdmin);

                return true;
            }
            else if(args[0].equalsIgnoreCase("stats")) {
                sender.sendMessage(TournamentHandler.getPlayerData((Player) sender).toString());
                return true;
            }
            else if(args[0].equalsIgnoreCase("leave")) {
                PlayerData pd = TournamentHandler.getPlayerData((Player)sender);
                if(pd == null || !pd.inTournament()) {
                    sender.sendMessage(ChatColor.RED + "You're not partitipating in any tournament");
                    return false;
                }

                TournamentHandler.removeParticipatorFromTournament((Player)sender);
                sender.sendMessage(ChatColor.RED + "You left the tournament!");
                return true;
            }
            else if(args[0].equalsIgnoreCase("setlobby") && sender.hasPermission("tournament.admin")) {
                if(TournamentHandler.saveTournamentLobby(((Player)sender).getLocation())) {
                    sender.sendMessage(ChatColor.GREEN + "Successfully saved lobby!");
                }
                else {
                    sender.sendMessage(ChatColor.RED + "Failed to save lobby!");
                }

                return true;
            }
        }
        else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("join")) {
                if(!TournamentHandler.existsTournament(args[1])) {
                    sender.sendMessage(ChatColor.RED + "Tournament " + args[1] + " does not exist.");
                    return false;
                }
                else if(TournamentHandler.getPlayerData((Player) sender).inTournament()) {
                    sender.sendMessage(ChatColor.RED + "You are already participating in a tournament!");
                    return false;
                }

                TournamentHandler.getTournament(args[1]).addParticipator((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "Successfully joined " + args[1]);
                return true;
            }
            if(args[0].equalsIgnoreCase("start") && sender.hasPermission("tournament.admin")) {
                Tournament t = TournamentHandler.getTournament(args[1]);

                if(t == null || t.started()) {
                    sender.sendMessage(ChatColor.RED + "Could not start tournament! It either doesnt exist or it was already started.");
                    return false;
                }

                if(t.startRound())
                    sender.sendMessage(ChatColor.GREEN + "Tournament started!");
                else
                    sender.sendMessage(ChatColor.RED + "Could not start tournament! Player amount not even");
                return true;
            }
            else if(args[0].equalsIgnoreCase("info")) {
                Tournament t = TournamentHandler.getTournament(args[1]);
                if(t == null) {
                    sender.sendMessage(ChatColor.RED + "Tournament doesnt exist!");
                    return false;
                }
                else {
                    sender.sendMessage(t.toString());
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("arena") && sender.hasPermission("tournament.admin")) {
                if(args[1].equalsIgnoreCase("list")) {
                    List<Arena> arenas = TournamentHandler.getArenas();
                    int size = TournamentHandler.getArenas().size();
                    StringBuilder message = new StringBuilder(ChatColor.AQUA + "Occupied arenas are red, unoccupied arenas show up green\n");

                    for(int i = 0; i < size; i++) {
                        message.append(arenas.get(i).isOccupied() ? ChatColor.RED : ChatColor.GREEN).append(arenas.get(i).getName());
                        if(i != size-1)
                            message.append(", ");
                    }

                    sender.sendMessage(message.toString());
                    return true;
                }
                else if(TournamentHandler.getArenaOfPlayerInCreation((Player) sender) == null) {
                    sender.sendMessage(ChatColor.RED + "You are not creating any arena! DO /arena create before using this command!");
                    return false;
                }


                if(args[1].equalsIgnoreCase("setspawnpoint1")) {
                    sender.sendMessage(TournamentHandler.getArenaOfPlayerInCreation((Player)sender).setSpawnPoint1(((Player) sender).getLocation()) ? ChatColor.GREEN + "Success!" : ChatColor.RED + "Failure!");
                    return true;
                }
                else if(args[1].equalsIgnoreCase("setspawnpoint2")) {
                    sender.sendMessage(TournamentHandler.getArenaOfPlayerInCreation((Player)sender).setSpawnPoint2(((Player) sender).getLocation()) ? ChatColor.GREEN + "Success!" : ChatColor.RED + "Failure!");
                    return true;
                }
                else if(args[1].equalsIgnoreCase("setspectatorpoint")) {
                    TournamentHandler.getArenaOfPlayerInCreation((Player)sender).setSpectatorPoint(((Player) sender).getLocation());
                    sender.sendMessage(ChatColor.GREEN + "Success!");
                    return true;
                }
                else if(args[1].equalsIgnoreCase("save")) {
                    Arena ar = TournamentHandler.getArenaOfPlayerInCreation((Player)sender);

                    if(!TournamentHandler.saveArena(ar, (Player)sender)) {
                        sender.sendMessage(ChatColor.RED + "Cannot save arena! Some spawnpoints were not set!");
                        return false;
                    }

                    sender.sendMessage(ChatColor.GREEN + "Success!");
                    return true;
                }
            }
        }
        else if(args.length == 3 && sender.hasPermission("tournament.admin") && args[0].equalsIgnoreCase("prepare")) {
            String[] arenaNames = args[1].split(",");
            List<Arena> arenas = new ArrayList<>();

            if(TournamentHandler.getTournamentLobby() == null) {
                sender.sendMessage(ChatColor.RED + "The tournament lobby is not set! Set it by doin /tt setlobby");
                return false;
            }

            for(String arenaName : arenaNames) {
                if(TournamentHandler.getArena(arenaName) == null || TournamentHandler.getArena(arenaName).isReserved()) {
                    sender.sendMessage(ChatColor.RED + "Arena " + arenaName + " doesnt exist or is not available !");
                    return false;
                }
                else {
                    arenas.add(TournamentHandler.getArena(arenaName));
                }
            }

            TournamentHandler.createTournament(args[2], arenas);
            sender.sendMessage(ChatColor.GREEN + "Arena Preparation started!");
            Matrix.getPlugin().getServer().broadcastMessage(ChatColor.AQUA + "Tournament " + args[2] + " started! Do /tt join " + args[2] + " to join!");
            return true;
        }
        else if(args.length == 4  && sender.hasPermission("tournament.admin") && args[0].equalsIgnoreCase("arena") && args[1].equalsIgnoreCase("create")) {
            sender.sendMessage(ChatColor.RED + String.format("You started creation of %s", args[2]));
            TournamentHandler.createArenaInCreation((Player)sender, new Arena(args[2], args[3], ((Player)(sender)).getLocation()));
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Unknown command or wrong syntax! Use /tt help for help.");


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

          TODO: TOURNAMENT NEEDS LOBBY
          TODO: When player joined tournament, broadcast message that someone joined
         */
        return false;
    }
}
