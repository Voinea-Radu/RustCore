package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TimeCommand extends SubCommand {
    public TimeCommand(@NotNull IAPI api) {
        super(api, "time", true, false, "[ticks]");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> args) {
        if (args.size() != 1) {
            sendUsage(user);
            return;
        }

        try {
            int ticks = Integer.parseInt(args.get(0));
            user.getPlayer().getWorld().setTime(ticks);
        } catch (NumberFormatException e) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.invalidNumber);
            return;
        }

        api.getMessageManager().sendMessage(user, Main.instance.lang.timeChanged);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
