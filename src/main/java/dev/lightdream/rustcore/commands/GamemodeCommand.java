package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GamemodeCommand extends SubCommand {
    public GamemodeCommand(@NotNull IAPI api) {
        super(api, "gm", true, false, "[0/1/2/3]");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> args) {
        if (args.size() != 1) {
            sendUsage(user);
            return;
        }

        String gamemode = args.get(0);

        if (user == null) {
            return;
        }

        switch (gamemode) {
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
                sendUsage(user);
                return;
        }

        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.changedGamemode);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
