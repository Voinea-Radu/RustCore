package dev.lightdream.rustcore.commands.bans;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.commands.SubCommand;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "banip",
        onlyForPlayers = true,
        usage = "[player] [period] [reason]"
)
public class BanIpCommand extends SubCommand {
    public BanIpCommand(@NotNull IAPI api) {
        super(api);
    }

    @Override
    public void execute(User user, List<String> args) {
        if (args.size() < 3) {
            sendUsage(user);
            return;
        }

        if (user == null) {
            return;
        }

        User target = Main.instance.databaseManager.getUser(args.get(0));

        if (target == null) {
            MessageManager.sendMessage(user, Main.instance.lang.invalidUser);
            return;
        }

        if (target.isBanned()) {
            MessageManager.sendMessage(user, Main.instance.lang.alreadyBanned);
            return;
        }

        String timeString = args.get(1);

        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.size(); i++) {
            reason.append(args.get(i));
        }

        Long period = Utils.stringToPeriod(timeString);
        if (period == null) {
            MessageManager.sendMessage(user, Main.instance.lang.invalidTime);
            return;
        }

        target.ban(user, period, reason.toString(), true);
        MessageManager.sendMessage(user, Main.instance.lang.banned);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
