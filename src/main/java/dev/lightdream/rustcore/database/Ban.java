package dev.lightdream.rustcore.database;

import dev.lightdream.databasemanager.annotations.database.DatabaseField;
import dev.lightdream.databasemanager.annotations.database.DatabaseTable;
import dev.lightdream.databasemanager.dto.DatabaseEntry;
import dev.lightdream.rustcore.Main;

@DatabaseTable(table = "bans")
public class Ban extends DatabaseEntry {

    @DatabaseField(columnName = "user")
    public User user;
    @DatabaseField(columnName = "expire")
    public Long expire;
    @DatabaseField(columnName = "reason")
    public String reason;
    @DatabaseField(columnName = "by")
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
    }

    @SuppressWarnings("unused")
    public Ban() {
        super(Main.instance);
    }

    public void unban() {
        this.expire = 0L;
    }
}
