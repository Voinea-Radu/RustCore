package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.database.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListCommand extends SubCommand {
    public ListCommand(@NotNull IAPI api) {
        super(api, "list", true, false, "");
    }

    @Override
    public void execute(User user, List<String> list) {
        StringBuilder output = new StringBuilder();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            output.append(onlinePlayer.getName()).append(", ");
        }

        output.append(",");
        output = new StringBuilder(output.toString().replace(", ,", ""));
        user.sendMessage(api, output.toString());
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
