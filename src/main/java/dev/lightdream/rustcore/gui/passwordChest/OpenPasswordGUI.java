package dev.lightdream.rustcore.gui.passwordChest;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.gui.GUIConfig;
import dev.lightdream.api.dto.gui.GUIItem;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.PasswordChest;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.WithArgs;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.HashMap;
import java.util.List;

public class OpenPasswordGUI extends GUI  implements WithArgs {

    public int index = -1;
    public String password = "";
    public User user;
    public PasswordChest chest;

    public OpenPasswordGUI(IAPI api, User user, PasswordChest chest) {
        super(api,user);
        this.chest = chest;
    }

    @Override
    public String parse(String s, String s1, Integer integer) {
        return new MessageBuilder(s).addPlaceholders(new HashMap<String, String>() {{
            put("amount", String.valueOf(index + 1));
        }}).parseString();
    }

    @Override
    public GUIConfig setConfig() {
        return Main.instance.config.passwordGUI;
    }

    @Override
    public InventoryProvider getProvider() {
        return new OpenPasswordGUI(api, user, chest);
    }

    @Override
    public void functionCall(Player player, String function, List<String> args) {
        GUIFunctions.valueOf(function.toUpperCase()).function.execute(this, Main.instance.databaseManager.getUser(player), args);
    }

    @Override
    public boolean canAddItem(GUIItem guiItem, String s, Integer integer) {
        if (guiItem.repeated) {
            index++;
            return index < guiItem.nextSlots.size();
        }
        return true;
    }

    @Override
    public HashMap<Class<?>, Object> getArgs() {
        return new HashMap<Class<?>, Object>() {{
            put(String.class, password);
            put(User.class, user);
            put(Chest.class, chest);
        }};
    }

    @Override
    public void setItems(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void beforeUpdate(Player player, InventoryContents inventoryContents) {

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {

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
        getInventory().open(user.getPlayer(),i);
    }

    @SuppressWarnings("ConstantConditions")
    public void addDigit(String s) {
        this.password += s;
        if (this.password.length() == Main.instance.config.codeLength) {
            if (attemptUnlock()) {
                chest.open(user, password);
                return;
            }
            user.getPlayer().closeInventory();
        }
    }

    private boolean attemptUnlock() {
        return chest.password.equals(password);
    }
}
