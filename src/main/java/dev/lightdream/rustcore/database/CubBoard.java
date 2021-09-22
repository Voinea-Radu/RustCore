package dev.lightdream.rustcore.database;

import dev.lightdream.api.files.dto.LocationRange;
import dev.lightdream.api.files.dto.PluginLocation;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@DatabaseTable(tableName = "cupboards")
@NoArgsConstructor
public class CubBoard {

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "owners", dataType = DataType.SERIALIZABLE)
    public HashSet<Integer> owners;
    @DatabaseField(columnName = "location", dataType = DataType.SERIALIZABLE)
    public PluginLocation location;
    public boolean created;

    public CubBoard(PluginLocation location) {
        this.owners = new HashSet<>();
        this.location = location;

        for (PluginLocation corner : getProtectionRange().getCorners()) {
            if (Main.instance.databaseManager.getCupBoard(corner) != null) {
                this.created = false;
                return;
            }
        }

        this.created = true;
        Main.instance.getDatabaseManager().save(this);
    }

    public LocationRange getProtectionRange() {
        PluginLocation p1 = location.newOffset(-Main.instance.config.cubBoardProtectionRadius, 0, -Main.instance.config.cubBoardProtectionRadius);
        PluginLocation p2 = location.newOffset(Main.instance.config.cubBoardProtectionRadius, 0, Main.instance.config.cubBoardProtectionRadius);

        p1.y = 0.0;
        p2.y = 255.0;

        return new LocationRange(p1, p2);
    }
}
