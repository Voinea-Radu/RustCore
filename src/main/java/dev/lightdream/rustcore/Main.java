package dev.lightdream.rustcore;

import dev.lightdream.api.API;
import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.configs.SQLConfig;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.rustcore.commands.*;
import dev.lightdream.rustcore.commands.bans.BanCommand;
import dev.lightdream.rustcore.commands.bans.BanIpCommand;
import dev.lightdream.rustcore.commands.bans.UnbanCommand;
import dev.lightdream.rustcore.commands.mutes.MuteCommand;
import dev.lightdream.rustcore.commands.mutes.UnMuteCommand;
import dev.lightdream.rustcore.config.Config;
import dev.lightdream.rustcore.config.Data;
import dev.lightdream.rustcore.config.Lang;
import dev.lightdream.rustcore.managers.DatabaseManager;
import dev.lightdream.rustcore.managers.EventManager;
import dev.lightdream.rustcore.managers.ScheduleManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class Main extends LightDreamPlugin {

    public static Main instance;

    //Settings
    public Config config;
    public Lang lang;
    public Data data;

    //Managers
    public DatabaseManager databaseManager;
    public EventManager eventManager;

    @Override
    public void onEnable() {
        init("RustCore", "rc", "1.0");
        instance = this;
        databaseManager = new DatabaseManager(this);
        eventManager = new EventManager(this);
        new ScheduleManager(this);
    }


    @Override
    public @NotNull String parsePapi(OfflinePlayer offlinePlayer, String s) {
        return "";
    }

    @Override
    public void loadConfigs() {
        sqlConfig = fileManager.load(SQLConfig.class);
        config = fileManager.load(Config.class);
        baseConfig = config;
        lang = fileManager.load(Lang.class, fileManager.getFile(baseConfig.baseLang));
        baseLang = lang;
        data = fileManager.load(Data.class);
    }

    @Override
    public void disable() {
        fileManager.save(data);
    }

    @Override
    public void registerFileManagerModules() {

    }

    @Override
    public void loadBaseCommands() {
        baseSubCommands.add(new GiveCommand(this));
        baseSubCommands.add(new ClanCommand(this));
        baseSubCommands.add(new VanishCommand(this));
        baseSubCommands.add(new GamemodeCommand(this));
        baseSubCommands.add(new BanCommand(this));
        baseSubCommands.add(new BanIpCommand(this));
        baseSubCommands.add(new UnbanCommand(this));
        baseSubCommands.add(new MuteCommand(this));
        baseSubCommands.add(new UnMuteCommand(this));
        baseSubCommands.add(new GodCommand(this));
        baseSubCommands.add(new SpeedCommand(this));
        baseSubCommands.add(new TimeCommand(this));
        baseSubCommands.add(new WeatherCommand(this));
        baseSubCommands.add(new FeedCommand(this));
        baseSubCommands.add(new HealCommand(this));
        baseSubCommands.add(new KickCommand(this));
        baseSubCommands.add(new ListCommand(this));
        baseSubCommands.add(new TPHereCommand(this));
    }

    @Override
    public MessageManager instantiateMessageManager() {
        return new MessageManager(this, Main.class);
    }

    @Override
    public void registerLangManager() {
        API.instance.langManager.register(Main.class, getLangs());
    }

    @Override
    public HashMap<String, Object> getLangs() {
        HashMap<String, Object> langs = new HashMap<>();

        baseConfig.langs.forEach(lang -> {
            Lang l = fileManager.load(Lang.class, fileManager.getFile(lang));
            langs.put(lang, l);
        });

        return langs;
    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    @Override
    public void setLang(Player player, String s) {
        User user = databaseManager.getUser(player);
        user.setLang(s);
        databaseManager.save(user);
    }


}
