package dev.lightdream.rustcore.managers.events;

import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.gui.EnchantingGUI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnchantingEvents implements Listener {
    private final Main plugin;

    public EnchantingEvents(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnchantingTableInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (!event.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)) {
            return;
        }

        User user = plugin.databaseManager.getUser(event.getPlayer());

        event.setCancelled(true);
        new EnchantingGUI(plugin, null).open(user);
    }

}
