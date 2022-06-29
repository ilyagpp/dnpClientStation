package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {



    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public String getAllClients(Model model){
        List<Client> list = clientService.findAll();
        model.addAttribute("clients", list);

        return "/clients";
    }

    @GetMapping("/client/new")
    public String get(Model model){

        return "/client";
    }

    @PostMapping("/client/new")
    public String addNewClient(@Valid Client client,
                               BindingResult result,
                               Model model){

        if (client.getEmail()!=null && !clientService.checkEmail(client.getEmail())){
            model.addAttribute("emailError",  String.format("Email %s уже зарегистрирован, попробуйте другой",client.getEmail()));
        }

        if (client.getPhoneNumber() !=null && !clientService.checkPhoneNumber(client.getPhoneNumber())){
            model.addAttribute("phoneNumberError", String.format("Номер %s уже зарегистрирован, попробуйте другой",client.getPhoneNumber()));
        }

        if (result.hasErrors()){
            Map<String,String> errors = ControllerUtils.getErrors(result);
            model.mergeAttributes(errors);

            model.addAttribute("client", client);

            return "/client";
        }
        if (addNewClient(client, model)){
           return "clientOK";
        } else model.addAttribute("clientError", "Ошибка! Клиент не создан!");

        return "/client";

    }

    @GetMapping("/client/phone")
    public String getClientByPhoneNumber(@RequestParam(required = false) String phoneNumber,
                            Model model){
        if (phoneNumber != null){
            Client client = clientService.findByPhoneNumber(phoneNumber);

            if (client != null) {
                model.addAttribute("client", client);
                model.addAttribute("message", String.format("Клиент с номером телефона %s успешно найден", phoneNumber));

                return "/clientprofile";
            } else {
                model.addAttribute("message", String.format("Клиент с номером телефона %s не найден в системе!", phoneNumber));
                model.addAttribute("phoneNumberError","Номер не найден!");
            }
        }

        return "/clientprofile";
    }


    @GetMapping("/client/email")
    public String getClientByEmail(@RequestParam(required = false) String email,
                                   Model model){
        if (email != null){

            Client client = clientService.findByEmail(email);

            if (client != null) {
                model.addAttribute("client", client);
                model.addAttribute("message", String.format("Клиент с email: %s успешно найден", email));

                return "/clientprofile";
            } else {
                model.addAttribute("message", String.format("Клиент с email: %s не найден в системе!", email));
                model.addAttribute("emailError","email не найден!");
            }

        }

        return "/clientprofile";

    }

    @GetMapping("/clients/free")
    public String getClientsList(Model model){

        List<Client> list = clientService.findAllByClientCardIsNullOrderById();
        model.addAttribute("clients", list);
        return "/main/resources/templates/parts/freeClients.ftl";
    }


    private boolean addNewClient(Client client, Model model) {
        if (clientService.findByEmail(client.getEmail()) != null){
            model.addAttribute("emailError", "Пользователь с таким адресом уже существует!");
            model.addAttribute("client", client);
            return false;
        }
        if (clientService.findByPhoneNumber(client.getPhoneNumber()) != null){
            model.addAttribute("phoneNumberError", "Этот номер уже зарегистрирован в системе!");
            model.addAttribute("client", client);
            return false;
        }

        client.setAdded(LocalDateTime.now());

        clientService.save(client);
        return true;

    }






}
