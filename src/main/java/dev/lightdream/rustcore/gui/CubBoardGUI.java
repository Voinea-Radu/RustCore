package dev.lightdream.rustcore.gui;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.files.dto.GUIConfig;
import dev.lightdream.api.files.dto.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

public class CubBoardGUI extends GUI {

    private final CubBoard cubBoard;

    public CubBoardGUI(IAPI api, CubBoard cubBoard) {
        super(api);
        this.cubBoard = cubBoard;
    }

    @Override
    public String parse(String s, Player player) {
        return s;
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
    public void functionCall(Player player, String s, Object o) {

    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s) {
        return true;
    }
}
