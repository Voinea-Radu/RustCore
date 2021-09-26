package dev.lightdream.rustcore.managers;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.api.gui.GUI;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.api.utils.NbtUtils;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.gui.CubBoardGUI;
import dev.lightdream.rustcore.gui.functions.GUIFunctions;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventManager implements Listener {

    private final Main plugin;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        User user = plugin.getDatabaseManager().getUser(event.getPlayer());
        if (event.getClickedBlock() == null) {
            return;
        }

        CubBoard cubBoard = plugin.getDatabaseManager().getCupBoard(new PluginLocation(event.getClickedBlock().getLocation()));

        if (cubBoard == null) {
            return;
        }

        if (!cubBoard.owners.contains(user.id)) {
            event.setCancelled(true);
            return;
        }
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        User user = plugin.getDatabaseManager().getUser(event.getPlayer());
        CubBoard cubBoard = plugin.getDatabaseManager().getCupBoard(new PluginLocation(event.getBlock().getLocation()));
        if (cubBoard == null) {
            return;
        }
        if (!cubBoard.owners.contains(user.id)) {
            event.setCancelled(true);
            return;
        }
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        User user = plugin.getDatabaseManager().getUser(event.getPlayer());
        CubBoard cubBoard = plugin.getDatabaseManager().getCupBoard(new PluginLocation(event.getBlock().getLocation()));
        if (cubBoard == null) {
            return;
        }
        if (!cubBoard.owners.contains(user.id)) {
            event.setCancelled(true);
            return;
        }
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCupBoardPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (!event.canBuild()) {
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

        event.setCancelled(true);

        if (cubBoard.owners.size() >= Main.instance.config.maxCubBoardMembers) {
            Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.maxNumberOfOwners);
            return;
        }

        cubBoard.addOwner(user);
        new CubBoardGUI(plugin, cubBoard).open(user);
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @EventHandler
    public void onBuildHammerUse(PlayerInteractEvent event) {
        Item item = new Item(event.getItem());

        if (!item.equals(plugin.config.buildHammerItem, false)) {
            return;
        }
    }

    @EventHandler
    public void onMaterialDeposit(InventoryClickEvent event) {
        User user = plugin.databaseManager.getUser((Player) event.getWhoClicked());
        SmartInventory sInventory = Main.instance.inventoryManager.getInventory(user.getPlayer()).orElse(null);

        if (sInventory == null) {
            return;
        }

        GUI gui = (GUI) sInventory.getProvider();

        if (gui == null) {
            return;
        }

        CubBoard cubBoard = (CubBoard) gui.getArgs().get(CubBoard.class);

        if (cubBoard == null) {
            return;
        }

        Item item = new Item(event.getCurrentItem());

        Object usable = NbtUtils.getNBT(event.getCurrentItem(), "usable");

        if (usable != null && !((Boolean) usable)) {
            return;
        }

        switch (item.material) {
            case OAK_LOG:
                cubBoard.addWood(item.amount);
                break;
            case COBBLESTONE:
                cubBoard.addCobblestone(item.amount);
                break;
            case IRON_INGOT:
                cubBoard.addIron(item.amount);
                break;
            case DIAMOND:
                cubBoard.addDiamond(item.amount);
                break;
            case EMERALD:
                cubBoard.addEmerald(item.amount);
                break;
            default:
                return;
        }

        event.setCurrentItem(null);
        new CubBoardGUI(plugin, cubBoard).open(user);
    }

}
