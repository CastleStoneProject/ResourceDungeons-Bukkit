package net.tkarura.resourcedungeons.bukkit;

import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.command.CommandManager;
import net.tkarura.resourcedungeons.core.command.DungeonCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BukkitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        ResourceDungeonsBukkit bukkit = ResourceDungeonsBukkit.getInstance();
        ResourceDungeons core = bukkit.getResourceDungeons();
        CommandManager cm = core.getCommandManager();
        BukkitCommandSender bcs = new BukkitCommandSender(commandSender, strings);

        String name = strings.length > 0 ? strings[0] : "help";

        DungeonCommand cmd = cm.getCommand(name);
        DungeonCommand cmd_ = cmd != null ? cmd : cm.getCommand("help");

        cmd_.runCommand(bcs);

        return true;
    }

}
