package net.tkarura.resourcedungeons.bukkit;

import net.tkarura.resourcedungeons.core.generate.DungeonCheckPoint;
import net.tkarura.resourcedungeons.core.generate.DungeonGenerateCheck;
import net.tkarura.resourcedungeons.core.script.DungeonScriptEngine;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class ServerListener extends BukkitRunnable implements Listener {

    private final Deque<DungeonScriptEngine> handles = new ArrayDeque<>();

    public void setGenerateHandle(DungeonScriptEngine engine) {
        this.handles.push(engine);
    }

    @Override
    public void run() {

        if (handles.isEmpty()) {
            return;
        }

        handles.pop().runSessions();

    }

    @EventHandler
    public void onChunkPopulateEvent(ChunkPopulateEvent event) {

        Chunk chunk = event.getChunk();

        IDungeonWorld world = new BukkitWorld(event.getWorld());

        int width = 16;
        int height = event.getWorld().getMaxHeight();
        int length = 16;
        int base_x = chunk.getX() * width;
        int base_y = 0;
        int base_z = chunk.getZ() * length;

        DungeonGenerateCheck check = new DungeonGenerateCheck(
                base_x, base_y, base_z,
                width, height, length
        );
        check.setDungeonManager(ResourceDungeonsBukkit.getInstance().getResourceDungeons().getDungeonManager());
        check.setWorld(world);
        check.search();

        if (check.getCheckPoints().isEmpty()) {
            return;
        }

        List<DungeonCheckPoint> points = check.getCheckPoints();

        Collections.shuffle(points);

        DungeonCheckPoint point = points.get(0);
        DungeonScriptEngine engine = new DungeonScriptEngine(point.getDungeon());
        engine.setBaseLocation(point.getX(), point.getY(), point.getZ());
        engine.setWorld(world);
        engine.setSessionManager(ResourceDungeonsBukkit.getInstance().getResourceDungeons().getSessionManager());
        engine.setScriptLibraryDir(ResourceDungeonsBukkit.getInstance().getJarFile());

        new Thread(() -> {
            try {
                engine.loadScripts();
                engine.callMainFunction();
                setGenerateHandle(engine);
                ResourceDungeonsBukkit.getInstance().getLogger().info(
                        "Dungeon Generated. id: " + engine.getDungeon().getId() +
                                " x: " + point.getX() + " y: " + point.getY() + " z: " + point.getZ()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

}
