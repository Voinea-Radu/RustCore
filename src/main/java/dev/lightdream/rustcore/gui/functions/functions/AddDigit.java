package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.functions.GUIFunction;
import dev.lightdream.rustcore.gui.passwordChest.OpenPasswordGUI;
import dev.lightdream.rustcore.gui.passwordChest.SetPasswordGUI;

import java.util.List;

public class AddDigit extends GUIFunction {
    @Override
    public void execute(GUI gui, User user, List<String> args) {

        if (gui instanceof SetPasswordGUI) {
            SetPasswordGUI setPasswordGUI = (SetPasswordGUI) gui;
            setPasswordGUI.addDigit(args.get(0));
            return;
        }

        if (gui instanceof OpenPasswordGUI) {
            OpenPasswordGUI openPasswordGUI = (OpenPasswordGUI) gui;
            openPasswordGUI.addDigit(args.get(0));
        }
    }
}
