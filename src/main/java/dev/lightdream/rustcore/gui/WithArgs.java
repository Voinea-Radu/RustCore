package dev.lightdream.rustcore.gui;

import lombok.var;

import java.util.HashMap;

public interface WithArgs {
    HashMap<Class<?>, Object> getArgs();
    default  <T> T getArg(Class<T> clazz) {
        var value = this.getArgs().getOrDefault(clazz, null);
        return clazz.isInstance(value) ? clazz.cast(value) : null;
    }
}
