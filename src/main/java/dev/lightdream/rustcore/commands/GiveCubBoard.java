package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.rustcore.Main;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class GiveCubBoard extends SubCommand {
    public GiveCubBoard(@NotNull IAPI api) {
        super(api, Collections.singletonList("giveCubBoard"), "", "", false, false, "[player]");
    }

    @Override
    public void execute(CommandSender commandSender, List<String> args) {
        if(args.size()!=1){
            sendUsage(commandSender);
            return;
        }
        User target = Main.instance.databaseManager.getUser(args.get(0));
        if(target==null){
            api.getMessageManager().sendMessage(commandSender, Main.instance.lang.invalidUser);
            return;
        }
        if(!target.isOnline()){
            api.getMessageManager().sendMessage(commandSender, Main.instance.lang.offlineUser);
            return;
        }
        target.getPlayer().getInventory().addItem(ItemBuilder.makeItem(Main.instance.config.cubBoardItem));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, List<String> list) {
        return null;
    }
}
