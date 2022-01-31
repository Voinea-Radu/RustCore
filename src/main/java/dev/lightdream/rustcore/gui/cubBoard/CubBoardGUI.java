package dev.lightdream.rustcore.gui.cubBoard;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.gui.GUIConfig;
import dev.lightdream.api.dto.gui.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.List;

public class CubBoardGUI extends GUI {

    private final CubBoard cubBoard;

    public CubBoardGUI(IAPI api, User user, CubBoard cubBoard) {
        super(api,user);
        this.cubBoard = cubBoard;
    }

    @Override
    public String parse(String s, String s1, Integer integer) {
        return new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", CubBoardGUI.super.getUser().name);
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
        return new CubBoardGUI(api, (User) getUser(), cubBoard);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s, Integer integer) {
        return true;
    }

    public HashMap<Class<?>, Object> getArgs() {
        return new HashMap<Class<?>, Object>() {{
            put(CubBoard.class, cubBoard);
        }};
    }

    @Override
    public void setItems(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void beforeUpdate(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {

    }

    @Override
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public boolean preventClose() {
        return false;
    }

    @Override
    public void changePage(int i) {
        getInventory().open(getUser().getPlayer(), i);
    }
}
