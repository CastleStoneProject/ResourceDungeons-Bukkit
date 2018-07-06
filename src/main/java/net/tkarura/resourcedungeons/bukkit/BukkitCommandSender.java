package net.tkarura.resourcedungeons.bukkit;

import net.tkarura.resourcedungeons.core.command.DungeonCommandSender;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BukkitCommandSender implements DungeonCommandSender {

    private CommandSender sender;
    private Player player;
    private String[] args;

    public BukkitCommandSender(CommandSender sender, String[] args) {
        this.sender = sender;
        this.player = sender instanceof Player ? (Player) sender : null;
        this.args = args;
    }

    @Override
    public void sendMessage(String s) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    @Override
    public UUID getUUID() {
        return player != null ? player.getUniqueId() : null;
    }

    @Override
    public String getName() {
        return sender.getName();
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public boolean hasPermission(String s) {
        return sender.hasPermission(s);
    }

    @Override
    public IDungeonWorld getWorld() {
        return player != null ? new BukkitWorld(player.getWorld()) : null;
    }

    @Override
    public int getX() {
        return player != null ? player.getLocation().getBlockX() : 0;
    }

    @Override
    public int getY() {
        return player != null ? player.getLocation().getBlockY() : 0;
    }

    @Override
    public int getZ() {
        return player != null ? player.getLocation().getBlockZ() : 0;
    }

}
