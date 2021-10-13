package dev.lightdream.rustcore.database;

import dev.lightdream.api.databases.EditableDatabaseEntry;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.libs.j256.table.DatabaseTable;
import dev.lightdream.rustcore.Main;

import java.util.HashMap;
import java.util.HashSet;

@DatabaseTable(tableName = "clans")
public class Clan extends EditableDatabaseEntry {

    @SuppressWarnings("unused")
    @DatabaseField(columnName = "id", generatedId = true, canBeNull = false)
    public int id;
    @DatabaseField(columnName = "members", dataType = DataType.SERIALIZABLE)
    public HashSet<Integer> members = new HashSet<>();
    @DatabaseField(columnName = "invites", dataType = DataType.SERIALIZABLE)
    public HashSet<Integer> invites = new HashSet<>();
    @DatabaseField(columnName = "owner", foreign = true)
    public User owner;
    @DatabaseField(columnName = "tag")
    public String name;

    public Clan(User user, String name) {
        super(Main.instance);
        this.owner = user;
        this.name = name;
        this.members.add(owner.id);
        save();
        Main.instance.getMessageManager().sendMessage(user, Main.instance.lang.clanCleared);
    }

    @SuppressWarnings("unused")
    public Clan() {
        super(Main.instance);
        save();
    }

    @SuppressWarnings("unused")
    public void changeName(String name) {
        this.name = name;
        Main.instance.getMessageManager().sendMessage(owner, Main.instance.lang.nameChanged);
        save();
    }

    public void changeOwner(User user) {
        this.owner = user;
        sendMessageToMembers(new MessageBuilder(Main.instance.lang.newOwner).addPlaceholders(new HashMap<String, String>() {{
            put("owner_name", owner.name);
        }}));
        save();
    }

    public void invite(User user) {
        this.invites.add(user.id);
        Main.instance.getMessageManager().sendMessage(user, new MessageBuilder(Main.instance.lang.invitedClan).addPlaceholders(new HashMap<String, String>() {{
            put("clan_name", name);
        }}));
        Main.instance.getMessageManager().sendMessage(owner, Main.instance.lang.clanInviteSent);
        save();
    }

    public void join(User user) {
        this.invites.remove(user.id);
        this.members.add(user.id);
        sendMessageToMembers(new MessageBuilder(Main.instance.lang.joinedClan).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", user.name);
        }}));
        save();
    }

    public void leave(User user) {
        this.members.remove(user.id);
        sendMessageToMembers(new MessageBuilder(Main.instance.lang.leftClan).addPlaceholders(new HashMap<String, String>() {{
            put("player_name", user.name);
        }}));
        save();
    }

    public void sendMessageToMembers(MessageBuilder message) {
        members.forEach(memberID -> {
            User member = Main.instance.databaseManager.getUser(memberID);
            if (member == null || !member.isOnline()) {
                return;
            }
            Main.instance.getMessageManager().sendMessage(member, message);
        });
        if (owner.isOnline()) {
            Main.instance.getMessageManager().sendMessage(owner, message);
        }
    }

    @Override
    public Integer getID() {
        return id;
    }
}
