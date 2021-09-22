package dev.lightdream.rustcore.managers;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.Item;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.gui.CubBoardGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class EventManager implements Listener {

    private final Main plugin;
    public List<User> buildHammerSession = new ArrayList<>();

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        User user = plugin.getDatabaseManager().getUser(event.getPlayer());
        if(event.getClickedBlock()==null){
            return;
        }
        CubBoard cubBoard = plugin.getDatabaseManager().getCupBoard(new PluginLocation(event.getClickedBlock().getLocation()));
        if (cubBoard == null) {
            return;
        }
        if (!cubBoard.owners.contains(user)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        User user = plugin.getDatabaseManager().getUser(event.getPlayer());
        CubBoard cubBoard = plugin.getDatabaseManager().getCupBoard(new PluginLocation(event.getBlock().getLocation()));
        if (cubBoard == null) {
            return;
        }
        if (!cubBoard.owners.contains(user)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        User user = plugin.getDatabaseManager().getUser(event.getPlayer());
        CubBoard cubBoard = plugin.getDatabaseManager().getCupBoard(new PluginLocation(event.getBlock().getLocation()));
        if (cubBoard == null) {
            return;
        }
        if (!cubBoard.owners.contains(user)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCupBoardPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Item item = new Item(event.getItemInHand());

        if (!item.equals(plugin.config.cubBoardItem, false)) {
            return;
        }

        if (!new CubBoard(new PluginLocation(event.getBlock().getLocation())).created) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onCupBoardInteract(PlayerInteractEvent event) {
        User user = plugin.databaseManager.getUser(event.getPlayer());

        if (event.getClickedBlock() == null) {
            return;
        }
        CubBoard cubBoard = plugin.databaseManager.getCupBoard(new PluginLocation(event.getClickedBlock().getLocation()), true);
        if (cubBoard == null) {
            return;
        }
        new CubBoardGUI(plugin, cubBoard).open(user);
    }

    @EventHandler
    public void onBuildHammerUse(PlayerInteractEvent event){
        User user = plugin.databaseManager.getUser(event.getPlayer());
        Item item = new Item(event.getItem());

        if (!item.equals(plugin.config.buildHammerItem, false)) {
            return;
        }

        if (buildHammerSession.contains(user)) {
            buildHammerSession.remove(user);
            System.out.println("Removing user from build hammer session");
            return;
        }

        buildHammerSession.add(user);
        System.out.println("Adding user to build hammer session");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        User user = plugin.databaseManager.getUser(event.getPlayer());
        buildHammerSession.remove(user);
    }

}
