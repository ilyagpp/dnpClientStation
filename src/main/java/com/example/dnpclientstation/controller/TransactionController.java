package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.domain.Price;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.service.TransactionService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Controller
public class TransactionController {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String transactions(@RequestParam(required = false) String start,
                               @RequestParam(required = false) String end,
                               @RequestParam(required = false) String search,
                               @RequestParam(required = false) String clientId,
                               @AuthenticationPrincipal User creator,
                               Model model,
                               @RequestHeader(required = false) String referer,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {




        String url = "/transactions";
                if (referer.contains("transactions")){
            if (!StringUtils.isEmpty(start)) {
                model.addAttribute("start", start);
                if (!url.contains(start)) {
                    url = url + "?start=" + start;
                }
            }
            if (!StringUtils.isEmpty(end)) {
                model.addAttribute("end", end);
                if (!url.contains(end)) {
                    if (url.contains("?")) {
                        url = url + "&end=" + end;
                    }else url = url + "?end=" + end;
                }
            }

        }
        model.addAttribute("url", url);
        Page<FuelTransaction> fuelTransactionPage = transactionService.findByCreatorIdAndCreateDateTimeBetween(creator, start, end, pageable);
        model.addAttribute("page", fuelTransactionPage);



        Client client;
        if (search != null) {
            client = transactionService.searchClientByCardNameEmailPhone(search);
            if (client != null) {
                model.addAttribute("client", client);
            } else {
                model.addAttribute("clientError", String.format("по запросу %s данные в системе отсутствуют", search));
            }
        }

        if (clientId != null) {
            client = transactionService.findClientById(Long.valueOf(clientId)).orElse(null);
            if (client != null) {
                model.addAttribute("client", client);
            }
        }

        List<Price> priceList = transactionService.getPriceListByCreatorId(creator.getId());
        if (priceList != null) {
            model.addAttribute("priceList", priceList);
        }
        return "transactions";
    }


    @PostMapping("/transaction/accumulate")
    public String accumulateBonus(Model model,
                                  @AuthenticationPrincipal User creator,
                                  @RequestParam(required = false) Long id,
                                  @RequestParam @Length(max = 13, message = "Номер кары введен неверно, длинна не соответствует") String clientCard,
                                  @RequestParam @NotBlank String fuel,
                                  @RequestParam String exampleRadios,
                                  @RequestParam(required = false) String input

    ) {

        if (input != null && !input.equals("")) {

            input = ControllerUtils.checkByСomma(input);
            String[] fuelPlusPrice = fuel.split(" -> ");
            String fuelA = ControllerUtils.checkByСomma(fuelPlusPrice[0]);
            float price = Float.parseFloat(ControllerUtils.checkByСomma(fuelPlusPrice[1]));


            if (price == 0) {
                model.addAttribute("priceError", "Цена не может быть нулевой");
                return "redirect:/transactions";
            }

            float total;
            float volume = 0.0f;
            switch (exampleRadios) {

                case "t":
                    total = Float.parseFloat(input);
                    volume = total / price;
                    break;

                case "v":
                    volume = Float.parseFloat(input);
            }

            transactionService.createOrUpdateTransaction(id, clientCard, fuelA, volume, price, creator);

        } else model.addAttribute("volumeError", "Вы забыли ввести объем");
        return "redirect:/transactions";
    }

    @PostMapping("/transaction/use")
    public String useBonus(@AuthenticationPrincipal User creator,
                           Model model,
                           @RequestParam @NotBlank String fuel,
                           @RequestParam String clientCard,
                           @RequestParam String bonus) {


        if (bonus != null && !bonus.equals("")) {
            bonus = ControllerUtils.checkByСomma(bonus);
            String[] input = fuel.split(" -> ");
            String fuelA = ControllerUtils.checkByСomma(input[0]);
            Float price = Float.valueOf(ControllerUtils.checkByСomma(input[1]));

            int result = transactionService.useBonus(fuelA, clientCard, Float.valueOf(bonus), price, creator);

            if (result == 1) {
                model.addAttribute("transactionComplete", "Успешно!");
            }
            model.addAttribute("transactionError", "На карте недостаточно бонусов для списания!");

        }

        return "redirect:/transactions";

    }
}
