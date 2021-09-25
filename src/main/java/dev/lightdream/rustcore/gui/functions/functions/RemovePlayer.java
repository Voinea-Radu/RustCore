package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.gui.functions.GUIFunction;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;

public class RemovePlayer implements GUIFunction {
    @Override
    public void execute(GUI gui, User user, MessageBuilder args) {
        String targetName = args.getBaseString();

        if (targetName == null) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.invalidUser);
            return;
        }

        User target = Main.instance.databaseManager.getUser(targetName);

        if (target == null) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.invalidUser);
            return;
        }

        CubBoard cubBoard = (CubBoard) gui.getArgs().get(CubBoard.class);

        if (cubBoard == null) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.invalidCubBoard);
            return;
        }

        cubBoard.removeOwner(target);
        GUIFunctions.OPEN_GUI.function.execute(gui, user, new MessageBuilder("cub_board_players"));
    }
}
