package dev.lightdream.rustcore.managers;

import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.location.PluginLocation;
import dev.lightdream.api.utils.Utils;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.BigFurnace;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.database.User;
import org.bukkit.Bukkit;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ScheduleManager {

    private final Main plugin;

    public ScheduleManager(Main plugin) {
        this.plugin = plugin;
        registerBuildHammerPreview();
        registerCubBoardProcess();
        registerCraftingProcess();
        registerBabyGodDisabler();
        registerBigFurnaces();
    }

    @SuppressWarnings("ConstantConditions")
    private void registerBuildHammerPreview() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            if (!new Item(player.getItemInHand()).equals(plugin.config.buildHammerItem, false)) {
                return;
            }

            User user = Main.instance.databaseManager.getUser(player);
            if (!user.isOnline()) {
                return;
            }

            CubBoard cubBoard = plugin.databaseManager.getCupBoard(user.getLocation());

            if (cubBoard == null) {
                return;
            }

            List<PluginLocation> corners = cubBoard.getProtectionRange().getCorners();

            HashSet<PluginLocation> effects = new HashSet<>();

            PluginLocation min = Utils.minPluginLocation(corners);
            PluginLocation max = Utils.maxPluginLocation(corners);

            for (Double x = min.x; x <= max.x; x++) {
                for (double y = user.getLocation().y - 10; y <= user.getLocation().y + 10; y++) {
                    PluginLocation p1 = min.clone();
                    PluginLocation p2 = max.clone();
                    p1.x = x;
                    p1.y = y;
                    p2.x = x;
                    p2.y = y;
                    effects.add(p1);
                    effects.add(p2);
                }
            }

            for (Double z = min.z; z <= max.z; z++) {
                for (double y = user.getLocation().y - 10; y <= user.getLocation().y + 10; y++) {
                    PluginLocation p1 = min.clone();
                    PluginLocation p2 = max.clone();
                    p1.z = z;
                    p1.y = y;
                    p2.z = z;
                    p2.y = y;
                    effects.add(p1);
                    effects.add(p2);
                }
            }

            Color effectColor = cubBoard.owners.contains(user.id) ? Color.GREEN : Color.RED;

            effects.forEach(p -> new ParticleBuilder(ParticleEffect.REDSTONE, p.newOffset(0.5, 0.5, 0.5).toLocation())
                    .setOffsetY(1f)
                    .setSpeed(0.02f)
                    .setColor(effectColor)
                    .display(player));
        }), 0, plugin.config.buildHammerPreviewScheduleTimer);
    }

    private void registerCubBoardProcess() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> plugin.databaseManager.getAll(CubBoard.class).forEach(CubBoard::process), 0, 60 * 20);
    }

    private void registerCraftingProcess() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                User user = plugin.databaseManager.getUser(onlinePlayer);
                user.processRecipe();
            }
        }, 0, 20);
    }

    private void registerBabyGodDisabler() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            User user = Main.instance.databaseManager.getUser(player);
            if (System.currentTimeMillis() - user.creationDate > Main.instance.config.babyModeTime * 1000L) {
                user.setBabyGod(false);
            }
        }), 0, 60 * 20);
    }

    private void registerBigFurnaces(){
        Bukkit.getScheduler().runTaskTimer(plugin, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            List<BigFurnace> toRemove = new ArrayList<>();
            plugin.databaseManager.getAll(BigFurnace.class).forEach(bigFurnace -> {
                if(!(bigFurnace.location.getBlock().getState() instanceof  Furnace)){
                    toRemove.add(bigFurnace);
                    return;
                }
                Furnace furnace = (Furnace) bigFurnace.location.getBlock().getState();
                if (furnace.getCookTime() < 200) {
                    furnace.setCookTime((short) (furnace.getCookTime() + 10));
                    furnace.update();
                }
            });
            toRemove.forEach(BigFurnace::delete);
        }), 0, 10);

    }

}
