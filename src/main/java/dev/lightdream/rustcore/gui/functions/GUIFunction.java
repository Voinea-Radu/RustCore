package dev.lightdream.rustcore.gui.functions;

import com.google.gson.JsonElement;
import dev.lightdream.api.databases.User;

public interface GUIFunction {

    void execute(User user, JsonElement args);

}
