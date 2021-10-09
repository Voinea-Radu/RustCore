package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.CraftingGUI;
import dev.lightdream.rustcore.gui.cubBoard.CubBoardGUI;
import dev.lightdream.rustcore.gui.cubBoard.CubBoardPlayersGUI;
import dev.lightdream.rustcore.gui.functions.GUIFunction;

import java.util.List;

public class OpenGUI extends GUIFunction {
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        String menu = args.get(0);

        switch (menu) {
            case "cub_board":
                new CubBoardGUI(Main.instance, gui.getArg(CubBoard.class)).open(user);
                break;
            case "cub_board_players":
                new CubBoardPlayersGUI(Main.instance, gui.getArg(CubBoard.class)).open(user);
                break;
            case "crafting_general":
                new CraftingGUI(Main.instance, menu).open(user);
                break;
            case "crafting_food":
                new CraftingGUI(Main.instance, menu).open(user);
                break;
            case "crafting_battle":
                new CraftingGUI(Main.instance, menu).open(user);
                break;
            case "crafting_armour":
                new CraftingGUI(Main.instance, menu).open(user);
                break;
            case "crafting_tools":
                new CraftingGUI(Main.instance, menu).open(user);
                break;
            case "crafting_rare":
                new CraftingGUI(Main.instance, menu).open(user);
                break;
        }
    }
}
