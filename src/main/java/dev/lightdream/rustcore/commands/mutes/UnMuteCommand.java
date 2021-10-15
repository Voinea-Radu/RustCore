package dev.lightdream.rustcore.commands.mutes;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.commands.SubCommand;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnMuteCommand extends SubCommand {
    public UnMuteCommand(@NotNull IAPI api) {
        super(api, "unmute", true, false, "[player]");
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
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.invalidUser);
            return;
        }

        if (!target.isMuted()) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.notMuted);
            return;
        }

        target.unmute();
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.unMuted);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
