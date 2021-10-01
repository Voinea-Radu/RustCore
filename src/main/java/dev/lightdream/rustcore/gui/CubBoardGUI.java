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

import java.util.HashMap;
import java.util.List;

public class CubBoardGUI extends GUI {

    private final CubBoard cubBoard;

    public CubBoardGUI(IAPI api, CubBoard cubBoard) {
        super(api);
        this.cubBoard = cubBoard;
    }

    @Override
    public String parse(String s, Player player) {
        User user = Main.instance.databaseManager.getUser(player);
        return new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", user.name);
            put("wood_current_amount", String.valueOf(cubBoard.wood));
            put("cobblestone_current_amount", String.valueOf(cubBoard.cobblestone));
            put("iron_current_amount", String.valueOf(cubBoard.iron));
            put("diamond_current_amount", String.valueOf(cubBoard.diamond));
            put("emerald_current_amount", String.valueOf(cubBoard.emerald));
        }}).parseString();
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.cubBoardGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new CubBoardGUI(api, cubBoard);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s) {
        return true;
    }

    @Override
    public HashMap<Class<?>, Object> getArgs() {
        return new HashMap<Class<?>, Object>() {{
            put(CubBoard.class, cubBoard);
        }};
    }
}
