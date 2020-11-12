package me.s1mple.matrix.ArenaManager.Commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import me.s1mple.matrix.ArenaManager.Data.Arena;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Path;

import javax.annotation.Nonnull;

public class AMCommand implements CommandExecutor {
    private Matrix plugin;

    private String helpMessage = ChatColor.BLUE + "/am create <schematic_name> <name>: " + ChatColor.AQUA + " Creates arena (IMPORTANT: )\n" +
            ChatColor.AQUA + "1. Create a schematic and note the name. "+
            ChatColor.AQUA + "1a. (you can skip this step if the schematic already exists) "+
            ChatColor.AQUA + "4. do /am create <name of the schematic> <name of the arena>. Done! "+
            ChatColor.BLUE + "/am list: " + ChatColor.AQUA + "List arenas\n" +
            ChatColor.BLUE + "/am reset <name>: " + ChatColor.AQUA + "reset arena";

    public AMCommand(Matrix plugin) {
        super();

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if(command.getName().equalsIgnoreCase("arenamanager")) {
            if(sender.hasPermission("matrix.arenamanager")) {
                if (args.length == 0) {
                    sender.sendMessage(helpMessage);
                    return true;
                }
                else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("help")) {
                        sender.sendMessage(helpMessage);
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("list")) {
                        for(Arena arena : Arena.getArenas()) {
                            sender.sendMessage(ChatColor.BLUE + "* " + ChatColor.AQUA + arena.getName());
                        }
                        return true;
                    }
                }
                else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("reset")) {
                        Arena arena = Arena.getArena(args[1]);

                        if(arena == null) {
                            sender.sendMessage(ChatColor.RED + "Arena does not exist!");
                            return false;
                        }
                        else{
                            arena.reset();
                            sender.sendMessage(ChatColor.GREEN + "Success!");
                            return true;
                        }
                    }

                }
                else if(args.length == 3) {
                    if(args[0].equalsIgnoreCase("create")) {
                        File schem = Util.getSchematicFile(args[1]);
                        if(!schem.exists()) {
                            sender.sendMessage(ChatColor.RED + "Schematic does not exist!");
                            return false;
                        }
                        else{
                            Arena.createArena(args[2], schem, ((Player)sender).getLocation());
                            sender.sendMessage(ChatColor.GREEN + "Success!");
                            return true;
                        }
                    }
                }
                else
                    sender.sendMessage(ChatColor.RED + "Unknown command! Use /am help for help.");
            }
            else
                sender.sendMessage(ChatColor.RED + "No permissions!");
        }

        return false;
    }
}
