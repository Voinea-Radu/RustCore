package dev.lightdream.rustcore.gui;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.dto.Recipe;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CraftingGUI extends GUI {

    private final String category;
    private final List<Recipe> recipes;
    private int index = -1;

    public CraftingGUI(IAPI api, String category) {
        super(api);
        if (category.equals("general")) {
            this.recipes = Main.instance.config.generalRecipes;
        } else {
            this.recipes = new ArrayList<>();
        }
        this.category = category;
        //System.out.println("Recipes size "+this.recipes.size());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String parse(String s, Player player) {
        //System.out.println("Index " + index);

        if (index < 0) {
            return s;
        }

        if (index >= recipes.size()) {
            return s;
        }

        Recipe recipe = recipes.get(index);

        recipe.description = new ArrayList<>(recipe.description);

        while (recipe.description.size() < 10) {
            recipe.description.add("");
        }

        String output = new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("recipe_name", recipe.name);
            put("recipe_duration", String.valueOf(recipe.time));
            put("recipe_description_1", recipe.description.get(0));
            put("recipe_description_2", recipe.description.get(1));
            put("recipe_description_3", recipe.description.get(2));
            put("recipe_description_4", recipe.description.get(3));
            put("recipe_description_5", recipe.description.get(4));
            put("recipe_description_6", recipe.description.get(5));
            put("recipe_description_7", recipe.description.get(6));
            put("recipe_description_8", recipe.description.get(7));
            put("recipe_description_9", recipe.description.get(8));
            put("recipe_description_10", recipe.description.get(9));
            put("recipe_id", recipe.id);
            put("material", recipe.result.material.parseMaterial().name());
        }}).parseString();
        return output;
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.craftingGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new CraftingGUI(api, category);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s) {
        if (!guiItem.repeated) {
            return true;
        }
        if (index >= recipes.size() - 1) {
            return false;
        }
        index++;
        return true;
    }

    @Override
    public HashMap<Class<?>, Object> getArgs() {
        return new HashMap<Class<?>, Object>() {{
            put(String.class, category);
        }};
    }
}
