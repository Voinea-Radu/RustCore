package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VanishCommand extends SubCommand {
    public VanishCommand(@NotNull IAPI api) {
        super(api, Arrays.asList("vanish", "v"), "", "", true, false, "");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> list) {
        User user = Main.instance.databaseManager.getUser(commandSender);

        if (user == null) {
            return;
        }

        user.setVanished(!user.vanished);
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.vanishToggled);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return new ArrayList<>();
    }
}
