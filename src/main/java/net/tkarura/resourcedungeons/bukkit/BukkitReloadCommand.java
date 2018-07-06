package net.tkarura.resourcedungeons.bukkit;

import net.tkarura.resourcedungeons.core.command.DungeonCommand;
import net.tkarura.resourcedungeons.core.command.DungeonCommandSender;
import net.tkarura.resourcedungeons.core.exception.DungeonCommandException;

import static net.tkarura.resourcedungeons.core.ResourceDungeons.PREFIX_MES;

public class BukkitReloadCommand extends DungeonCommand {

    public BukkitReloadCommand() {
        super("reload");
        description = "設定の再読込をします。";
    }

    @Override
    public void execute(DungeonCommandSender sender) throws DungeonCommandException {
        sender.sendMessage(PREFIX_MES + " &6リロードをします。");
        ResourceDungeonsBukkit.getInstance().initResourceDungeons();
        sender.sendMessage(PREFIX_MES + " &aリロードが終わりました。");
    }
}
