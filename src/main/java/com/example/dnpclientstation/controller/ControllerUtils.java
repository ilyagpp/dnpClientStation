package com.example.dnpclientstation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    public static Map<String, String> getErrors(BindingResult bindingResult) {
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

    public static void initPageSize(Model model, Integer size, @PageableDefault Pageable pageable){
        if (size != null) {
            model.addAttribute("size", size);
        }else model.addAttribute("size", pageable.getPageSize());
    }

    public static Page<?> listToPage(Pageable pageable, List<?> entities) {
        int lowerBound = pageable.getPageNumber() * pageable.getPageSize();
        int upperBound = Math.min(lowerBound + pageable.getPageSize(), entities.size());
        List<?> subList = new ArrayList<>();
        try {
            subList = entities.subList(lowerBound, upperBound);
        } catch (Exception e) {
            if (pageable.getPageSize() <= entities.size()) {
                subList = entities.subList(0, pageable.getPageSize());
            } else {
                subList = entities;
            }
        }

        return new PageImpl<>(subList, pageable, entities.size());
    }

    public static boolean isValidPhoneNumber(String phoneNumber, Model model) {

        if (phoneNumber.length()!= 10){
            model.addAttribute("phoneNumberError", String.format("не верный формат номера %s, введите номер в формате 999 123 45 67", phoneNumber));
            return false;
        }
        if (phoneNumber.startsWith("+")|| phoneNumber.startsWith("7")|| phoneNumber.startsWith("8")){
            model.addAttribute("phoneNumberError", String.format("Номер %s не должен начинаться с +7 или с 8, введите номер в формате 999 123 4567", phoneNumber));
            return false;
        }
        return true;
    }

}
