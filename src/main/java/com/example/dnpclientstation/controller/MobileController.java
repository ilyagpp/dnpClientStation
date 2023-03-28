package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.service.ClientService;
import com.example.dnpclientstation.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Controller
public class MobileController {

    @Autowired
    ClientService clientService;

    @Autowired
    MailService mailService;

    @GetMapping("/mobile")
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
        return "mobileMainPage";
    }

    @GetMapping("/mobile/newclient")
    public String clientPage(Model model){

        return "/mobileclientregistr";
    }

    @PostMapping("/mobile/newclient")
    public String regOperationPage(@Valid Client client,
                                   BindingResult result,
                                   @RequestHeader(required = false) String referer,
                                   Model model){
        if (client.getId() == null) {
            boolean isValidClientData = true;
            if (client.getEmail() != null)  {
                if (!clientService.checkEmail(client.getEmail())) {
                    model.addAttribute("emailError", String.format("Email %s уже зарегистрирован, попробуйте другой", client.getEmail()));
                    isValidClientData = false;
                }

            }

            if (client.getPhoneNumber() != null) {

                if (!ControllerUtils.isValidPhoneNumber(client.getPhoneNumber(), model)){
                    isValidClientData = false;
                }

                if (!clientService.checkPhoneNumber(client.getPhoneNumber())) {
                    model.addAttribute("phoneNumberError", String.format("Номер %s уже зарегистрирован, попробуйте другой", client.getPhoneNumber()));
                    isValidClientData = false;
                }
            }


            if  (!StringUtils.isEmpty(client.getPin()) && client.getPin().length() != 4){
                model.addAttribute("pinError","Не валидный пин");
                isValidClientData = false;
            }

            if (result.hasErrors()) {
                Map<String, String> errors = ControllerUtils.getErrors(result);
                model.mergeAttributes(errors);

                model.addAttribute("client", client);

                return "/mobileclientregistr";
            }
            if (!isValidClientData){ return "/mobileclientregistr"; }
        }
        if (clientService.addNewOrEditClient(client, model)) {
            model.addAttribute("register", "Успешная регистрация!");
            return "mobileClient";
        } else model.addAttribute("clientError", "Ошибка! Клиент не создан!");

        return "/mobileclientregistr";

    }


    @GetMapping("/mobile/remind")
    public String remindPin(@RequestParam(required = false)  String phoneNumber,
                            @RequestHeader(required = false) String referer,
                            Model model){


        if (phoneNumber != null && !phoneNumber.equals("")) {

            if (!ControllerUtils.isValidPhoneNumber(phoneNumber, model)){
                return "/mobileRemindPin";
            }


            Client client = clientService.findByPhoneNumber(phoneNumber);
            if (client != null){
                mailService.recallPin(client);
                model.addAttribute("mailSend",  String.format("Пин-код успешно отправлен на email: %s",client.getEmail()));

                return "/mobileClient";
            } else model.addAttribute("phoneNumberError", String.format("Номер: %s не зарегистрирован!",phoneNumber));

        }

        return "/mobileRemindPin";

    }

}
