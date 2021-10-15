package dev.lightdream.rustcore.managers;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.rustcore.database.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class DatabaseManager extends dev.lightdream.api.managers.DatabaseManager {
    public DatabaseManager(IAPI api) {
        super(api);
        setup(User.class);
        setup(CubBoard.class);
        setup(RecyclingTable.class);
        setup(PasswordChest.class);
        setup(Clan.class);
        setup(Ban.class);
        setup(Mute.class);
    }

    public CubBoard getCupBoard(PluginLocation location) {
        return getCupBoard(location, false);
    }

    public CubBoard getCupBoard(PluginLocation location, boolean exact) {
        if (location == null) {
            return null;
        }
        if (exact) {
            return getAll(CubBoard.class).stream().filter(cubBoard -> cubBoard.location.equals(location)).findFirst().orElse(null);
        }
        return getAll(CubBoard.class).stream().filter(cubBoard -> cubBoard.getProtectionRange().check(location)).findFirst().orElse(null);
    }

    public CubBoard getCubBoard(Integer id) {
        return getAll(CubBoard.class).stream().filter(cubBoard -> cubBoard.id == id).findFirst().orElse(null);
    }

    public @NotNull User getUser(@NotNull UUID uuid, String ip) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.uuid.equals(uuid)).findFirst();

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        User user = new User(uuid, Bukkit.getOfflinePlayer(uuid).getName(), api.getSettings().baseLang, ip);
        save(user);
        return user;
    }

    @SuppressWarnings("unused")
    public @Nullable User getUser(@NotNull String name) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.name.equals(name)).findFirst();
        return optionalUser.orElse(null);
    }

    @SuppressWarnings("unused")
    public @NotNull User getUser(@NotNull OfflinePlayer player) {
        return getUser(player.getUniqueId(), "");
    }

    public @NotNull User getUser(@NotNull Player player) {
        return getUser(player.getUniqueId(), player.getAddress().getHostName());
    }

    @SuppressWarnings("unused")
    public @Nullable User getUser(int id) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.id == id).findFirst();

        return optionalUser.orElse(null);
    }

    public @NotNull User getUser(@NotNull HumanEntity entity) {
        return getUser((Player) entity);
    }

    @SuppressWarnings("unused")
    public @Nullable User getUser(@NotNull CommandSender sender) {
        if (!(sender instanceof Player)) {
            return null;
        }
        return getUser((Player) sender);
    }


    public @Nullable RecyclingTable getRecyclingTable(PluginLocation location) {
        if (location == null) {
            return null;
        }

        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    PluginLocation newLocation = location.newOffset(x, y, z);
                    RecyclingTable output = getAll(RecyclingTable.class).stream().filter(recyclingTable -> recyclingTable.location.equals(newLocation)).findFirst().orElse(null);
                    if (output != null) {
                        return output;
                    }
                }
            }
        }

        return null;

    }

    @SuppressWarnings("unused")
    public @Nullable RecyclingTable getRecyclingTable(Integer id) {
        return getAll(RecyclingTable.class).stream().filter(recyclingTable -> recyclingTable.id == id).findFirst().orElse(null);
    }

    public @Nullable PasswordChest getPasswordChest(PluginLocation location) {
        return getAll(PasswordChest.class).stream().filter(passwordChest -> passwordChest.location.equals(location)).findFirst().orElse(null);
    }

    public @Nullable Clan getClan(User user) {
        return getAll(Clan.class).stream().filter(clan -> clan.members.contains(user.id)).findFirst().orElse(null);
    }

    public @Nullable Clan getClan(String name) {
        return getAll(Clan.class).stream().filter(clan -> clan.name.equals(name)).findFirst().orElse(null);
    }

    public @Nullable Ban getBan(User user) {
        return getAll(Ban.class).stream().filter(ban -> {
            if (ban.expire <= System.currentTimeMillis()) {
                return false;
            }
            if (ban.user.equals(user)) {
                return true;
            }
            if (ban.ip) {
                for (String ip : ban.user.ips) {
                    if (user.ips.contains(ip)) {
                        return true;
                    }
                }
            }
            return false;
        }).findFirst().orElse(null);
    }

    public @Nullable Mute getMute(User user) {
        return getAll(Mute.class).stream().filter(mute -> {
            if (mute.expire <= System.currentTimeMillis()) {
                return false;
            }
            return mute.user.equals(user);
        }).findFirst().orElse(null);
    }


}
