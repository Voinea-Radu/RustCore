package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "expert",
        onlyForPlayers = true
)
public class ExpertCommand extends SubCommand {
    public ExpertCommand(@NotNull IAPI api) {
        super(api);
    }

    @Override
    public void execute(User user, List<String> list) {
        user.setBabyGod(false);
        user.sendMessage(Main.instance.lang.expertMode);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
