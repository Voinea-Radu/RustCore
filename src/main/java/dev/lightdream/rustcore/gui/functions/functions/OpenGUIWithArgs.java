package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.EnchantingCategoryGUI;
import dev.lightdream.rustcore.gui.functions.GUIFunction;
import dev.lightdream.rustcore.utils.Utils;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OpenGUIWithArgs extends GUIFunction {

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        String menu = args.get(0);
        String enchantId = args.get(1);

        switch (menu) {
            case "enchanting_category_gui":
                ItemStack item = gui.getArg(ItemStack.class).clone();
                gui.getArg(ItemStack.class).setAmount(0);

                if (item == null) {
                    break;
                }
                if (args.size() != 2) {
                    break;
                }

                new EnchantingCategoryGUI(Main.instance, item, Utils.getEnchantByID(enchantId)).open(user);
                break;
        }
    }
}
