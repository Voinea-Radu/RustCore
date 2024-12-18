package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GodCommand extends SubCommand {
    public GodCommand(@NotNull IAPI api) {
        super(api, "god", true, false, "");
    }

    @Override
    public void execute(User user, List<String> list) {
        user.setGod(!user.god);
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.godToggled);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
