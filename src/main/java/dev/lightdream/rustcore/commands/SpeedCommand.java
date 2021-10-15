package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpeedCommand extends SubCommand {
    public SpeedCommand(@NotNull IAPI api) {
        super(api, "speed", true, false, "[amount]");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> args) {
        if (args.size() != 1) {
            sendUsage(user);
            return;
        }

        String amountString = args.get(0);
        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException e) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.invalidNumber);
            return;
        }

        if (user.getPlayer().isFlying()) {
            user.getPlayer().setFlySpeed(getSpeed(amount, true));
        } else {
            user.getPlayer().setWalkSpeed(getSpeed(amount, false));
        }

        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.speedChanged);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }

    private float getSpeed(float speed, boolean fly) {
        final float defaultSpeed = fly ? 0.1f : 0.2f;
        float maxSpeed = 1f;

        if (speed < 1f) {
            return defaultSpeed * speed;
        }
        final float ratio = ((speed - 1) / 9) * (maxSpeed - defaultSpeed);
        return ratio + defaultSpeed;

    }
}
