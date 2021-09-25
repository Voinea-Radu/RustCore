package dev.lightdream.rustcore.gui.functions;

import dev.lightdream.rustcore.gui.functions.functions.OpenGUI;
import dev.lightdream.rustcore.gui.functions.functions.RemovePlayer;

@SuppressWarnings("unused")
public enum GUIFunctions {

    OPEN_GUI(new OpenGUI()),
    REMOVE_PLAYER(new RemovePlayer());

    public GUIFunction function;

    GUIFunctions(GUIFunction function) {
        this.function = function;
    }
}
