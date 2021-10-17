package dev.lightdream.rustcore.managers.events;

import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.BigFurnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BigFurnaceEvents implements Listener {

    private final Main plugin;

    public BigFurnaceEvents(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBigFurnacePlace(BlockPlaceEvent event) {
        Item item = new Item(event.getItemInHand());

        if (!item.equals(plugin.config.bigFurnace, false)) {
            return;
        }

        System.out.println("Created big furnace");
        new BigFurnace(new PluginLocation(event.getBlock().getLocation()));
    }

    @EventHandler
    public void onBigFurnaceBreak(BlockBreakEvent event) {
        BigFurnace furnace = Main.instance.databaseManager.getBigFurnace(new PluginLocation(event.getBlock().getLocation()));

        if (furnace == null) {
            return;
        }

        System.out.println("Deleted big furnace");
        furnace.delete();
    }

}
