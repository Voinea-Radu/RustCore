package dev.lightdream.rustcore.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public class Enchant {

    public String id;
    public String name;
    //public Enchantment enchant;
    public HashMap<Integer, Integer> levels;

}
