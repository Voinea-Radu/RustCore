package dev.lightdream.rustcore.gui.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.database.User;

import java.util.Arrays;
import java.util.List;

public abstract class GUIFunction {

    public abstract void execute(GUI gui, User user, List<String> args);

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public void execute(GUI gui, User user, String args){
        execute(gui, user, Arrays.asList(args));
    }

}
