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
        if(!this.isAvailable)
            return false;

        this.isAvailable = false;
        return true;
    }

    /**
     * Sets the arena as free
     */
    public void freeArena() {
        this.isAvailable = true;
    }

    public Location getSpawnPoint1() {
        return spawnPoint1;
    }

    /**
     * Sets the arena spawn point 1
     * @return false if location not valid
     * @param spawnPoint1
     */
    public boolean setSpawnPoint1(Location spawnPoint1) {
        if(getSpawnPoint2().getWorld().equals(spawnPoint1.getWorld())) {
            this.spawnPoint1 = spawnPoint1;
        }

        return this.spawnPoint1 != null;
    }

    public Location getSpawnPoint2() {
        return spawnPoint2;
    }

    /**
     * Sets the second arena spawn point
     * @param spawnPoint2
     * @return
     */
    public boolean setSpawnPoint2(Location spawnPoint2) {
        if(getSpawnPoint1().getWorld().equals(spawnPoint2.getWorld())) {
            this.spawnPoint2 = spawnPoint2;
        }

        return this.spawnPoint2 != null;
    }

    public Location getSpectatorPoint() {
        return spectatorPoint;
    }

    public void setSpectatorPoint(Location spectatorPoint) {
        this.spectatorPoint = spectatorPoint;
    }

    public boolean isOccupied() {
        return !isAvailable;
    }
}
