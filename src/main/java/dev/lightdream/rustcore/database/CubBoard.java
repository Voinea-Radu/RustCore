package dev.lightdream.rustcore.database;

import dev.lightdream.api.databases.EditableDatabaseEntry;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.LocationRange;
import dev.lightdream.api.dto.PluginLocation;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

@DatabaseTable(tableName = "cupboards")
public class CubBoard extends EditableDatabaseEntry {

    @SuppressWarnings("unused")
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "owners", dataType = DataType.SERIALIZABLE)
    public HashSet<Integer> owners;
    @DatabaseField(columnName = "location", dataType = DataType.SERIALIZABLE)
    public PluginLocation location;

    public boolean created;

    @DatabaseField(columnName = "wood")
    public int wood;
    @DatabaseField(columnName = "cobblestone")
    public int cobblestone;
    @DatabaseField(columnName = "iron")
    public int iron;
    @DatabaseField(columnName = "diamond")
    public int diamond;
    @DatabaseField(columnName = "emerald")
    public int emerald;
    @DatabaseField(columnName = "last_process_time")
    public long lastProcessTime;
    @DatabaseField(columnName = "last_chance")
    public boolean lastChance;

    public CubBoard(PluginLocation location) {
        super(Main.instance);

        this.owners = new HashSet<>();
        this.location = location;
        this.wood = 0;
        this.cobblestone = 0;
        this.iron = 0;
        this.diamond = 0;
        this.emerald = 0;
        this.lastProcessTime = System.currentTimeMillis();
        this.lastChance = false;

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

    public void process() {
        System.out.println("Processing cub board with id " + this.id);
        if (System.currentTimeMillis() - this.lastProcessTime >= Main.instance.config.processCubBoardTime) {
            return;
        }

        this.lastProcessTime = System.currentTimeMillis();

        if (lastChance) {
            new BukkitRunnable() {
                public void run() {
                    location.setBlock(Material.AIR);
                }
            }.runTask(Main.instance);
            delete();
            return;
        }

        if (this.wood < Main.instance.config.processWoodAmount ||
                this.cobblestone < Main.instance.config.processCobblestoneAmount ||
                this.iron < Main.instance.config.processIronAmount ||
                this.diamond < Main.instance.config.processDiamondAmount ||
                this.emerald < Main.instance.config.processEmeraldAmount) {
            this.lastChance = true;
            save();
            return;
        }

        this.wood -= Main.instance.config.processWoodAmount;
        this.cobblestone -= Main.instance.config.processCobblestoneAmount;
        this.iron -= Main.instance.config.processIronAmount;
        this.diamond -= Main.instance.config.processDiamondAmount;
        this.emerald -= Main.instance.config.processEmeraldAmount;
        this.lastChance = false;

        save();
    }

    public void addWood(int amount) {
        this.wood += amount;
        save();
    }

    public void addCobblestone(int amount) {
        this.cobblestone += amount;
        save();
    }

    public void addIron(int amount) {
        this.iron += amount;
        save();
    }

    public void addDiamond(int amount) {
        this.diamond += amount;
        save();
    }

    public void addEmerald(int amount) {
        this.emerald += amount;
        save();
    }


    @Override
    public Integer getID() {
        return this.id;
    }
}
