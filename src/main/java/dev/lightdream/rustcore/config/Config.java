package dev.lightdream.rustcore.config;

import dev.lightdream.api.files.dto.GUIConfig;
import dev.lightdream.api.files.dto.GUIItem;
import dev.lightdream.api.files.dto.Item;
import dev.lightdream.api.files.dto.XMaterial;

import java.util.Arrays;
import java.util.HashMap;

public class Config extends dev.lightdream.api.files.config.Config {

    public int cubBoardProtectionRadius = 20;
    public Item cubBoardItem = new Item(XMaterial.CRAFTING_TABLE, 1, "CupBoard", Arrays.asList("Place to create a 40x40 area of protection"));
    public Item buildHammerItem = new Item(XMaterial.IRON_AXE, 1, "Building Hammer", Arrays.asList("Right click to preview the build protection"));

    public GUIConfig cubBoardGUI= new GUIConfig(
            "cub_board_gui",
            "CHEST",
            "Cub Board",
            3,
            new Item(XMaterial.GRAY_STAINED_GLASS_PANE, ""),
            new HashMap<String, GUIItem>(){{

            }}
    );

    public int buildHammerPreviewScheduleTimer = 20;

}
