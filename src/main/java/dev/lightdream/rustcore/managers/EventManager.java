package dev.lightdream.rustcore.managers;

import dev.lightdream.api.dto.Item;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.managers.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventManager implements Listener {

    private final Main plugin;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        plugin.getServer().getPluginManager().registerEvents(new CraftingEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new CubBoardEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EnchantingEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RecyclingEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ArmorStandEvents(plugin), plugin);
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @EventHandler
    public void onBuildHammerUse(PlayerInteractEvent event) {
        Item item = new Item(event.getItem());

        if (!item.equals(plugin.config.buildHammerItem, false)) {
            return;
        }
    }


}
