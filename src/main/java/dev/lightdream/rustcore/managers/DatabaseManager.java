package dev.lightdream.rustcore.managers;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.rustcore.database.CubBoard;
import dev.lightdream.rustcore.database.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
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

    public @NotNull User getUser(@NotNull UUID uuid) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.uuid.equals(uuid)).findFirst();

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        User user = new User(uuid, Bukkit.getOfflinePlayer(uuid).getName(), api.getSettings().baseLang);
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
        return getUser(player.getUniqueId());
    }

    public @NotNull User getUser(@NotNull Player player) {
        return getUser(player.getUniqueId());
    }

    @SuppressWarnings("unused")
    public @Nullable User getUser(int id) {
        Optional<User> optionalUser = getAll(User.class).stream().filter(user -> user.id == id).findFirst();

        return optionalUser.orElse(null);
    }

    @SuppressWarnings("unused")
    public @Nullable User getUser(@NotNull CommandSender sender) {
        if (!(sender instanceof Player)) {
            return null;
        }
        return getUser((Player) sender);
    }


}
