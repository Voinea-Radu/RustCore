package dev.lightdream.rustcore.managers.events;

import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.api.dto.XMaterial;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.RecyclingTable;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RecyclingEvents implements Listener {

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final Main plugin;

    public RecyclingEvents(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHopperItemPickup(InventoryPickupItemEvent event) {
        System.out.println(Arrays.toString(event.getInventory().getContents()));
        PluginLocation location = new PluginLocation(event.getItem().getLocation());
        ItemStack item = event.getItem().getItemStack();

        location.round();
        RecyclingTable recyclingTable = Main.instance.getDatabaseManager().getRecyclingTable(location);

        if (recyclingTable == null) {
            return;
        }

        Block block = recyclingTable.location.getBlock();
        Hopper hopper = (Hopper) block.getState();

        //event.getItem().setItemStack(new ItemStack(Material.STONE, 0));
        //event.getItem().remove();
        //event.getItem().setItemStack();

        List<XMaterial> materials = Main.instance.config.recyclingMap.get(XMaterial.matchXMaterial(item));

        if (materials == null) {
            return;
        }
        materials.forEach(material -> {
            System.out.println(material);
            hopper.getInventory().addItem(material.parseItem());
        });
        //System.out.println(Arrays.toString(event.getInventory().getContents()));
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            event.getInventory().remove(item);
            //System.out.println(Arrays.toString(event.getInventory().getContents()));
        }, 1);

    }

    @EventHandler
    public void onRecyclingTablePlace(BlockPlaceEvent event) {
        Item inHandItem = new Item(event.getItemInHand());
        PluginLocation pluginLocation = new PluginLocation(event.getBlock().getLocation());

        if (!Main.instance.config.recyclingTableItem.equals(inHandItem, false)) {
            System.out.println("Not recycling table");
            System.out.println(inHandItem);
            System.out.println(Main.instance.config.recyclingTableItem);
            return;
        }

        new RecyclingTable(Main.instance, pluginLocation);


    }


}
