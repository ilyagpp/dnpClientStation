package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "mobile/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestMobileController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transact/{phoneNumber}")
    public List<FuelTransaction> getByPhoneNumber(@PathVariable String phoneNumber){
        return transactionService.findByClient(phoneNumber);
    }
}
