package dev.lightdream.rustcore.managers;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.api.utils.Utils;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.CubBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.HashSet;
import java.util.List;

public class ScheduleManager {

    private final Main plugin;

    public ScheduleManager(Main plugin) {
        this.plugin = plugin;
        registerBuildHammerPreview();
    }

    @SuppressWarnings("ConstantConditions")
    private void registerBuildHammerPreview() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (User user : plugin.eventManager.buildHammerSession) {
                if (!user.isOnline()) {
                    continue;
                }
                CubBoard cubBoard = plugin.databaseManager.getCupBoard(user.getLocation());
                if (cubBoard == null) {
                    continue;
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

                Player player = user.getPlayer();
                effects.forEach(p -> new ParticleBuilder(ParticleEffect.FLAME, p.toLocation())
                        .setOffsetY(1f)
                        .setSpeed(0.1f)
                        .display(player));
            }
        }, 0, plugin.config.buildHammerPreviewScheduleTimer);
    }

}
