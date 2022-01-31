package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "heal",
        onlyForPlayers = true
)
public class HealCommand extends SubCommand {
    public HealCommand(@NotNull IAPI api) {
        super(api);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> list) {
        user.getPlayer().setHealth(user.getPlayer().getMaxHealth());
        user.getPlayer().setSaturation(20);
        user.getPlayer().setFoodLevel(20);

        MessageManager.sendMessage(user, Main.instance.lang.healed);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
