package dev.lightdream.rustcore.managers;

import dev.lightdream.api.dto.Item;
import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import dev.lightdream.rustcore.managers.events.*;
import dev.lightdream.rustcore.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

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
        plugin.getServer().getPluginManager().registerEvents(new PasswordChestEvents(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BigFurnaceEvents(plugin), plugin);
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
    public void onGodDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        User user = plugin.getDatabaseManager().getUser((Player) event.getEntity());

        if (user.god) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBannedPlayerJoin(PlayerJoinEvent event) {
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        if (!user.isBanned()) {
            return;
        }

        event.getPlayer().kickPlayer(new MessageBuilder(Main.instance.lang.banMessage).addPlaceholders(new HashMap<String, String>() {{
            put("reason", user.getBan().reason);
            put("duration", Utils.msToDate(user.getBan().expire - System.currentTimeMillis()));
        }}).parseString());
    }

    @EventHandler
    public void onMutedPlayerMessageSend(AsyncPlayerChatEvent event) {
        User user = Main.instance.databaseManager.getUser(event.getPlayer());

        if (!user.isMuted()) {
            return;
        }

        if (event.getMessage().startsWith("/")) {
            return;
        }

        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.youAreMuted);
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFirstTimeJoin(PlayerJoinEvent event) {
        if (Main.instance.databaseManager.userExists(event.getPlayer().getUniqueId())) {
            return;
        }

        for (String s : Main.instance.data.startKit) {
            event.getPlayer().getInventory().addItem(ItemBuilder.deserialize(s));
        }
    }
}
