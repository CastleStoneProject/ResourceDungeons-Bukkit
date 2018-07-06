package net.tkarura.resourcedungeons.bukkit;

import net.tkarura.resourcedungeons.core.ResourceDungeons;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ResourceDungeonsBukkit extends JavaPlugin {

    private static ResourceDungeonsBukkit instance;

    private ResourceDungeons resourceDungeons = new ResourceDungeons();

    private BukkitCommand bukkitCommand = new BukkitCommand();
    private ServerListener listener = new ServerListener();

    private ClassLoader classLoader;

    @Override
    public void onEnable() {

        instance = this;
        classLoader = getClassLoader();

        initResourceDungeons();

        getCommand("ResourceDungeons").setExecutor(bukkitCommand);

        getServer().getPluginManager().registerEvents(listener, this);
        listener.runTaskTimer(this, 0, 1);

        getLogger().info("plugin has enabled.");

    }

    public void initResourceDungeons() {

        resourceDungeons.setLogger(this.getLogger());

        File dungeons_dir = new File(getDataFolder(), "Dungeons");
        if (!dungeons_dir.exists()) {
            dungeons_dir.mkdirs();
        }

        resourceDungeons.setDungeonDirectory(dungeons_dir);
        resourceDungeons.init();

        BukkitDungeonGenerateCommand bdgc = new BukkitDungeonGenerateCommand();
        bdgc.setDungeonManager(resourceDungeons.getDungeonManager());
        bdgc.setSessionManager(resourceDungeons.getSessionManager());
        resourceDungeons.getCommandManager().register(bdgc);
        resourceDungeons.getCommandManager().register(new BukkitReloadCommand());

    }

    @Override
    public void onDisable() {

        getLogger().info("plugin has disabled.");

    }

    public ResourceDungeons getResourceDungeons() {
        return this.resourceDungeons;
    }

    public static ResourceDungeonsBukkit getInstance() {
        return instance;
    }

    public ServerListener getServerListener() {
        return this.listener;
    }

    public ClassLoader getPluginClassLoader() {
        return this.classLoader;
    }

    public File getJarFile() {
        return this.getFile();
    }

}
