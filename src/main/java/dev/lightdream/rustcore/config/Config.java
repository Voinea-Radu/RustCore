package dev.lightdream.rustcore.config;

import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.XMaterial;

import java.util.Arrays;
import java.util.HashMap;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class Config extends dev.lightdream.api.conifgs.Config {

    public int cubBoardProtectionRadius = 20;
    public Item cubBoardItem = new Item(XMaterial.CRAFTING_TABLE, 1, "CupBoard", Arrays.asList("Place to create a 40x40 area of protection"));
    public Item buildHammerItem = new Item(XMaterial.IRON_AXE, 1, "Building Hammer", Arrays.asList("Right click to preview the build protection"));

    public GUIConfig cubBoardGUI = new GUIConfig(
            "cub_board_gui",
            "CHEST",
            "Cub Board",
            6,
            new Item(XMaterial.GRAY_STAINED_GLASS_PANE, ""),
            new HashMap<String, GUIItem>() {{
                put("book", new GUIItem(new Item(XMaterial.BOOK, 22, 1, "Information", Arrays.asList(
                        "You need the next to upkeep the cub board for one hour",
                        "10x WOOD",
                        "5x COBBLESTONE",
                        "2x DIAMOND",
                        "1x EMERALD",
                        "3x IRON")),
                        new GUIItem.GUIItemArgs()));
                put("players", new GUIItem(new Item(XMaterial.PLAYER_HEAD, 10, 1, "Players List", Arrays.asList(
                        "Click to view and edit")),
                        new GUIItem.GUIItemArgs(new HashMap<Object, Object>() {{
                            put("open_gui", "cub_board_players");
                        }})));
            }});
    public GUIConfig cubBoardPlayersGUI = new GUIConfig(
            "cub_board_players_gui",
            "CHEST",
            "Cub Board - Players",
            6,
            new Item(XMaterial.GRAY_STAINED_GLASS_PANE, ""),
            new HashMap<String, GUIItem>() {{
                put("player_head", new GUIItem(new Item(XMaterial.PLAYER_HEAD, 1, 1, "%target_player_name%", "%player_name%", Arrays.asList(
                        "Click to remove"
                )), new GUIItem.GUIItemArgs(new HashMap<Object, Object>() {{
                    put("remove_player", "%target_player_name%");
                }}), true, Arrays.asList(20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42, 48, 49, 50)));
            }}
    );

    public int buildHammerPreviewScheduleTimer = 20;
    public int maxCubBoardMembers = 18;

}
