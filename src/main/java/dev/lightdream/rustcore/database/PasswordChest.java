package dev.lightdream.rustcore.database;

import dev.lightdream.api.dto.location.PluginLocation;
import dev.lightdream.databasemanager.annotations.database.DatabaseField;
import dev.lightdream.databasemanager.annotations.database.DatabaseTable;
import dev.lightdream.databasemanager.dto.DatabaseEntry;
import dev.lightdream.rustcore.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import java.util.HashSet;

@DatabaseTable(table = "password_chests")
public class PasswordChest extends DatabaseEntry {

    @DatabaseField(columnName = "location")
    public PluginLocation location;
    @DatabaseField(columnName = "active_owners")
    public HashSet<Integer> owners = new HashSet<>();
    @DatabaseField(columnName = "password")
    public String password = "";

    public PasswordChest(PluginLocation pluginLocation) {
        super(Main.instance);
        this.location = pluginLocation;
    }

    @SuppressWarnings("unused")
    public PasswordChest() {
        super(Main.instance);
    }

    @SuppressWarnings("unused")
    public boolean canOpen(User user) {
        return canOpen(user, "");
    }

    public boolean canOpen(User user, String password) {
        if (owners.contains(user.id)) {
            return true;
        }
        if (this.password.equals(password) && hasPassword()) {
            addOwner(user);
            return true;
        }
        return false;
    }

    public boolean open(User user) {
        return open(user, "");
    }

    @SuppressWarnings("ConstantConditions")
    public boolean open(User user, String password) {
        if (canOpen(user, password)) {
            Block block = location.getBlock();
            if (block == null || !block.getType().equals(Material.CHEST)) {
                delete();
                return false;
            }
            Chest chest = (Chest) block.getState();
            if (chest == null) {
                delete();
                return false;
            }
            user.getPlayer().openInventory(chest.getBlockInventory());
            return true;
        }
        return false;
    }

    public void addOwner(User user) {
        this.owners.add(user.id);
        save();
    }

    public void setPassword(User user, String password) {
        this.password = password;
        addOwner(user);
        save();
    }

    public boolean hasPassword() {
        return !this.password.equals("");
    }
}
