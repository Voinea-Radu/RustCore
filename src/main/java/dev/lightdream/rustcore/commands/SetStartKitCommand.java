package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SetStartKitCommand extends SubCommand {
    public SetStartKitCommand(@NotNull IAPI api) {
        super(api, "setstartkit", true, false, "");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> list) {

        List<String> output = new ArrayList<>();

        for (ItemStack itemStack : user.getPlayer().getInventory()) {
            output.add(ItemBuilder.serialize(itemStack));
        }

        Main.instance.data.startKit = output;
        user.sendMessage(api, Main.instance.lang.kitSet);
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
