package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.domain.dto.TerminalTransaction;
import com.example.dnpclientstation.service.CardService;
import com.example.dnpclientstation.service.ClientService;
import com.example.dnpclientstation.service.TransactionService;
import com.example.dnpclientstation.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/terminal")
public class TerminalController {

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    @Autowired
    TransactionService transactionService;


    @GetMapping("/{id}}")
    public ResponseEntity<Integer> onLine(@PathVariable Long id){

        Optional<User> user = userService.findById(id);

        return user.isPresent()? new ResponseEntity<>(0, HttpStatus.OK):
                new ResponseEntity<>(-1, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/client")
    public ResponseEntity<Client> getClient(@RequestParam(required = false) String input) {

        Client client = clientService.searchClientByCardNameEmailPhone(input);


        return client != null ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{term_id}/accumulate")
    public ResponseEntity<FuelTransaction> accumulate(@PathVariable Long term_id,
                                                      @RequestParam(required = false) Long id,
                                                      @RequestParam @NotBlank String cardNumber,
                                                      @RequestParam @NotBlank String fuel,
                                                      @RequestParam @NotNull Float price,
                                                      @RequestParam @NotNull Float volume) {

        Optional<User> creator = userService.findById(term_id);
        if (creator.isPresent()) {
            FuelTransaction fuelTransaction =
                    transactionService.createOrUpdateTransaction(id, cardNumber, fuel, volume, price, creator.get());
            return fuelTransaction != null ? new ResponseEntity<>(fuelTransaction, HttpStatus.CREATED)
                    : new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @PostMapping("/{term_id}/use")
    public ResponseEntity<TerminalTransaction> useBonus(@PathVariable Long term_id,
                                                        @RequestParam(required = false) Long id,
                                                        @RequestParam Float bonus,
                                                        @RequestParam String fuel,
                                                        @RequestParam String clientCard,
                                                        @RequestParam Float price,
                                                        @RequestParam @Length(max = 4, min = 4) String pin) {


        Optional<User> creator = userService.findById(term_id);

        if (creator.isPresent()) {

            boolean checkPin = transactionService.checkPin(clientCard, pin);
            if (!checkPin) {
                return new ResponseEntity<>(new TerminalTransaction(new FuelTransaction(), false), HttpStatus.BAD_REQUEST);
            }

            FuelTransaction transaction = transactionService.useBonus(id, fuel, clientCard, bonus, price, creator.get());

            if (transaction != null) {
                return new ResponseEntity<>(new TerminalTransaction(transaction, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new TerminalTransaction(new FuelTransaction(), true), HttpStatus.BAD_REQUEST);
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}





