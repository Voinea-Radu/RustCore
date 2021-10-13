package dev.lightdream.rustcore.utils;

import dev.lightdream.api.utils.MessageBuilder;
import dev.lightdream.rustcore.Main;
import dev.lightdream.rustcore.dto.Enchant;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Utils extends dev.lightdream.api.utils.Utils {

    public static Enchant getEnchantByID(String id) {
        AtomicReference<Enchant> enchant = new AtomicReference<>();

        Main.instance.config.enchantsList.forEach(e -> {
            if (e.id.equals(id)) {
                enchant.set(e);
            }
        });

        return enchant.get();
    }

    public static Long stringToPeriod(String timeString) {
        char timeDeterminant = timeString.charAt(timeString.length() - 1);
        String periodString = timeString.replace(Character.toString(timeDeterminant), "");
        long period;

        try {
            period = Integer.parseInt(periodString);
        } catch (NumberFormatException e) {
            return null;
        }

        switch (timeDeterminant) {
            case 'd':
                period *= 24L;
            case 'h':
                period *= 60L;
            case 'm':
                period *= 60L;
            case 's':
                period *= 1000L;
        }

        return period;
    }

    public static String msToDate(long ms) {
        int d = (int) (ms / 1000 / 60 / 60 / 24);
        ms -= d * 1000 * 60 * 60 * 24L;
        int h = (int) (ms / 1000 / 60 / 60);
        ms -= h * 1000 * 60 * 60L;
        int m = (int) (ms / 1000 / 60);
        ms -= m * 1000 * 60L;
        int s = (int) (ms / 1000);
        return new MessageBuilder("%d%d %h%h %m%m %s%s").addPlaceholders(new HashMap<String, String>() {{
            put("d", String.valueOf(d));
            put("h", String.valueOf(h));
            put("m", String.valueOf(m));
            put("s", String.valueOf(s));
        }}).parseString();
    }


}
