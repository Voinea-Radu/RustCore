package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "weather",
        usage = "[rain / sun]"
)
public class WeatherCommand extends SubCommand {
    public WeatherCommand(@NotNull IAPI api) {
        super(api);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> args) {
        if (args.size() != 1) {
            sendUsage(user);
            return;
        }

        String type = args.get(0);

        if (type.equalsIgnoreCase("rain")) {
            user.getPlayer().getWorld().setStorm(true);
        } else if (type.equalsIgnoreCase("sun")) {
            user.getPlayer().getWorld().setStorm(false);
        }

        MessageManager.sendMessage(user, Main.instance.lang.weatherChanged);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
