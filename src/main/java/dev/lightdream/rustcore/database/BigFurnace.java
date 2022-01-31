package dev.lightdream.rustcore.database;

import dev.lightdream.api.dto.location.PluginLocation;
import dev.lightdream.databasemanager.annotations.database.DatabaseField;
import dev.lightdream.databasemanager.annotations.database.DatabaseTable;
import dev.lightdream.databasemanager.dto.DatabaseEntry;
import dev.lightdream.rustcore.Main;

@DatabaseTable(table = "big_furnaces")
public class BigFurnace extends DatabaseEntry {
    @DatabaseField(columnName = "location")
    public PluginLocation location;

    public BigFurnace(PluginLocation location) {
        super(Main.instance);
        this.location = location;
    }

    public BigFurnace() {
        super(Main.instance);
    }

}
