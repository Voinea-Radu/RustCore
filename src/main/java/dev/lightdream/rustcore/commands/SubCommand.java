package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.databases.User;
import dev.lightdream.rustcore.Main;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class SubCommand extends dev.lightdream.api.commands.SubCommand {
    public SubCommand(@NotNull IAPI api, @NotNull List<String> aliases, @NotNull String description, @NotNull String permission, boolean onlyForPlayers, boolean onlyForConsole, @NotNull String usage) {
        super(api);
    }

    public SubCommand(@NotNull IAPI api) {
        super(api);
    }



    @Override
    public void execute(User user, List<String> list) {
        execute(Main.instance.databaseManager.getUser(user.id), list);
    }

    public abstract void execute(dev.lightdream.rustcore.database.User user, List<String> list);

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return onTabComplete(Main.instance.databaseManager.getUser(user.id), list);
    }

    public abstract List<String> onTabComplete(dev.lightdream.rustcore.database.User user, List<String> list);

}
