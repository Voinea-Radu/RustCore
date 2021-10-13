package dev.lightdream.rustcore.database;

import dev.lightdream.api.databases.EditableDatabaseEntry;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;
import lombok.NoArgsConstructor;

@DatabaseTable(tableName = "bans")
public class Ban extends EditableDatabaseEntry {

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
    @DatabaseField(columnName = "ip")
    public boolean ip;

    public Ban(User user, User by, Long duration, String reason, boolean ip) {
        super(Main.instance);
        this.user = user;
        this.by = by;
        this.expire = System.currentTimeMillis() + duration;
        this.reason = reason;
        this.ip = ip;
        save();
    }

    public Ban(){
        super(Main.instance);
    }

    @Override
    public Integer getID() {
        return id;
    }

    public void unban() {
        this.expire = 0L;
    }
}
