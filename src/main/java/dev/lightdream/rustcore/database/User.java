package dev.lightdream.rustcore.database;


import dev.lightdream.api.utils.ItemBuilder;
import dev.lightdream.api.utils.ScoreBoardUtils;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.dto.Recipe;
import lombok.NoArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
public class User extends dev.lightdream.api.databases.User {

    private final List<Recipe> activeRecipes = new ArrayList<>();
    private int recipeProgress = 0;

    public User(UUID uuid, String name, String lang) {
        super(uuid, name, lang);
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
}
