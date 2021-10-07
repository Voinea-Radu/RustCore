package dev.lightdream.rustcore.managers.events;

import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.User;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorStandEvents implements Listener {

    private final Main plugin;

    public ArmorStandEvents(Main plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void foo(EntityDamageByEntityEvent event) {

        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        if (!(damager instanceof Player)) {
            return;
        }

        if (!damaged.getType().equals(EntityType.ARMOR_STAND)) {
            return;
        }

        User user = plugin.databaseManager.getUser((Player) damager);

        if (user.getPlayer().isSneaking()) {
            return;
        }

        ArmorStand armorStand = (ArmorStand) damaged;

        ItemStack[] armour = user.getPlayer().getInventory().getArmorContents().clone();

        user.getPlayer().getInventory().setHelmet(armorStand.getHelmet());
        user.getPlayer().getInventory().setChestplate(armorStand.getChestplate());
        user.getPlayer().getInventory().setLeggings(armorStand.getLeggings());
        user.getPlayer().getInventory().setBoots(armorStand.getBoots());

        armorStand.setHelmet(armour[3].clone());
        armorStand.setChestplate(armour[2].clone());
        armorStand.setLeggings(armour[1].clone());
        armorStand.setBoots(armour[0].clone());

        event.setCancelled(true);
    }

}
