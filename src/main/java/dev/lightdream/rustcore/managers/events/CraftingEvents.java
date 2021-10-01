package dev.lightdream.rustcore.managers.events;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.XMaterial;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.gui.CraftingGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftingEvents implements Listener {

    private final Main plugin;

    public CraftingEvents(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCrafting(PlayerInteractEvent event) {
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        if (!event.getClickedBlock().getType().equals(XMaterial.CRAFTING_TABLE.parseMaterial())) {
            return;
        }

        new CraftingGUI(Main.instance, "general").open(user);
        event.setCancelled(true);
    }

}
