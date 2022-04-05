package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.service.TransactionService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String transactions(@RequestParam(required = false) String start,
                               @RequestParam(required = false) String end,
                               @RequestParam(required = false) String clear,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) String clientId,
                               @AuthenticationPrincipal User creator,
                               Model model) {




        List<FuelTransaction> fuelTransactionList = transactionService.findByCreatorIdAndCreateDateTimeBetween(creator, start, end);
        model.addAttribute("transactions", fuelTransactionList);

            if (!StringUtils.isEmpty(start)) {
                model.addAttribute("start", start);
            }
            if (!StringUtils.isEmpty(end)) {
                model.addAttribute("end", end);
            }

        Client client = null;
        if (search != null) {
            client = transactionService.searchClientbyCardNameEmailPhone(search);
            if (client != null) {
                model.addAttribute("client", client);
            } else {
                model.addAttribute("clientError", String.format("по запросу %s данные в системе отсутствуют", search));
            }
        }

        if (clientId != null){
            client = transactionService.findClientById(Long.valueOf(clientId)).orElse(null);
            if (client != null){
                model.addAttribute("client",client);
            }
        }

        return "/transactions";
    }

    @PostMapping("/transactions")
    public String createOfUpdate(Model model,
                                 @AuthenticationPrincipal User creator,
                                 @RequestParam(required = false) Long id,
                                 @RequestParam @Length(max = 13, message = "Номер кары введен неверно, длинна не соответствует") String clientCard,
                                 @RequestParam @NotBlank String fuel,
                                 @RequestParam @NotNull(message = "Цена не может быть пустой") @Max(value = 999, message = "Максимальный объем одной транзакции 999 литров") Float volume,
                                 @RequestParam @Min(0) @NotNull(message = "Поле не может быть пустым!") Float price
                                 //BindingResult bindingResult
    ) {


        transactionService.createOrUpdateTransaction(id, clientCard, fuel, volume, price, creator);


        return "redirect:/transactions";
    }

}
