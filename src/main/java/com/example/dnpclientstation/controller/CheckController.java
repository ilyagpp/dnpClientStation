package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Controller
public class CheckController {

    @Autowired
    ClientService clientService;


    @GetMapping("/check")
    public String checkBonusByPhoneNumber(@RequestParam (required = false) @NotBlank String phoneNumber,
                                            Model model){
        if (phoneNumber != null && !phoneNumber.equals("")) {

            Client client = clientService.findByPhoneNumber(phoneNumber);
            if (client != null) {
                model.addAttribute("bonus", client.getClientCard().getBonus());
                model.addAttribute("phoneNumber", phoneNumber);
            } else
                model.addAttribute("clientError", String.format("Номер %s не зарегистрирован в системе.", phoneNumber));
        }
        return "/checkBonusByPhoneNumber";
    }


}
