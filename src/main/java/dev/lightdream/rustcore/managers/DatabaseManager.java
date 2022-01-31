package dev.lightdream.rustcore.managers;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.dto.location.PluginLocation;
import dev.lightdream.rustcore.database.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class DatabaseManager extends dev.lightdream.api.managers.DatabaseManager {
    public DatabaseManager(IAPI api) {
        super(api);
    }
    @Override
    public void setup() {
        setup(User.class);
        setup(CubBoard.class);
        setup(RecyclingTable.class);
        setup(PasswordChest.class);
        setup(Clan.class);
        setup(Ban.class);
        setup(Mute.class);
        setup(BigFurnace.class);
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

    public boolean userExists(UUID uuid) {
        return getAll(User.class).stream().anyMatch(user -> user.uuid.equals(uuid));
    }

    @Nullable
    public User getUser(@NotNull final UUID uuid) {
        return this.get(User.class, new HashMap<String, Object>() {
            {
                this.put("uuid", uuid);
            }
        }).stream().findFirst().orElse(null);
    }

    @Nullable
    public User getUser(@NotNull String name) {
        Optional<User> optionalUser = this.getAll(User.class).stream().filter((user) -> user.name.equals(name)).findFirst();
        return optionalUser.orElse(null);
    }

    public @NotNull User createUser(@NotNull OfflinePlayer player) {
        User user = getUser(player.getUniqueId());
        if (user == null) {
            user = getUser(player.getName());
        }
        if (user != null) {
            user.uuid = player.getUniqueId();
        } else {
            user = new User(player.getUniqueId(), player.getName(), player.isOnline() ? player.getPlayer().getAddress().getHostName() : "");
        }
        user.save();
        return user;
    }
    @SuppressWarnings("unused")
    public @NotNull User getUser(@NotNull OfflinePlayer player) {
        return createUser(player);
    }

    public @NotNull User getUser(@NotNull Player player) {
        return createUser(player);
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

    public @Nullable BigFurnace getBigFurnace(PluginLocation location) {
        return getAll(BigFurnace.class).stream().filter(bigFurnace -> bigFurnace.location.equals(location)).findFirst().orElse(null);
    }



}
