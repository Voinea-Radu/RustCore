package dev.lightdream.rustcore.gui.functions;

import dev.lightdream.rustcore.gui.functions.functions.*;

@SuppressWarnings("unused")
public enum GUIFunctions {

    OPEN_GUI(new OpenGUI()),
    OPEN_GUI_WITH_ARGS(new OpenGUIWithArgs()),
    REMOVE_PLAYER(new RemovePlayer()),
    ENCHANT_ITEM(new EnchantItem()),
    ADD_DIGIT(new AddDigit()),
    CRAFT_RECIPE(new CraftRecipe());

    public GUIFunction function;

    GUIFunctions(GUIFunction function) {
        this.function = function;
    }
}
