package dev.lightdream.rustcore.commands.bans;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.commands.SubCommand;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnbanCommand extends SubCommand {
    public UnbanCommand(@NotNull IAPI api) {
        super(api, "unban", true, false, "[player]");
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

        if (!target.isBanned()) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.notBanned);
            return;
        }

        target.unban();
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.unBanned);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
