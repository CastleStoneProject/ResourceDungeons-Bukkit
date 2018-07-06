package net.tkarura.resourcedungeons.bukkit;

import net.tkarura.resourcedungeons.core.command.DungeonCommandSender;
import net.tkarura.resourcedungeons.core.command.DungeonGenerateCommand;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.script.DungeonScriptEngine;

import static net.tkarura.resourcedungeons.core.ResourceDungeons.PREFIX_MES;

public class BukkitDungeonGenerateCommand extends DungeonGenerateCommand {

    @Override
    public void generate(DungeonCommandSender sender, DungeonScriptEngine engine) throws DungeonScriptException {

        engine.setScriptLibraryDir(ResourceDungeonsBukkit.getInstance().getJarFile());

        new Thread(() -> {
            try {
                engine.loadScripts();
                engine.callMainFunction();
                ResourceDungeonsBukkit.getInstance().getServerListener().setGenerateHandle(engine);
                sender.sendMessage(PREFIX_MES + " &aダンジョンの生成に成功しました。");
            } catch (DungeonScriptException e) {
                e.printStackTrace();
                sender.sendMessage(PREFIX_MES + " &cダンジョンの生成に失敗しました。 : " + e.getLocalizedMessage());
            }
        }).start();

    }

}
