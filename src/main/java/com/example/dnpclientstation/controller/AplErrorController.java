package com.example.dnpclientstation.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller()

public class AplErrorController implements ErrorController {
    @RequestMapping("/error")
    public String getErrorPage(Model model){
        model.addAttribute("error", "Упс... Кажется такой страницы нет!");
        return "error";

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
