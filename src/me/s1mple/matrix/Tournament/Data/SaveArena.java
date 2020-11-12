package me.s1mple.matrix.Tournament.Data;

/**
 * Class used to save to file
 */

import java.util.Map;

public class SaveArena {
    public Map<String, Object> getSpawnpoint1() {
        return spawnpoint1;
    }

    public Map<String, Object> getSpawnpoint2() {
        return spawnpoint2;
    }

    public Map<String, Object> getSpectatorPoint() {
        return spectatorPoint;
    }

    public String getName() {
        return name;
    }

    private Map<String, Object> spawnpoint1;
    private Map<String, Object> spawnpoint2;
    private Map<String, Object> spectatorPoint;
    private String name;

    public SaveArena(Map<String, Object> spawnpoint1, Map<String, Object> spawnpoint2, Map<String, Object> spectatorPoint, String name) {
        this.spawnpoint1 = spawnpoint1;
        this.spawnpoint2 = spawnpoint2;
        this.spectatorPoint = spectatorPoint;
        this.name = name;
    }
}
