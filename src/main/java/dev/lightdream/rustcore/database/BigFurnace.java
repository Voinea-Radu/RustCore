package dev.lightdream.rustcore.database;

import dev.lightdream.api.databases.EditableDatabaseEntry;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;

@DatabaseTable(tableName = "big_furnaces")
public class BigFurnace extends EditableDatabaseEntry {

    @SuppressWarnings("unused")
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "location", dataType = DataType.SERIALIZABLE)
    public PluginLocation location;

    public BigFurnace(PluginLocation location) {
        super(Main.instance);
        this.location = location;
        save();
    }

    public BigFurnace() {
        super(Main.instance);
    }

    @Override
    public Integer getID() {
        return id;
    }
}
