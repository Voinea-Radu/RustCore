package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "god",
        onlyForPlayers = true
)
public class GodCommand extends SubCommand {
    public GodCommand(@NotNull IAPI api) {
        super(api);
    }

    @Override
    public void execute(User user, List<String> list) {
        user.setGod(!user.god);
        MessageManager.sendMessage(user, Main.instance.lang.godToggled);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
