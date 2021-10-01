package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.functions.GUIFunction;

import java.util.List;

public class CraftRecipe extends GUIFunction {
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        String recipeId = args.get(0);
        if (recipeId == null) {
            return;
        }
        user.addRecipe(recipeId);
    }
}
