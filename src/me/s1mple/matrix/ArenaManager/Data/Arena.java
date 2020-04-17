package me.s1mple.matrix.ArenaManager.Data;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.AbstractWorld;
import me.s1mple.matrix.ArenaManager.ArenaManager;
import me.s1mple.matrix.Matrix;
import me.s1mple.matrix.Util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private static List<Arena> arenaList = new ArrayList<>();
    private String name;
    private File schem;
    private Location pastePosition;

    public Arena(String name, String schemFileName, Location loc) {
        this.name = name;
        this.schem = Util.getSchematicFile(schemFileName);
        this.pastePosition = loc;
    }

    public Arena(String name, File schemFile, Location loc) {
        this.name = name;
        this.schem = schemFile;
        this.pastePosition = loc;
    }

    /**
     * Gets the list of Arenas
     * @return
     */
    public static List<Arena> getArenas() {
        return arenaList;
    }

    /**
     * Creates an Arena
     * @param name
     * @param schem
     * @param pos
     */
    public static void createArena(String name, File schem, Location pos) {
        Arena arena = new Arena(name, schem, pos);
        FileConfiguration config = ArenaManager.getConfig();
        int id = arenaList.size();
        config.set("Arenas."+id+".Name", name);
        config.set("Arenas."+id+".Schematic", schem.getName());
        config.set("Arenas."+id+".World", pos.getWorld().getName());
        config.set("Arenas."+id+".X", pos.getX());
        config.set("Arenas."+id+".Y", pos.getY());
        config.set("Arenas."+id+".Z", pos.getZ());
        ArenaManager.saveConfig();
        addArena(arena);
    }

    /**
     * Returns the arena with the name
     * Null if not found
     * @param name
     * @return
     */
    public static Arena getArena(String name) {
        for(Arena arena : arenaList) {
            if(arena.name.equalsIgnoreCase(name))
                return arena;
        }

        return null;
    }

    /**
     * Adds an Arena to the List
     * @param arena
     */
    public static void addArena(Arena arena) {
        if(!arenaList.contains(arena))
            arenaList.add(arena);
    }

    /**
     * Name of the Arena
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Reset the arena
     */
    public void reset() {
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "/world " + pastePosition.getWorld().getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "/schem load " + schem.getName());
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "/paste -o");
    }
}
