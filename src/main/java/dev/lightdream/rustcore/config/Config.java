package dev.lightdream.rustcore.config;

import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.XMaterial;
import dev.lightdream.rustcore.dto.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class Config extends dev.lightdream.api.conifgs.Config {

    public int cubBoardProtectionRadius = 20;
    public Item cubBoardItem = new Item(XMaterial.SPONGE, 1, "CupBoard", Arrays.asList("Place to create a 40x40 area of protection"));
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
                        "3x IRON",
                        "2x DIAMOND",
                        "1x EMERALD")),
                        new GUIItem.GUIItemArgs()));
                put("players", new GUIItem(new Item(XMaterial.PLAYER_HEAD, 10, 1, "Players List", Arrays.asList(
                        "Click to view and edit")),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("open_gui", "cub_board_players");
                        }})));
                put("wood", new GUIItem(new Item(XMaterial.OAK_LOG, 38, 1, "Wood", Arrays.asList(
                        "Currently have: %wood_current_amount%"
                ), new HashMap<String, Object>() {{
                    put("usable", false);
                }}), new GUIItem.GUIItemArgs()));
                put("cobblestone", new GUIItem(new Item(XMaterial.COBBLESTONE, 39, 1, "Cobblestone", Arrays.asList(
                        "Currently have: %cobblestone_current_amount%"
                ), new HashMap<String, Object>() {{
                    put("usable", false);
                }}), new GUIItem.GUIItemArgs()));
                put("iron", new GUIItem(new Item(XMaterial.IRON_INGOT, 40, 1, "Iron", Arrays.asList(
                        "Currently have: %iron_current_amount%"
                ), new HashMap<String, Object>() {{
                    put("usable", false);
                }}), new GUIItem.GUIItemArgs()));
                put("diamond", new GUIItem(new Item(XMaterial.DIAMOND, 41, 1, "Diamond", Arrays.asList(
                        "Currently have: %diamond_current_amount%"
                ), new HashMap<String, Object>() {{
                    put("usable", false);
                }}), new GUIItem.GUIItemArgs()));
                put("emerald", new GUIItem(new Item(XMaterial.EMERALD, 42, 1, "Emerald", Arrays.asList(
                        "Currently have: %emerald_current_amount%"
                ), new HashMap<String, Object>() {{
                    put("usable", false);
                }}), new GUIItem.GUIItemArgs()));
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
                )), new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                    put("remove_player", "%target_player_name%");
                }}), Arrays.asList(20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 38, 39, 40, 41, 42, 48, 49, 50)));
            }}
    );

    public GUIConfig craftingGUI = new GUIConfig(
            "crafting_gui",
            "CHEST",
            "Crafting",
            6,
            new Item(XMaterial.GRAY_STAINED_GLASS_PANE, ""),
            new HashMap<String, GUIItem>() {{
                put("crafting_general", new GUIItem(new Item(XMaterial.SPONGE, 10, 1, "General", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("open_gui", "crafting_general");
                        }})));
                put("crafting_food", new GUIItem(new Item(XMaterial.BREAD, 11, 1, "General", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("open_gui", "crafting_food");
                        }})));
                put("crafting_battle", new GUIItem(new Item(XMaterial.IRON_SWORD, 12, 1, "General", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("open_gui", "crafting_battle");
                        }})));
                put("crafting_armour", new GUIItem(new Item(XMaterial.IRON_HELMET, 19, 1, "General", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("open_gui", "crafting_armour");
                        }})));
                put("crafting_tools", new GUIItem(new Item(XMaterial.IRON_PICKAXE, 20, 1, "General", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("open_gui", "crafting_tools");
                        }})));
                put("crafting_rare", new GUIItem(new Item(XMaterial.BLAZE_POWDER, 21, 1, "General", new ArrayList<>()),
                        new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                            put("open_gui", "crafting_rare");
                        }})));
                put("recipe", new GUIItem(new Item(XMaterial.PLACEHOLDER, 1, "%recipe_name%", Arrays.asList(
                        "%recipe_description_1%",
                        "%recipe_description_2%",
                        "%recipe_description_3%",
                        "%recipe_description_4%",
                        "%recipe_description_6%",
                        "%recipe_description_7%",
                        "%recipe_description_8%",
                        "%recipe_description_9%",
                        "%recipe_description_10%"
                )), new GUIItem.GUIItemArgs(new HashMap<String, Object>() {{
                    put("craft_recipe", "%recipe_id%");
                }}), Arrays.asList(14, 15, 16, 23, 24, 25, 32, 33, 34, 41, 42, 43)));
            }}
    );

    public List<Recipe> generalRecipes = Arrays.asList(
            new Recipe("cub_board", new HashMap<XMaterial, Integer>() {{
                put(XMaterial.OAK_PLANKS, 9);
            }}, cubBoardItem, 10, "Cub Board", Arrays.asList(
                    "You need:",
                    "",
                    "9x Oak Planks",
                    "",
                    "DurationL: %recipe_duration%s"
            ))
    );

    public List<Recipe> foodRecipes = new ArrayList<>();
    public List<Recipe> battleRecipes = new ArrayList<>();
    public List<Recipe> armourRecipes = new ArrayList<>();
    public List<Recipe> toolsRecipes = new ArrayList<>();
    public List<Recipe> rareRecipes = new ArrayList<>();

    public int buildHammerPreviewScheduleTimer = 20;
    public int maxCubBoardMembers = 18;
    public long processCubBoardTime = 160 * 60 * 1000L;
    public int processWoodAmount = 5;
    public int processCobblestoneAmount = 4;
    public int processIronAmount = 3;
    public int processDiamondAmount = 2;
    public int processEmeraldAmount = 1;

}
