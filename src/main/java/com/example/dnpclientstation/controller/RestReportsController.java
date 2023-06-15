package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.AZS;
import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.service.AZSService;
import com.example.dnpclientstation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "rest/", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestReportsController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AZSService azsService;

    @GetMapping("transact/")
    public List<FuelTransaction> get() {

        return transactionService.findAll();
    }

    @GetMapping("azs/")
    public List<AZS> getAllAzs() {
        return azsService.getAll();
    }

    @GetMapping("transact/settings")
    public List<FuelTransaction> getWithSettings(@RequestParam(required = false) Long[] azs,
                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime  end,
                                                 @RequestParam(required = false) Integer operation,
                                                 @RequestParam(required = false) Integer payType) {

        List<FuelTransaction> resultlist = transactionService.findBySettings(azs, operation, payType, start, end);

        return resultlist.isEmpty() ? new ArrayList<>() : resultlist;
    }




}
