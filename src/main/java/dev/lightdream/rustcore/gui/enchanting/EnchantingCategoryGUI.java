package dev.lightdream.rustcore.gui.enchanting;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.gui.GUIConfig;
import dev.lightdream.api.dto.gui.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.api.utils.Utils;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.dto.Enchant;
import dev.lightdream.rustcore.gui.WithArgs;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class EnchantingCategoryGUI extends GUI  implements WithArgs {

    private final Enchant enchant;
    private ItemStack item;
    private int index = -1;

    public EnchantingCategoryGUI(IAPI api, User user,ItemStack item, Enchant enchant) {
        super(api,user);
        this.item = item;
        this.enchant = enchant;
    }

    @Override
    public String parse(String s, String s1, Integer integer) {
        if (index < 0 || index >= enchant.levels.size()) {
            return s;
        }

        return new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("enchant_name", enchant.name);
            put("enchant_id", enchant.id);
            put("enchant_level", String.valueOf(enchant.levels.get(index + 1)));
        }}).parseString();
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.enchantingCategoryGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new EnchantingCategoryGUI(api, (User) getUser(), item, enchant);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s, Integer integer) {
        if (guiItem.repeated) {
            index++;
            return index < enchant.levels.size();
        }
        return true;
    }

    @Override
    public HashMap<Class<?>, Object> getArgs() {
        return new HashMap<Class<?>, Object>() {{
            put(ItemStack.class, item);
        }};
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setItems(Player player, InventoryContents inventoryContents) {
        inventoryContents.set(Utils.getSlotPosition(Main.instance.config.enchantingItemPosition), ClickableItem.of(item, e -> {
            User user = Main.instance.databaseManager.getUser(e.getWhoClicked());
            user.getPlayer().getInventory().addItem(item);
            item = null;
            new EnchantingGUI(api, user,null).open(user);
        }));
    }

    @Override
    public void beforeUpdate(Player player, InventoryContents inventoryContents) {

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        if (item != null) {
            user.getPlayer().getInventory().addItem(item);
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent inventoryClickEvent) {

    }

    @Override
    public boolean preventClose() {
        return false;
    }

    @Override
    public void changePage(int i) {
        getInventory().open(getUser().getPlayer(),i);
    }

}
