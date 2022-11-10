package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.domain.*;
import com.example.dnpclientstation.repositories.MessageRepo;
import com.example.dnpclientstation.service.ClientService;
import com.example.dnpclientstation.service.PriceService;
import com.example.dnpclientstation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller

public class MainController {


    @Autowired
    TransactionService transactionService;
    @Autowired
    PriceService priceService;
    @Autowired
    ClientService clientService;

    @Autowired
    private MessageRepo messageRepo;


    @GetMapping("/")
    public String start(Model model){
        return "start";
    }

/*
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model){
        Iterable<Message> messages;
        if(filter != null && !filter.isEmpty()){
            messages  = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User aUser,
            @Valid Message message,
            BindingResult bindingResult,
            Model model){

        message.setAuthor(aUser);

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        }else {
            model.addAttribute("message", null);
            messageRepo.save(message);
        }
        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }
*/


    @GetMapping ("/main-operator")
    public String operatorMain(@AuthenticationPrincipal User creator,
                               @RequestParam(required = false) String search,
                               Model model){


        List<Price> priceList = priceService.getPriceList(creator);

        model.addAttribute("priceList", priceList);


        if (search != null) {
            Client client = clientService.searchClientByCardNameEmailPhone(search);
            model.addAttribute(search);
            if (client != null) {
                model.addAttribute("client", client);
            } else {
                model.addAttribute("clientError", String.format("по запросу %s данные в системе отсутствуют", search));
            }
        }
        model.addAttribute("lastTransactionsList", transactionService.findByCreatorIdLimited(creator.getId(), 3));

        return "/operatorMain";
    }



    @PostMapping("/main-operator")
    public String setUp(@AuthenticationPrincipal User creator,
                        @RequestParam String[] price,
                        @RequestParam String[] fuel,
                        @RequestParam Long[] id,
                        Model model){

        ControllerUtils.checkByComa(price);

        if(priceService.setUpPriceList(id, fuel, price, creator)) {
            model.addAttribute("priceSetUpComplete", "Цены успешно установлены!");
        }else model.addAttribute("priceSetUpError", "Что-то пошло не так! Обратитесь к администратору!");

        return "redirect:/main-operator";
    }

}
