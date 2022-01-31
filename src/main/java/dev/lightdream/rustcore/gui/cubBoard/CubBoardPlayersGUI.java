package dev.lightdream.rustcore.gui.cubBoard;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.gui.GUIConfig;
import dev.lightdream.api.dto.gui.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.WithArgs;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CubBoardPlayersGUI extends GUI implements WithArgs {

    private final CubBoard cubBoard;
    private int index;

    public CubBoardPlayersGUI(IAPI api, User user,CubBoard cubBoard) {
        super(api,user);
        this.cubBoard = cubBoard;
        this.index = -1;
    }

    @Override
    public String parse(String s, String s1, Integer integer) {
        User target = Main.instance.databaseManager.getUser(new ArrayList<>(cubBoard.owners).get(index));

        if (target == null) {
            return s;
        }

        return new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", getUser().name);
            put("target_player_name", target.name);
        }}).parseString();
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.cubBoardPlayersGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new CubBoardPlayersGUI(api, (User) super.getUser(), cubBoard);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s, Integer integer) {
        if (index >= cubBoard.owners.size() - 1) {
            return false;
        }
        index++;
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
        getInventory().open(getUser().getPlayer(),i);
    }

}
