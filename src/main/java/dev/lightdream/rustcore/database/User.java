package dev.lightdream.rustcore.database;


import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.api.utils.ScoreBoardUtils;
import dev.lightdream.libs.fasterxml.annotation.JsonIgnore;
import dev.lightdream.libs.j256.field.DataType;
import dev.lightdream.libs.j256.field.DatabaseField;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.dto.Recipe;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class User extends dev.lightdream.api.databases.User {

    private final List<Recipe> activeRecipes = new ArrayList<>();
    @DatabaseField(columnName = "vanished")
    public boolean vanished;
    @DatabaseField(columnName = "god")
    public boolean god;
    @DatabaseField(columnName = "ip", dataType = DataType.SERIALIZABLE)
    public HashSet<String> ips;
    private int recipeProgress = 0;

    public User(UUID uuid, String name, String lang, String ip) {
        super(Main.instance, uuid, name, lang);
        this.vanished = false;
        this.god = false;
        this.ips = new HashSet<>();
        this.ips.add(ip);
    }

    @SuppressWarnings("ConstantConditions")
    public void processRecipe() {
        if (activeRecipes.size() == 0) {
            return;
        }

        recipeProgress++;
        if (recipeProgress >= activeRecipes.get(0).time) {
            recipeProgress = 0;
            if (!isOnline()) {
                return;
            }
            getPlayer().getInventory().addItem(ItemBuilder.makeItem(activeRecipes.get(0).result));
            activeRecipes.remove(0);
        }

        new BukkitRunnable() {
            public void run() {
                processRecipeScoreBoard();
            }
        }.runTask(Main.instance);
    }

    public void processRecipeScoreBoard() {
        if (activeRecipes.size() == 0) {
            return;
        }

        ScoreBoardUtils sb = new ScoreBoardUtils(Main.instance.lang.recipesScoreBoardTitle);

        sb.add(activeRecipes.get(0).name + " " + (activeRecipes.get(0).time - recipeProgress));

        for (int i = 0; i < activeRecipes.size(); i++) {
            if (i == 0) {
                continue;
            }
            sb.add(activeRecipes.get(i).name + " " + activeRecipes.get(i).time);
        }

        sb.build();
        sb.send(getPlayer());
    }

    @SuppressWarnings("ConstantConditions")
    public void addRecipe(String recipeID) {

        AtomicInteger max = new AtomicInteger();

        Main.instance.config.maxNumberOfCrafts.forEach((perm, number) -> {
            if (getPlayer().hasPermission(perm)) {
                max.set(Math.max(max.get(), number));
            }
        });

        if (activeRecipes.size() >= max.get()) {
            return;
        }

        List<List<Recipe>> recipes = Arrays.asList(
                Main.instance.config.generalRecipes,
                Main.instance.config.foodRecipes,
                Main.instance.config.toolsRecipes,
                Main.instance.config.armourRecipes,
                Main.instance.config.battleRecipes,
                Main.instance.config.rareRecipes
        );

        for (List<Recipe> recipe : recipes) {
            for (Recipe generalRecipe : recipe) {
                if (generalRecipe.id.equals(recipeID)) {
                    addRecipe(generalRecipe);
                    return;
                }
            }
        }
    }

    public void addRecipe(Recipe recipe) {
        if (!recipe.take(this)) {
            return;
        }
        this.activeRecipes.add(recipe);
    }

    @JsonIgnore
    public @Nullable Clan getClan() {
        return Main.instance.databaseManager.getClan(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "activeRecipes=" + activeRecipes +
                ", recipeProgress=" + recipeProgress +
                ", id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }

    @SuppressWarnings("ConstantConditions")
    public void setVanished(boolean vanished) {
        this.vanished = vanished;
        if (!isOnline()) {
            return;
        }
        if (vanished) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.hidePlayer(getPlayer());
            }
            getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false));
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.showPlayer(getPlayer());
            }
            getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void ban(User by, Long duration, String reason, boolean ip) {
        new Ban(this, by, duration, reason, ip);
        if (isOnline()) {
            getPlayer().kickPlayer("");
        }
    }

    public void unban() {
        if (!isBanned()) {
            return;
        }
        getBan().unban();
    }

    public void mute(User by, Long duration, String reason) {
        new Mute(this, by, duration, reason);
    }

    public void unmute() {
        if (!isMuted()) {
            return;
        }
        getMute().unmute();
    }

    public boolean isBanned() {
        return getBan() != null;
    }

    public boolean isMuted() {
        return getMute() != null;
    }

    public Ban getBan() {
        return Main.instance.databaseManager.getBan(this);
    }

    public Mute getMute() {
        return Main.instance.databaseManager.getMute(this);
    }
}
