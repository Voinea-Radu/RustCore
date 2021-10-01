package dev.lightdream.rustcore.dto;

import dev.lightdream.api.databases.User;
import dev.lightdream.api.dto.Item;
import dev.lightdream.api.dto.XMaterial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    public String id;
    public HashMap<XMaterial, Integer> cost;
    public Item result;
    public int time;
    public String name;
    public List<String> description;

    public Recipe(String id, HashMap<XMaterial, Integer> cost, Item result, int time, String name, String description) {
        this.id = id;
        this.cost = cost;
        this.result = result;
        this.time = time;
        this.name = name;
        ArrayList<String> desc = new ArrayList<>();
        desc.add(description);
        this.description = desc;
    }

    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "ConstantConditions"})
    public boolean has(User user) {
        if (!user.isOnline()) {
            return false;
        }

        HashMap<XMaterial, Integer> itemsMap = new HashMap<>();

        for (ItemStack itemStack : user.getPlayer().getInventory()) {
            if (itemStack == null) {
                continue;
            }
            itemsMap.put(XMaterial.matchXMaterial(itemStack), itemsMap.getOrDefault(XMaterial.matchXMaterial(itemStack), 0) + itemStack.getAmount());
        }

        for (XMaterial xMaterial : cost.keySet()) {
            if (itemsMap.getOrDefault(xMaterial, 0) < cost.get(xMaterial)) {
                return false;
            }
        }

        return true;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public boolean take(User user) {
        if (!has(user)) {
            return false;
        }

        HashMap<XMaterial, Integer> itemsMap = (HashMap<XMaterial, Integer>) cost.clone();

        for (int i = 0; i < user.getPlayer().getInventory().getSize(); i++) {
            ItemStack item = user.getPlayer().getInventory().getItem(i);

            if (item == null) {
                continue;
            }

            int amountNeeded = itemsMap.getOrDefault(XMaterial.matchXMaterial(item), 0);
            int toRemove = Math.min(amountNeeded, item.getAmount());
            item.setAmount(item.getAmount() - toRemove);
            if (item.getAmount() == 0) {
                user.getPlayer().getInventory().setItem(i, null);
            } else {
                user.getPlayer().getInventory().setItem(i, item);
            }
            itemsMap.put(XMaterial.matchXMaterial(item), amountNeeded - toRemove);
        }
        return true;
    }


}
