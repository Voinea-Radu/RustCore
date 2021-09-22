package dev.lightdream.rustcore.managers;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.rustcore.database.CubBoard;

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
        if (exact) {
            return getAll(CubBoard.class).stream().filter(cubBoard -> cubBoard.location.equals(location)).findFirst().orElse(null);
        }
        return getAll(CubBoard.class).stream().filter(cubBoard -> cubBoard.getProtectionRange().check(location)).findFirst().orElse(null);
    }
}
