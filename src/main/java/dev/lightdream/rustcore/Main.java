package dev.lightdream.rustcore;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.LightDreamPlugin;
import dev.lightdream.api.commands.BaseCommand;
import dev.lightdream.api.commands.SubCommand;
import dev.lightdream.api.configs.JdaConfig;
import dev.lightdream.api.databases.User;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.commands.*;
import dev.lightdream.rustcore.commands.bans.BanCommand;
import dev.lightdream.rustcore.commands.bans.BanIpCommand;
import dev.lightdream.rustcore.commands.bans.UnbanCommand;
import dev.lightdream.rustcore.commands.mutes.MuteCommand;
import dev.lightdream.rustcore.commands.mutes.UnMuteCommand;
import dev.lightdream.rustcore.config.Config;
import dev.lightdream.rustcore.config.Data;
import dev.lightdream.rustcore.config.Lang;
import dev.lightdream.rustcore.config.SQLConfig;
import dev.lightdream.rustcore.managers.DatabaseManager;
import dev.lightdream.rustcore.managers.EventManager;
import dev.lightdream.rustcore.managers.ScheduleManager;
import lombok.var;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public final class Main extends LightDreamPlugin {

    public static Main instance;

    //Settings
    public Config config;
    public Lang lang;
    public Data data;

    //Managers
    public DatabaseManager databaseManager;
    public EventManager eventManager;

    //Main Command
    public MainCommand command;

    public Main() {
        instance = this;
    }

    @Override
    public void onEnable() {
        init("RustCore", "rc");
        instance = this;
        eventManager = new EventManager(this);
        command = new MainCommand(this);
        loadBaseCommands();
        new ScheduleManager(this);
    }


    @Override
    public @NotNull String parsePapi(OfflinePlayer offlinePlayer, String s) {
        return "";
    }

    @Override
    public void loadConfigs() {
        data = fileManager.load(Data.class);
        config = fileManager.load(Config.class);
        lang = fileManager.load(Lang.class);
        this.baseJdaConfig = this.fileManager.load(JdaConfig.class);
        this.baseLang = lang;
        this.sqlConfig = this.fileManager.load(SQLConfig.class);
    }

    @Override
    public dev.lightdream.api.managers.DatabaseManager registerDatabaseManager() {
        if (databaseManager == null) databaseManager = new DatabaseManager(this);
        return databaseManager;
    }

    @Override
    public void disable() {
        fileManager.save(data);
    }

    @Override
    public void registerFileManagerModules() {

    }

    @Override
    public void registerUser(Player player) {

    }

    public void loadBaseCommands() {
        var baseSubCommands = command.subCommands;
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
        baseSubCommands.add(new ExpertCommand(this));
    }


    @Override
    public DatabaseManager getDatabaseManager() {
        if (databaseManager == null) databaseManager = new DatabaseManager(this);
        return databaseManager;
    }
    public static class MainCommand extends BaseCommand {
        public MainCommand(IAPI api) {
            super(api);
        }

        @Override
        public void execute(User user, List<String> list) {
            Iterator<SubCommand> var5;
            SubCommand subCommand;
            if (list.size() == 0) {
                var5 = this.subCommands.iterator();

                do {
                    if (!var5.hasNext()) {
                        this.sendUsage(user);
                        return;
                    }

                    subCommand = var5.next();
                } while(!subCommand.getCommand().equals(""));

                subCommand.execute(user, list);
            } else {
                var5 = this.subCommands.iterator();

                do {
                    if (!var5.hasNext()) {
                        MessageManager.sendMessage(user, new MessageBuilder(this.api.getLang().unknownCommand));
                        return;
                    }

                    subCommand = var5.next();
                } while(!(subCommand.getCommand().contains(list.get(0).toLowerCase())));

                if (subCommand.onlyForPlayers() && user.getPlayer() == null) {
                    MessageManager.sendMessage(user, new MessageBuilder(this.api.getLang().mustBeAPlayer));
                } else if (subCommand.onlyForConsole() && user.getPlayer() != null) {
                    MessageManager.sendMessage(user, new MessageBuilder(this.api.getLang().mustBeConsole));
                } else if (user.hasPermission(subCommand.getPermission())) {
                    MessageManager.sendMessage(user, new MessageBuilder(this.api.getLang().noPermission));
                } else {
                    subCommand.execute(user, new ArrayList<>(list.subList(1, list.size())));
                }
            }
        }

        @Override
        public List<String> onTabComplete(User user, List<String> list) {
            if (list == null) return Collections.emptyList();
            if (list.size() < 2) {
                var result = new ArrayList<String>();
                if (list.isEmpty()) subCommands
                        .stream()
                        .filter(subCommand -> user.hasPermission(subCommand.getPermission()))
                        .forEach(subCommand -> result.add(subCommand.getCommand()));
                else subCommands
                        .stream()
                        .filter(subCommand -> user.hasPermission(subCommand.getPermission()) && subCommand.getCommand().startsWith(list.get(0)))
                        .forEach(subcommand -> result.add(subcommand.getCommand()));
                return result;
            }
            SubCommand command = null;
            for (var command1 : subCommands) {
                if (command1.getCommand().equalsIgnoreCase(list.get(0))) {
                    command = command1;
                    break;
                }
            }
            if (command == null) sendUsage(user);
            else return command.onTabComplete(user,new ArrayList<>(list.subList(1, list.size())));
            return Collections.emptyList();
        }
    }
}
