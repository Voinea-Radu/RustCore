package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.functions.GUIFunction;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;

import java.util.List;

public class RemovePlayer extends GUIFunction {
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        String targetName = args.get(0);

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
        GUIFunctions.OPEN_GUI.function.execute(gui, user, "cub_board_players");
    }
}
