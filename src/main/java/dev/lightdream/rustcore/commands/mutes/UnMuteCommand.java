package dev.lightdream.rustcore.commands.mutes;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class UnMuteCommand extends SubCommand {
    public UnMuteCommand(@NotNull IAPI api) {
        super(api, Collections.singletonList("unmute"), "", "", true, false, "[player]");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        if (args.size() != 1) {
            sendUsage(commandSender);
            return;
        }

        User user = Main.instance.databaseManager.getUser(commandSender);

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
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return null;
    }
}
