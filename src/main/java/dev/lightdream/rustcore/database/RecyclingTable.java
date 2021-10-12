package dev.lightdream.rustcore.database;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.databases.EditableDatabaseEntry;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;

@DatabaseTable(tableName = "recycling_tables")
public class RecyclingTable extends EditableDatabaseEntry {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "location", dataType = DataType.SERIALIZABLE)
    public PluginLocation location;

    public RecyclingTable(IAPI api, PluginLocation location) {
        super(api);
        this.location = location;
        save();
    }

    @SuppressWarnings("unused")
    public RecyclingTable() {
        super(Main.instance);
        save();
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public String toString() {
        return "RecyclingTable{" +
                "id=" + id +
                ", location=" + location +
                '}';
    }
}
