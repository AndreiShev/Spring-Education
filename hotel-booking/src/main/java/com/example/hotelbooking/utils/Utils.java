package com.example.hotelbooking.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.math.BigDecimal;

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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
