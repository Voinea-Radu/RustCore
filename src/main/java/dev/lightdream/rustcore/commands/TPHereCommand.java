package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TPHereCommand extends SubCommand {
    public TPHereCommand(@NotNull IAPI api) {
        super(api, "tphere", true, false, "[player]");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> args) {
        if (args.size() != 1) {
            sendUsage(user);
            return;
        }

        dev.lightdream.api.databases.User target = Main.instance.databaseManager.getUser(args.get(0));

        if (target == null) {
            api.getMessageManager().sendMessage(user, Main.instance.lang.invalidUser);
            return;
        }

        if (!target.isOnline()) {
            api.getMessageManager().sendMessage(user, Main.instance.lang.offlineUser);
            return;
        }

        target.getPlayer().teleport(user.getPlayer().getLocation());
        user.sendMessage(api, Main.instance.lang.teleported);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
