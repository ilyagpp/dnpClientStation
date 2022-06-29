package com.example.dnpclientstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/er")
    public String getErrorPage(Model model){


        return "er";

    }

}
