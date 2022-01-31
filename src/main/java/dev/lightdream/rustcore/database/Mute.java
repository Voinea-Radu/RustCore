package dev.lightdream.rustcore.database;

import dev.lightdream.databasemanager.annotations.database.DatabaseField;
import dev.lightdream.databasemanager.annotations.database.DatabaseTable;
import dev.lightdream.databasemanager.dto.DatabaseEntry;
import dev.lightdream.rustcore.Main;

@DatabaseTable(table = "mutes")
public class Mute extends DatabaseEntry {

    @DatabaseField(columnName = "user")
    public User user;
    @DatabaseField(columnName = "expire")
    public Long expire;
    @DatabaseField(columnName = "reason")
    public String reason;
    @DatabaseField(columnName = "by")
    public User by;

    public Mute(User user, User by, Long duration, String reason) {
        super(Main.instance);
        this.user = user;
        this.by = by;
        this.expire = System.currentTimeMillis() + duration;
        this.reason = reason;
        save();
    }

    @SuppressWarnings("unused")
    public Mute() {
        super(Main.instance);
    }

    public void unmute() {
        this.expire = 0L;
    }
}
