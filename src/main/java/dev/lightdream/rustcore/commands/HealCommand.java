package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HealCommand extends SubCommand {
    public HealCommand(@NotNull IAPI api) {
        super(api, "heal", true, false, "");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> list) {
        user.getPlayer().setHealth(user.getPlayer().getMaxHealth());
        user.getPlayer().setSaturation(20);
        user.getPlayer().setFoodLevel(20);

        api.getMessageManager().sendMessage(user, Main.instance.lang.healed);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
