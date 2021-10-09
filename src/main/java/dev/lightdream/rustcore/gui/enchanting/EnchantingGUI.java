package dev.lightdream.rustcore.gui.enchanting;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.GUIConfig;
import dev.lightdream.api.dto.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.dto.Enchant;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import dev.lightdream.rustcore.utils.Utils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnchantingGUI extends GUI {

    private final List<String> enchants = new ArrayList<>();
    private ItemStack item;
    private int index = -1;

    public EnchantingGUI(IAPI api, ItemStack item) {
        super(api);
        this.item = item;

        if (item == null) {
            return;
        }

        Main.instance.config.enchants.forEach((target, list) -> {
            if (target.includes(item)) {
                this.enchants.addAll(list);
            }
        });
    }

    @Override
    public String parse(String s, Player player) {

        if (index < 0 || index >= enchants.size()) {
            return s;
        }

        Enchant enchant = Utils.getEnchantByID(enchants.get(index));

        return new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("enchant_name", enchant.name);
            put("enchant_id", enchant.id);
        }}).parseString();
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.enchantingGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new EnchantingGUI(api, item);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s) {
        if (guiItem.repeated) {
            index++;
            return index < enchants.size();
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
            new EnchantingGUI(api, null).open(user);
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
    public void onInventoryClick(InventoryClickEvent event) {

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        User user = Main.instance.databaseManager.getUser(event.getWhoClicked());
        ItemStack clickItem = event.getCurrentItem();

        if (event.getRawSlot() < 54) {
            return;
        }

        if (item != null) {
            user.getPlayer().getInventory().addItem(item);
        }

        event.setCurrentItem(null);
        user.getPlayer().closeInventory();
        new EnchantingGUI(Main.instance, clickItem).open(user);
        event.setCancelled(true);
    }

}
