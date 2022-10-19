package com.example.dnpclientstation.domain;

import java.util.Locale;

public class FuelUtil {

    public static String convert(String fuel){

        fuel = fuel.toUpperCase(Locale.ROOT);

        if (fuel.contains("ДТ")) {
            return Fuel.DIESEL.name();
        } else if (fuel.contains("92")) {
            return Fuel.AI_92.name();
        } else if (fuel.contains("95")) {
            return Fuel.AI_95.name();
        } else if (fuel.contains("СУГ") || fuel.contains("ГАЗ")) {
            return Fuel.SUG.name();
        }
        throw new NumberFormatException("Неизвестный вид топлива");

    }

}
