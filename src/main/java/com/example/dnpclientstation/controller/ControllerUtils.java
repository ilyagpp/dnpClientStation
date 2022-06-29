package com.example.dnpclientstation.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    static String checkByСomma (String s){

        return s.replace(',' , '.');
    }

    static String[] checkByComa (String[] strings){

        for (int i = 0; i < strings.length; i++){

            strings[i] = checkByСomma(strings[i]);
        }
        return strings;
    }

}
