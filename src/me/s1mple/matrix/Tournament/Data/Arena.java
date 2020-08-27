package me.s1mple.matrix.Tournament.Data;

import org.bukkit.Location;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Arena {
    private Location spawnPoint1;
    private Location spawnPoint2;
    private Location spectatorPoint;

    private boolean isAvailable;

    private String name;

    public Arena(Location spawnPoint1, Location spawnPoint2, Location spectatorPoint, String name) {
        this.spawnPoint1 = spawnPoint1;
        this.spawnPoint2 = spawnPoint2;
        this.spectatorPoint = spectatorPoint;
        isAvailable = true;
        this.name = name;
    }

    public Arena(String name) {
        this(null, null, null, name);
    }

    /**
     * Occupies arena for Tournament
     * @return If the arena could be occupied
     */
    public boolean occupyArena() {
        throw new NotImplementedException();
    }

    /**
     * Sets the arena as free
     */
    public void freeArena() {
        throw new NotImplementedException();
    }

    public Location getSpawnPoint1() {
        return spawnPoint1;
    }

    public void setSpawnPoint1(Location spawnPoint1) {
        // Make sure both spawnpoints are in the same world
        throw new NotImplementedException();
    }

    public Location getSpawnPoint2() {
        return spawnPoint2;
    }

    public void setSpawnPoint2(Location spawnPoint2) {
        // Make sure both spawnpoints are in the same world
        throw new NotImplementedException();
    }

    public Location getSpectatorPoint() {
        return spectatorPoint;
    }

    public void setSpectatorPoint(Location spectatorPoint) {
        // Make sure both spawnpoints are in the same world
        throw new NotImplementedException();
    }
}
