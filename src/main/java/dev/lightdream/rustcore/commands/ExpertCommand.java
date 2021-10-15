package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExpertCommand extends SubCommand {
    public ExpertCommand(@NotNull IAPI api) {
        super(api, "expert", true, false, "");
    }

    @Override
    public void execute(User user, List<String> list) {
        user.setBabyGod(false);
        user.sendMessage(api, Main.instance.lang.expertMode);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
