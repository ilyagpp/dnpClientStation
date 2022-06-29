package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.service.CardService;
import com.example.dnpclientstation.service.ClientService;
import com.example.dnpclientstation.service.TransactionService;
import com.example.dnpclientstation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;


}
