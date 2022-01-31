package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.rustcore.Main;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command = "give",
        usage = "[CubBoard/BuildHammer/RecyclingTable/PasswordChest/BigFurnace] [player]"
)
public class GiveCommand extends SubCommand {
    public GiveCommand(@NotNull IAPI api) {
        super(api);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(dev.lightdream.rustcore.database.User user, List<String> args) {
        if (args.size() != 2) {
            sendUsage(user);
            return;
        }

        String item = args.get(0).toLowerCase();
        User target = Main.instance.databaseManager.getUser(args.get(1));

        if (target == null) {
            MessageManager.sendMessage(user, Main.instance.lang.invalidUser);
            return;
        }

        if (!target.isOnline()) {
            MessageManager.sendMessage(user, Main.instance.lang.offlineUser);
            return;
        }

        switch (item) {
            case "cubboard":
                target.getPlayer().getInventory().addItem(ItemBuilder.makeItem(Main.instance.config.cubBoardItem));
                break;
            case "buildhammer":
                target.getPlayer().getInventory().addItem(ItemBuilder.makeItem(Main.instance.config.buildHammerItem));
                break;
            case "recyclingtable":
                target.getPlayer().getInventory().addItem(ItemBuilder.makeItem(Main.instance.config.recyclingTableItem));
                break;
            case "passwordchest":
                target.getPlayer().getInventory().addItem(ItemBuilder.makeItem(Main.instance.config.passwordChestItem));
                break;
            case "bigfurnace":
                target.getPlayer().getInventory().addItem(ItemBuilder.makeItem(Main.instance.config.bigFurnace));
                break;
            default:
                MessageManager.sendMessage(user, Main.instance.lang.invalidItem);
                break;
        }
    }

    @Override
    public List<String> onTabComplete(dev.lightdream.rustcore.database.User user, List<String> args) {
        if (args.size() == 1) {
            return Arrays.asList("CubBoard", "BuildHammer", "RecyclingTable", "PasswordChest", "BigFurnace");
        }
        return null;
    }
}
