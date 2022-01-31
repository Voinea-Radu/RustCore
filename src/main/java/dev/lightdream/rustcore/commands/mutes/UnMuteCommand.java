package dev.lightdream.rustcore.commands.mutes;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.commands.SubCommand;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "unmute",
        onlyForPlayers = true,
        usage = "[player]"
)
public class UnMuteCommand extends SubCommand {
    public UnMuteCommand(@NotNull IAPI api) {
        super(api);
    }

    @Override
    public void execute(User user, List<String> args) {
        if (args.size() != 1) {
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

        if (!target.isMuted()) {
            MessageManager.sendMessage(user, Main.instance.lang.notMuted);
            return;
        }

        target.unmute();
        MessageManager.sendMessage(user, Main.instance.lang.unMuted);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
