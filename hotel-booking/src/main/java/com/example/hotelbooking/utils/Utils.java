package com.example.hotelbooking.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class Utils {
    @SneakyThrows
    public static void copyNonNullValues(Object from, Object to) {
        Field[] fields = from.getClass().getDeclaredFields();

        for (Field item: fields) {
            item.setAccessible(true);
            Object value = item.get(from);

            if (value != null) {
                item.set(to, value);
            }
        }
    }
}
