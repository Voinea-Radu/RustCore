package dev.lightdream.rustcore.gui;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CubBoardPlayersGUI extends GUI {

    private final CubBoard cubBoard;
    private int index;

    public CubBoardPlayersGUI(IAPI api, CubBoard cubBoard) {
        super(api);
        this.cubBoard = cubBoard;
        this.index = -1;
    }

    @Override
    public String parse(String s, Player player) {
        User user = Main.instance.databaseManager.getUser(player);
        User target = Main.instance.databaseManager.getUser(new ArrayList<>(cubBoard.owners).get(index));

        if (target == null) {
            return s;
        }

        String output = new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", user.name);
            put("target_player_name", target.name);
        }}).parseString();

        System.out.println(s + " -> " + output);
        return output;
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.cubBoardPlayersGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new CubBoardPlayersGUI(api, cubBoard);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s) {
        if (index >= cubBoard.owners.size() - 1) {
            return false;
        }
        index++;
        return true;
    }

    @Override
    public HashMap<Class<?>, Object> getArgs() {
        return new HashMap<Class<?>, Object>() {{
            put(CubBoard.class, cubBoard);
        }};
    }
}
