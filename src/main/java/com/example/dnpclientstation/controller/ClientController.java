package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.service.ClientService;
import com.example.dnpclientstation.service.MailService;
import com.example.dnpclientstation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class ClientController {


    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    MailService mailService;




    @GetMapping("/clients")
    public String getAllClients(Model model,
                                @RequestParam (required = false) String search,
                                @RequestParam (required = false) Integer size,
                                @RequestHeader(required = false) String referer,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {

        model.addAttribute("size", size);

        if (search != null){

            List<Client> searchClients = clientService.searchAll(search);
            Page<Client> clients = new PageImpl<Client>(searchClients, pageable, searchClients.size());
            model.addAttribute("page", clients);
            model.addAttribute("search", search);
            model.addAttribute("referer",referer.split("\\?")[0]);
        }else {

            Page<Client> list = clientService.findAll(pageable);
            model.addAttribute("page", list);
        }
            ControllerUtils.initPageSize(model, size, pageable);
            model.addAttribute("url", "/clients");


        return "/clients";
    }

    @GetMapping("/client/new")
    public String get() {

        return "/client";
    }

    @PostMapping("/client/new")
    public String addNewOrEditClient(@Valid Client client,
                                     BindingResult result,
                                     Model model) {
        if (client.getId() == null) {

            if (client.getEmail() != null && !clientService.checkEmail(client.getEmail())) {
                model.addAttribute("emailError", String.format("Email %s уже зарегистрирован, попробуйте другой", client.getEmail()));
            }

            if (client.getPhoneNumber() != null && !clientService.checkPhoneNumber(client.getPhoneNumber())) {
                model.addAttribute("phoneNumberError", String.format("Номер %s уже зарегистрирован, попробуйте другой", client.getPhoneNumber()));
            }


            if  (!StringUtils.isEmpty(client.getPin()) && client.getPin().length() != 4){
                model.addAttribute("pinError","Не валидный пин");
            }

            if (result.hasErrors()) {
                Map<String, String> errors = ControllerUtils.getErrors(result);
                model.mergeAttributes(errors);

                model.addAttribute("client", client);

                return "/client";
            }
        }
        if (clientService.addNewOrEditClient(client, model)) {
            return "clientOK";
        } else model.addAttribute("clientError", "Ошибка! Клиент не создан!");

        return "/client";

    }



    @PostMapping("client/edit/{id}")
    public String updateClient(@Valid Client client,
                               BindingResult bindingResult,
                               @RequestHeader(required = false) String referer,
                               Model model, @PathVariable Long id) {
        return addNewOrEditClient(client, bindingResult, model);

    }

    @GetMapping("client/edit/{id}")
    public String getClientById(@PathVariable Long id,
                                Model model) {

        Client client = clientService.findById(id).orElse(null);

        if (client != null) {
            model.addAttribute("client", client);
            model.addAttribute("edit", "true");
            return "client";
        }

        model.addAttribute("clientError", "Клиент не существует или id is null");
        return "redirect:/clients";

    }

    @PostMapping("/client/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {

        boolean result = clientService.deleteById(id);

        if (!result) {
            model.addAttribute("error", "К данному клиенту привязана карта, сначала карту необходимо отвязать!");
            return "er";
        } else {
            model.addAttribute("delete", "Удаление, успешно!");
            return "clientOK";
        }
    }


    @GetMapping("/client/phone")
    public String getClientByPhoneNumber(@RequestParam(required = false) String phoneNumber,
                                         Model model) {
        if (phoneNumber != null) {
            Client client = clientService.findByPhoneNumber(phoneNumber);

            if (client != null) {
                model.addAttribute("client", client);
                model.addAttribute("message", String.format("Клиент с номером телефона %s успешно найден", phoneNumber));

                return "/clientprofile";
            } else {
                model.addAttribute("message", String.format("Клиент с номером телефона %s не найден в системе!", phoneNumber));
                model.addAttribute("phoneNumberError", "Номер не найден!");
            }
        }

        return "/clientprofile";
    }


    @GetMapping("/client/email")
    public String getClientByEmail(@RequestParam(required = false) String email,
                                   Model model) {
        if (email != null) {

            Client client = clientService.findByEmail(email);

            if (client != null) {
                model.addAttribute("client", client);
                model.addAttribute("message", String.format("Клиент с email: %s успешно найден", email));
                return "/clientprofile";
            } else {
                model.addAttribute("message", String.format("Клиент с email: %s не найден в системе!", email));
                model.addAttribute("emailError", "email не найден!");
            }

        }

        return "/clientprofile";

    }

    @GetMapping("/clients/free")
    public String getClientsList(Model model) {

        List<Client> list = clientService.findAllByClientCardIsNullOrderById();
        model.addAttribute("clients", list);
        return "/main/resources/templates/parts/freeClients.ftl";
    }


    @GetMapping("/clients/pin")
    public String getPin(@RequestParam(required = false) String search,
                         @RequestParam(required = false) Boolean showPin,
                         @RequestParam(defaultValue = "false") Boolean recallPin,
                         Model model){


        if (search != null) {
            model.addAttribute("search", search);
            Client client = clientService.searchClientByCardNameEmailPhone(search);
            if (client != null && recallPin){

                mailService.recallPin(client);
                model.addAttribute("mailSend", String.format("Пин-код успешно отправлен на email: %s",client.getEmail()));
                return "clientOk";

            }

            if (client != null) {
                model.addAttribute("client", client);
                if (showPin != null) {
                    model.addAttribute("pin", client.getPin());
                    model.addAttribute("showPin", "true");
                }
            } else {
                model.addAttribute("clientError", String.format("по запросу %s ничего не найдено",search));
            }

        }
        return "pin";
    }
    @PostMapping("client/pin/{id}")
    public String changePin (@PathVariable Long id,
                             String pin,
                             @RequestHeader(required = false) String referer,
                             Model model){

        if (pin != null && pin.length()==4){
           boolean result =  clientService.changePin(pin , id);

            if (result){
                model.addAttribute("clientOk", "Новый пин успешно установлен");
                return "clientOk";
            }
        }
        model.addAttribute("error", "При смене пин кода произошла ошибка!");
        model.addAttribute("referer", referer);
        return "er";


    }




}
