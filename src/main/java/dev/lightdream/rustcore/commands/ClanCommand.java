package dev.lightdream.rustcore.commands;

import dev.lightdream.api.IAPI;
import dev.lightdream.api.managers.MessageManager;
import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.database.Clan;
import dev.lightdream.rustcore.database.User;

import java.util.HashMap;
import java.util.List;

@dev.lightdream.api.annotations.commands.SubCommand(
        parent = Main.MainCommand.class,
        command =  "clan",
        onlyForPlayers = true,
        usage = "[create/join//invite/kick/giveOwner//leave/delete/info] [name//user]"
)
public class ClanCommand extends SubCommand {
    public ClanCommand(IAPI api) {
        super(api);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void execute(User user, List<String> args) {
        if (args.size() == 0) {
            sendUsage(user);
            return;
        }

        String command = args.get(0);
        String name;
        String targetName;
        User target;
        Clan clan;
        Clan targetClan;

        if (user == null) {
            sendUsage(user);
            return;
        }

        switch (command.toLowerCase()) {
            case "create":
                if (args.size() != 2) {
                    sendUsage(user);
                    return;
                }

                name = args.get(1);
                Clan c1 = user.getClan();
                Clan c2 = Main.instance.databaseManager.getClan(name);

                if (c1 != null) {
                    MessageManager.sendMessage(user, Main.instance.lang.alreadyInClan);
                    return;
                }

                if (c2 != null) {
                    MessageManager.sendMessage(user, Main.instance.lang.clanAlreadyExists);
                    return;
                }

                new Clan(user, name);
                break;
            case "join":
                if (args.size() != 2) {
                    sendUsage(user);
                    return;
                }

                name = args.get(1);
                clan = Main.instance.databaseManager.getClan(name);

                if (clan == null) {
                    MessageManager.sendMessage(user, Main.instance.lang.invalidClan);
                    return;
                }

                if (clan.invites.contains(user.id)) {
                    clan.join(user);
                    return;
                }

                MessageManager.sendMessage(user, Main.instance.lang.notInvited);
                break;
            case "invite":
                if (args.size() != 2) {
                    sendUsage(user);
                    return;
                }

                targetName = args.get(1);
                target = Main.instance.databaseManager.getUser(targetName);
                clan = user.getClan();

                if (clan == null || !clan.owner.equals(user)) {
                    MessageManager.sendMessage(user, Main.instance.lang.notOwnClan);
                    return;
                }

                if (target == null) {
                    MessageManager.sendMessage(user, Main.instance.lang.invalidUser);
                    return;
                }

                if (target.getClan() != null) {
                    MessageManager.sendMessage(user, Main.instance.lang.targetAlreadyInClan);
                    return;
                }

                clan.invite(target);
                break;
            case "kick":
                if (args.size() != 2) {
                    sendUsage(user);
                    return;
                }

                targetName = args.get(1);
                target = Main.instance.databaseManager.getUser(targetName);
                clan = user.getClan();

                if (clan == null || !clan.owner.equals(user)) {
                    MessageManager.sendMessage(user, Main.instance.lang.notOwnClan);
                    return;
                }

                if (target == null) {
                    MessageManager.sendMessage(user, Main.instance.lang.invalidUser);
                    return;
                }

                targetClan = target.getClan();

                if (clan != targetClan) {
                    MessageManager.sendMessage(user, Main.instance.lang.notSameClan);
                    return;
                }

                clan.leave(target);
                break;
            case "giveowner":
                if (args.size() != 2) {
                    sendUsage(user);
                    return;
                }

                targetName = args.get(1);
                target = Main.instance.databaseManager.getUser(targetName);
                clan = user.getClan();

                if (clan == null || !clan.owner.equals(user)) {
                    MessageManager.sendMessage(user, Main.instance.lang.notOwnClan);
                    return;
                }

                if (target == null) {
                    MessageManager.sendMessage(user, Main.instance.lang.invalidUser);
                    return;
                }

                targetClan = target.getClan();

                if (clan != targetClan) {
                    MessageManager.sendMessage(user, Main.instance.lang.notSameClan);
                    return;
                }

                clan.changeOwner(target);
                break;
            case "leave":
                clan = user.getClan();

                if (clan == null) {
                    MessageManager.sendMessage(user, Main.instance.lang.notInClan);
                    return;
                }

                if (clan.owner.equals(user)) {
                    MessageManager.sendMessage(user, Main.instance.lang.mustFirstDelete);
                    return;
                }
                clan.leave(user);
                break;
            case "delete":
                clan = user.getClan();

                if (clan == null) {
                    MessageManager.sendMessage(user, Main.instance.lang.notInClan);
                    return;
                }

                if (!clan.owner.equals(user)) {
                    MessageManager.sendMessage(user, Main.instance.lang.notOwnClan);
                    return;
                }
                clan.delete();
                break;
            case "info":
                clan = user.getClan();

                if (clan == null) {
                    return;
                }

                StringBuilder members = new StringBuilder();

                for (Integer memberID : clan.members) {
                    members.append(Main.instance.databaseManager.getUser(memberID).name).append(", ");
                }
                members.append(",");
                members = new StringBuilder(members.toString().replace(", ,", ""));

                StringBuilder finalMembers = members;
                MessageManager.sendMessage(user, new MessageBuilder(Main.instance.lang.clanInfo).addPlaceholders(new HashMap<String, String>() {{
                    put("clan_name", clan.name);
                    put("clan_owner", clan.owner.name);
                    put("clan_members", finalMembers.toString());
                    put("clan_members_count", String.valueOf(clan.members.size()));
                }}));
                break;
            default:
                sendUsage(user);
                break;
        }
    }

    @Override
    public List<String> onTabComplete(User user, List<String> list) {
        return null;
    }
}
