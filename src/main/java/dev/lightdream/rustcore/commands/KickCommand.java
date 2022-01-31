package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "kick",
        onlyForPlayers = true,
        usage = "[player]"
)
public class KickCommand extends SubCommand {

    public KickCommand(@NotNull IAPI api) {
        super(api);
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
            MessageManager.sendMessage(user, Main.instance.lang.invalidUser);
            return;
        }

        if (!target.isOnline()) {
            MessageManager.sendMessage(user, Main.instance.lang.offlineUser);
            return;
        }

        target.getPlayer().kickPlayer(Main.instance.lang.kickedMessage);
        user.sendMessage(Main.instance.lang.kicked);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
