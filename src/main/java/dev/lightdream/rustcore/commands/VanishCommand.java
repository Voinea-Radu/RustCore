package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "vanish",
        onlyForPlayers = true,
        aliases = "v"
)
public class VanishCommand extends SubCommand {
    public VanishCommand(@NotNull IAPI api) {
        super(api);
    }

    @Override
    public void execute(User user, List<String> list) {
        user.setVanished(!user.vanished);
        MessageManager.sendMessage(user, Main.instance.lang.vanishToggled);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
