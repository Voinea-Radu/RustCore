package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GodCommand extends SubCommand {
    public GodCommand(@NotNull IAPI api) {
        super(api, Collections.singletonList("god"), "", "", true, false, "");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> list) {
        User user = Main.instance.databaseManager.getUser(commandSender);

        if (user == null) {
            return;
        }

        user.god = !user.god;
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.godToggled);

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return null;
    }
}
