package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VanishCommand extends SubCommand {
    public VanishCommand(@NotNull IAPI api) {
        super(api, Arrays.asList("vanish", "v"), true, false, "");
    }

    @Override
    public void execute(User user, List<String> list) {
        user.setVanished(!user.vanished);
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.vanishToggled);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return new ArrayList<>();
    }
}
