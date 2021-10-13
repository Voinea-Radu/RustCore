package dev.lightdream.rustcore.database;

import dev.lightdream.api.databases.EditableDatabaseEntry;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;

@DatabaseTable(tableName = "mutes")
public class Mute extends EditableDatabaseEntry {

    @SuppressWarnings("unused")
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "user", foreign = true)
    public User user;
    @DatabaseField(columnName = "expire")
    public Long expire;
    @DatabaseField(columnName = "reason")
    public String reason;
    @DatabaseField(columnName = "by", foreign = true)
    public User by;

    public Mute(User user, User by, Long duration, String reason) {
        super(Main.instance);
        this.user = user;
        this.by = by;
        this.expire = System.currentTimeMillis() + duration;
        this.reason = reason;
        save();
    }

    public Mute(){
        super(Main.instance);
    }

    @Override
    public Integer getID() {
        return id;
    }

    public void unmute() {
        this.expire = 0L;
    }
}
