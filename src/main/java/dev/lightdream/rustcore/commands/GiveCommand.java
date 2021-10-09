package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.rustcore.Main;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GiveCommand extends SubCommand {
    public GiveCommand(@NotNull IAPI api) {
        super(api, Collections.singletonList("give"), "", "", false, false, "[CubBoard/BuildHammer/" +
                "RecyclingTable/PasswordChest] [player]");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        if (args.size() != 2) {
            sendUsage(commandSender);
            return;
        }

        String item = args.get(0).toLowerCase();
        User target = Main.instance.databaseManager.getUser(args.get(1));

        if (target == null) {
            api.getMessageManager().sendMessage(commandSender, Main.instance.lang.invalidUser);
            return;
        }

        if (!target.isOnline()) {
            api.getMessageManager().sendMessage(commandSender, Main.instance.lang.offlineUser);
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
            default:
                api.getMessageManager().sendMessage(commandSender, Main.instance.lang.invalidItem);
                break;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> args) {
        if (args.size() == 1) {
            return Arrays.asList("CubBoard", "BuildHammer","RecyclingTable", "PasswordChest");
        }
        return null;
    }
}
