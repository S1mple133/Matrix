package me.s1mple.matrix.Tournament;

import org.bukkit.ChatColor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Messages {
    public static final String PREFIX = "TOURNAMENT |";
    public static String LOOSER_MESSAGE = ChatColor.RED + "You lost the battle! If you stay online, you will be assigned to the loosers tournament.";
    public static String ROUND_WON = ChatColor.AQUA + "%s won round against %s";
    public static String TOURNAMENT_WON = ChatColor.AQUA + "%s won the tournament!";
    public static String TOURNAMENT_LOOSER_WON = ChatColor.AQUA + "%s won the losers tournament!";
    public static String STARTING_LOSERS_TOURNAMENT = ChatColor.AQUA + "Losers tournament is starting . . .";
    public static String TOURNAMENT_WON_PLAYER = ChatColor.GREEN + "You won the round!";
    public static String ROUND_SKIP_PREFIX = "" + ChatColor.AQUA;
    public static String ROUND_SKIP_SUFFIX = " will skip the round, uneven amount of players.";
    public static String JOIN_TOURNAMENT_MSG = ChatColor.GREEN + "Joining Tournament . . .!";
    public static String NOT_IN_TOURNAMENT = ChatColor.RED + "You're not partitipating in any tournament";
    public static String LEFT_TOURNAMENT = ChatColor.RED + "You left the tournament!";
    public static String SAVED_LOBBY = ChatColor.GREEN + "Successfully saved lobby!";
    public static String FAILED_SETTING_LOBBY = ChatColor.RED + "Failed to save lobby!";
    public static String TOURNAMENT_NONEXISTENT = ChatColor.RED + "Tournament %tournament% does not exist.";
    public static String ALREADY_IN_TOURNAMENT = ChatColor.RED + "You are already participating in a tournament!";
    public static String JOINED_TOURNAMENT = ChatColor.GREEN + "Successfully joined %tournament%";
    public static String TOURNAMENT_CANNOT_BE_STARTED = ChatColor.RED + "Could not start tournament! It either doesnt exist or it was already started.";
    public static String TOURNAMENT_STARTED = ChatColor.GREEN + "Tournament started!";
    public static String TOURNAMENT_PLAYERAMOUNT_UNEVEN = ChatColor.RED + "Could not start tournament! Player amount not even";
    public static String TOURNAMENT_UNEXISTENT = ChatColor.RED + "Tournament doesnt exist!";
    public static String TOURNAMENT_STARTED_BROADCAST = ChatColor.AQUA + "Tournament %tournament% started! Do /tt join %tournament% to join!";

    public static void init() {
        try {
            for (Field f : Messages.class.getDeclaredFields()) {
                if (!f.getName().equals("PREFIX")) {
                    f.set(null, ChatColor.translateAlternateColorCodes('&', PREFIX + f.get(null)));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
