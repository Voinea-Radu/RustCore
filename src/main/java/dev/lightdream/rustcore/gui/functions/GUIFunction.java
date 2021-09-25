package dev.lightdream.rustcore.gui.functions;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;

public interface GUIFunction {

    void execute(GUI gui, User user, MessageBuilder args);

}
