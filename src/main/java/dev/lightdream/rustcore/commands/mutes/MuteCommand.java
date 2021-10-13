package dev.lightdream.rustcore.commands.mutes;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.Ban;
import dev.lightdream.rustcore.database.Mute;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.utils.Utils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MuteCommand extends SubCommand {
    public MuteCommand(@NotNull IAPI api) {
        super(api, Collections.singletonList("mute"), "", "", true, false, "[player] [period] [reason]");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        if (args.size() < 3) {
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

        if (target.isMuted()) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.alreadyMuted);
            return;
        }

        String timeString = args.get(1);

        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.size(); i++) {
            reason.append(args.get(i));
        }

        Long duration = Utils.stringToPeriod(timeString);
        if (duration == null) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.invalidTime);
            return;
        }

        target.mute(user, duration, reason.toString());
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.muted);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return null;
    }
}
