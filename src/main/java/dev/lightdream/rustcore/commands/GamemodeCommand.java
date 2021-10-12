package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand extends SubCommand {
    public GamemodeCommand(@NotNull IAPI api) {
        super(api, Collections.singletonList("gm"), "", "", true, false, "[0/1/2/3]");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(CommandSender commandSender, List<String> args) {

        if(args.size()!=1){
            sendUsage(commandSender);
            return;
        }

        String gamemode = args.get(0);

        User user = Main.instance.databaseManager.getUser(commandSender);

        if (user == null) {
            return;
        }

        switch (gamemode){
            case "1":
            case "c":
                user.getPlayer().setGameMode(GameMode.CREATIVE);
                break;
            case "0":
            case "s":
                user.getPlayer().setGameMode(GameMode.SURVIVAL);
                break;
            case "2":
            case "a":
                user.getPlayer().setGameMode(GameMode.ADVENTURE);
                break;
            case "3":
            case "sp":
                user.getPlayer().setGameMode(GameMode.SPECTATOR);
                break;
            default:
                sendUsage(commandSender);
                return;
        }

        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.changedGamemode);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return new ArrayList<>();
    }
}
