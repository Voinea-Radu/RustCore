package dev.lightdream.rustcore.gui.functions.functions;

import dev.lightdream.api.gui.GUI;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.WithArgs;
import dev.lightdream.rustcore.gui.enchanting.EnchantingGUI;
import dev.lightdream.rustcore.gui.functions.GUIFunction;
import dev.lightdream.rustcore.utils.Utils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnchantItem extends GUIFunction {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(GUI gui, User user, List<String> args) {
        String enchantID = args.get(0);
        int level;

        try {
            level = Integer.parseInt(args.get(1));
        } catch (NumberFormatException e) {
            return;
        }

        int xpLevel = Utils.getEnchantByID(enchantID).levels.get(level);
        int xp = Utils.getTotalExperience(xpLevel);

        if (Utils.getTotalExperience(user.getPlayer()) < xp) {
            return;
        }

        Utils.setTotalExperience(user.getPlayer(), Utils.getTotalExperience(user.getPlayer()) - xp);

        ItemStack item = ((WithArgs) gui).getArg(ItemStack.class).clone();

        item.addUnsafeEnchantment(Enchantment.getByName(enchantID), level);

        ((WithArgs) gui).getArg(ItemStack.class).setAmount(0);
        new EnchantingGUI(gui.api,user, item).open(user);
    }
}
