package com.example.dnpclientstation.config;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

@Service
public class DateFormatter extends org.springframework.format.datetime.DateFormatter {
    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if (text != null && text.isEmpty()) {
            return null;
        }
        return super.parse(text, locale);
    }

}