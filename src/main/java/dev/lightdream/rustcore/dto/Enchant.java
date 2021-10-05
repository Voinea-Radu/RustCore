package dev.lightdream.rustcore.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
public class Enchant {

    public String id;
    public String name;
    public HashMap<Integer, Integer> levels;

}
