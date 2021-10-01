package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.CubBoardGUI;
import dev.lightdream.rustcore.gui.CubBoardPlayersGUI;
import dev.lightdream.rustcore.gui.functions.GUIFunction;

import java.util.List;

public class OpenGUI extends GUIFunction {
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        String menu = args.get(0);

        switch (menu) {
            case "cub_board":
                new CubBoardGUI(Main.instance, (CubBoard) gui.getArgs().get(CubBoard.class)).open(user);
            case "cub_board_players":
                new CubBoardPlayersGUI(Main.instance, (CubBoard) gui.getArgs().get(CubBoard.class)).open(user);
        }
    }
}
