package dev.lightdream.rustcore.database;

import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.databasemanager.annotations.database.DatabaseField;
import dev.lightdream.databasemanager.annotations.database.DatabaseTable;
import dev.lightdream.databasemanager.dto.DatabaseEntry;
import dev.lightdream.rustcore.Main;

import java.util.HashMap;
import java.util.HashSet;

@DatabaseTable(table = "clans")
public class Clan extends DatabaseEntry {

    @DatabaseField(columnName = "members")
    public HashSet<Integer> members = new HashSet<>();
    @DatabaseField(columnName = "invites")
    public HashSet<Integer> invites = new HashSet<>();
    @DatabaseField(columnName = "owner")
    public User owner;
    @DatabaseField(columnName = "tag")
    public String name;

    public Clan(User user, String name) {
        super(Main.instance);
        this.owner = user;
        this.name = name;
        this.members.add(owner.id);
        MessageManager.sendMessage(user, Main.instance.lang.clanCleared);
    }

    @SuppressWarnings("unused")
    public Clan() {
        super(Main.instance);
    }

    @SuppressWarnings("unused")
    public void changeName(String name) {
        this.name = name;
        MessageManager.sendMessage(owner, Main.instance.lang.nameChanged);
        this.save();
    }

    public void changeOwner(User user) {
        this.owner = user;
        sendMessageToMembers(new MessageBuilder(Main.instance.lang.newOwner).addPlaceholders(new HashMap<String, String>() {{
            put("owner_name", owner.name);
        }}));
        this.save();
    }

    public void invite(User user) {
        this.invites.add(user.id);
        MessageManager.sendMessage(user, new MessageBuilder(Main.instance.lang.invitedClan).addPlaceholders(new HashMap<String, String>() {{
            put("clan_name", name);
        }}));
        MessageManager.sendMessage(owner, Main.instance.lang.clanInviteSent);
        this.save();
    }

    public void join(User user) {
        this.invites.remove(user.id);
        this.members.add(user.id);
        sendMessageToMembers(new MessageBuilder(Main.instance.lang.joinedClan).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", user.name);
        }}));
        this.save();
    }

    public void leave(User user) {
        this.members.remove(user.id);
        sendMessageToMembers(new MessageBuilder(Main.instance.lang.leftClan).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", user.name);
        }}));
        this.save();
    }

    public void sendMessageToMembers(MessageBuilder message) {
        members.forEach(memberID -> {
            User member = Main.instance.databaseManager.getUser(memberID);
            if (member == null || !member.isOnline()) {
                return;
            }
            MessageManager.sendMessage(member, message);
        });
        if (owner.isOnline()) {
            MessageManager.sendMessage(owner, message);
        }
    }

}
