package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.gui.CubBoardGUI;
import dev.lightdream.rustcore.gui.CubBoardPlayersGUI;
import dev.lightdream.rustcore.gui.functions.GUIFunction;

public class OpenGUI implements GUIFunction {
    @Override
    public void execute(GUI gui, User user, MessageBuilder argsObject) {
        String args = argsObject.getBaseString();
        if (args == null) {
            return;
        }

        switch (args) {
            case "cub_board":
                new CubBoardGUI(Main.instance, (CubBoard) gui.getArgs().get(CubBoard.class)).open(user);
            case "cub_board_players":
                new CubBoardPlayersGUI(Main.instance, (CubBoard) gui.getArgs().get(CubBoard.class)).open(user);
        }
    }
}
