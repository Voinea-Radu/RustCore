package dev.lightdream.rustcore.database;

import dev.lightdream.api.databases.Savable;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.LocationRange;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;

import java.util.HashSet;

@DatabaseTable(tableName = "cupboards")
public class CubBoard extends Savable {

    @SuppressWarnings("unused")
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "founder", foreign = true)
    public User founder;
    @DatabaseField(columnName = "owners", dataType = DataType.SERIALIZABLE)
    public HashSet<Integer> owners;
    @DatabaseField(columnName = "location", dataType = DataType.SERIALIZABLE)
    public PluginLocation location;
    public boolean created;

    public CubBoard(PluginLocation location, User founder) {
        super(Main.instance);

        this.owners = new HashSet<>();
        this.owners.add(founder.id);
        this.location = location;
        this.founder = founder;

        for (PluginLocation corner : getProtectionRange().getCorners()) {
            if (Main.instance.databaseManager.getCupBoard(corner) != null) {
                this.created = false;
                return;
            }
        }

        this.created = true;
        save();
    }

    @SuppressWarnings("unused")
    public CubBoard() {
        super(Main.instance);
    }

    public LocationRange getProtectionRange() {
        PluginLocation p1 = location.newOffset(-Main.instance.config.cubBoardProtectionRadius, 0, -Main.instance.config.cubBoardProtectionRadius);
        PluginLocation p2 = location.newOffset(Main.instance.config.cubBoardProtectionRadius, 0, Main.instance.config.cubBoardProtectionRadius);

        p1.y = 0.0;
        p2.y = 255.0;

        return new LocationRange(p1, p2);
    }

    @SuppressWarnings("unused")
    public void addOwner(User user) {
        owners.add(user.id);
        save();
    }

    @SuppressWarnings("unused")
    public void removeOwner(User user) {
        owners.remove(user.id);
        save();
    }

}
