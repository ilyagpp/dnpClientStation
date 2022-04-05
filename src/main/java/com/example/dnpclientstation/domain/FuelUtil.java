package com.example.dnpclientstation.domain;

public class FuelUtil {

    public static String convert(String fuel){

        switch (fuel){
            case "Дт": return Fuel.DIESEL.name();

            case "Аи-92": return Fuel.AI_92.name();

            case "Аи-95": return Fuel.AI_95.name();

            case "СУГ": return Fuel.SUG.name();

            default: throw new NumberFormatException("Неизвестный вид топлива");
        }

    }

}
