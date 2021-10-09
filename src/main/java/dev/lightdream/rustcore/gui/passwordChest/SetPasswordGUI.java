package dev.lightdream.rustcore.gui.passwordChest;

import dev.lightdream.api.IAPI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.PasswordChest;
import dev.lightdream.rustcore.database.User;
import fr.minuskube.inv.content.InventoryProvider;

public class SetPasswordGUI extends OpenPasswordGUI {
    public SetPasswordGUI(IAPI api, User user, PasswordChest chest) {
        super(api, user, chest);
    }

    @Override
    public void addDigit(String s) {
        this.password += s;
        if (this.password.length() == Main.instance.config.codeLength) {
            chest.setPassword(user, password);
            chest.open(user, password);
        }
    }

    @Override
    public InventoryProvider getProvider() {
        return new SetPasswordGUI(api, user, chest);
    }
}
