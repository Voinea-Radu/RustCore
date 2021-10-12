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

    public static Long stringToPeriod(String timeString){
        char timeDeterminant = timeString.charAt(timeString.length() - 1);
        String periodString = timeString.replace(Character.toString(timeDeterminant), "");
        long period;

        try {
            period = Integer.parseInt(periodString);
        } catch (NumberFormatException e) {
            return null;
        }

        switch (timeDeterminant) {
            case 's':
                period *= 1000L;
            case 'm':
                period *= 60L;
            case 'h':
                period *= 60L;
            case 'd':
                period *= 24L;
        }

        return period;
    }



}
