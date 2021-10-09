package dev.lightdream.rustcore.managers.events;

import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.PasswordChest;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.passwordChest.OpenPasswordGUI;
import dev.lightdream.rustcore.gui.passwordChest.SetPasswordGUI;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PasswordChestEvents implements Listener {

    private final Main plugin;

    public PasswordChestEvents(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPasswordChestPlace(BlockPlaceEvent event) {
        Item item = new Item(event.getItemInHand());

        if (!item.equals(plugin.config.passwordChestItem)) {
            return;
        }

        new PasswordChest(new PluginLocation(event.getBlockPlaced().getLocation()));
    }

    @EventHandler
    public void onPasswordChestOpen(PlayerInteractEvent event) {

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        User user = plugin.databaseManager.getUser(event.getPlayer());
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        PasswordChest chest = plugin.getDatabaseManager().getPasswordChest(new PluginLocation(block.getLocation()));

        if (chest == null) {
            return;
        }

        if (chest.open(user)) {
            return;
        }

        if (chest.hasPassword()) {
            new OpenPasswordGUI(plugin, user, chest).open(user);
        } else {
            new SetPasswordGUI(plugin, user, chest).open(user);
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPasswordChestBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block == null) {
            return;
        }

        PasswordChest passwordChest = plugin.getDatabaseManager().getPasswordChest(new PluginLocation(block.getLocation()));

        if (passwordChest == null) {
            return;
        }

        passwordChest.delete();
    }

}
