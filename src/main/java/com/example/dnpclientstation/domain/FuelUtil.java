package com.example.dnpclientstation.domain;

import com.example.dnpclientstation.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class FuelUtil {
    static final Logger log =
            LoggerFactory.getLogger(FuelUtil.class);

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
