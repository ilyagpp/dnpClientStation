package com.example.dnpclientstation.domain;

import java.util.Locale;

public class FuelUtil {

    public static String convert(String fuel){

        fuel = fuel.toUpperCase(Locale.ROOT);
        switch (fuel){

            case "ДТ": return Fuel.DIESEL.name();

            case "АИ-92": return Fuel.AI_92.name();

            case "АИ-95": return Fuel.AI_95.name();

            case "СУГ": return Fuel.SUG.name();

            default: throw new NumberFormatException("Неизвестный вид топлива");
        }

    }

}
