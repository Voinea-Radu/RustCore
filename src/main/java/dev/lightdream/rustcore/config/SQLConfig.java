package dev.lightdream.rustcore.config;

import dev.lightdream.api.dto.location.PluginLocation;
import dev.lightdream.databasemanager.dto.DriverConfig;
import dev.lightdream.rustcore.database.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class SQLConfig extends dev.lightdream.databasemanager.dto.SQLConfig {
    public SQLConfig() {
        super();
        super.MYSQL = new DriverConfig.Driver("SELECT * FROM %table% WHERE %placeholder%", "SELECT * FROM %table% WHERE 1", "UPDATE %table% SET %placeholder% WHERE id=?", "INSERT INTO %table% (%placeholder-1%) VALUES(%placeholder-2%)", "CREATE TABLE IF NOT EXISTS %table% (%placeholder%, PRIMARY KEY(%keys%))", "DELETE FROM %table% WHERE id=?", new HashMap<Class<?>, String>() {
            {
                this.put(Integer.TYPE, "INT");
                this.put(Integer.class, "INT");
                this.put(String.class, "TEXT");
                this.put(Boolean.TYPE, "BOOLEAN");
                this.put(Boolean.class, "BOOLEAN");
                this.put(Float.TYPE, "FLOAT");
                this.put(Float.class, "FLOAT");
                this.put(Double.TYPE, "DOUBLE");
                this.put(Double.class, "DOUBLE");
                this.put(UUID.class, "TEXT");
                this.put(Long.class, "BIGINT");
                this.put(Long.TYPE, "BIGINT");
                this.put(HashSet.class,"BLOB");
                this.put(PluginLocation.class,"BLOB");
                this.put(User.class,"BLOB");
            }
        }, "AUTO_INCREMENT", "ORDER BY %order% DESC", "ORDER BY %order% ASC", "LIMIT %limit%");
        super.MARIADB = new DriverConfig.Driver(this.MYSQL);
        super.SQLSERVER = new DriverConfig.Driver(this.MYSQL);
        super.POSTGRESQL = new DriverConfig.Driver(this.MYSQL);
        super.H2 = new DriverConfig.Driver(this.MYSQL);
        super.SQLITE = new DriverConfig.Driver("SELECT * FROM %table% WHERE %placeholder% %order% %limit%", "SELECT * FROM %table% WHERE 1", "UPDATE %table% SET %placeholder% WHERE id=?", "INSERT INTO %table% (%placeholder-1%) VALUES(%placeholder-2%)", "CREATE TABLE IF NOT EXISTS %table% (%placeholder%)", "DELETE FROM %table% WHERE id=?", new HashMap<Class<?>, String>() {
            {
                this.put(Integer.TYPE, "INTEGER");
                this.put(Integer.class, "INTEGER");
                this.put(String.class, "TEXT");
                this.put(Boolean.TYPE, "BOOLEAN");
                this.put(Boolean.class, "BOOLEAN");
                this.put(Float.TYPE, "REAL");
                this.put(Float.class, "REAL");
                this.put(Double.TYPE, "REAL");
                this.put(Double.class, "REAL");
                this.put(UUID.class, "TEXT");
                this.put(Long.class, "BIGINT");
                this.put(Long.TYPE, "BIGINT");
                this.put(HashSet.class,"BLOB");
                this.put(PluginLocation.class,"BLOB");
                this.put(User.class,"BLOB");
            }
        }, "PRIMARY KEY AUTOINCREMENT", "ORDER BY %order% DESC", "ORDER BY %order% ASC", "LIMIT %limit%");
    }
}
