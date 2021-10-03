package dev.lightdream.rustcore.utils;

import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.dto.Enchant;

import java.util.concurrent.atomic.AtomicReference;

public class Utils extends dev.lightdream.api.utils.Utils {

    public static Enchant getEnchantByID(String id){
        AtomicReference<Enchant> enchant = new AtomicReference<>();

        Main.instance.config.enchantsList.forEach(e->{
            if(e.id.equals(id)){
                enchant.set(e);
            }
        });

        return enchant.get();
    }



}
