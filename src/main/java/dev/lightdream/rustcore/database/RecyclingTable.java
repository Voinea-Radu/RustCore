package dev.lightdream.rustcore.database;

import dev.lightdream.api.dto.location.PluginLocation;
import dev.lightdream.databasemanager.annotations.database.DatabaseField;
import dev.lightdream.databasemanager.annotations.database.DatabaseTable;
import dev.lightdream.databasemanager.dto.DatabaseEntry;
import dev.lightdream.rustcore.Main;

@DatabaseTable(table = "recycling_tables")
public class RecyclingTable extends DatabaseEntry {

    @DatabaseField(columnName = "location")
    public PluginLocation location;

    public RecyclingTable(PluginLocation location) {
        super(Main.instance);
        this.location = location;
        save();
    }

    @SuppressWarnings("unused")
    public RecyclingTable() {
        super(Main.instance);
        save();
    }

    @Override
    public String toString() {
        return "RecyclingTable{" +
                "id=" + id +
                ", location=" + location +
                '}';
    }
}
