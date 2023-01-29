package com.example.dnpclientstation.controller;


import com.example.dnpclientstation.domain.*;
import com.example.dnpclientstation.service.TransactionService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.constraints.NotBlank;
import java.lang.Long;
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
                               @RequestParam(required = false) String showAll,
                               @RequestParam(required = false) Integer size,
                               @RequestParam(required = false) Integer payType,
                               @AuthenticationPrincipal User creator,
                               Model model,
                               @RequestHeader(required = false) String referer,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {



        String url = "/transactions";

        if (showAll != null  || (referer!= null && referer.contains("showAll=true"))) {
            model.addAttribute("showAll", showAll);

            url = url + "?showAll=true";

        }


        if (referer != null && referer.contains("transactions")) {
            if (!StringUtils.isEmpty(start)) {
                model.addAttribute("start", start);
                if (!url.contains(start) && !url.contains("?")) {
                    url = url + "?start=" + start;
                } else {
                    url = url + "&start=" + start;
                }
            }
            if (!StringUtils.isEmpty(end)) {
                model.addAttribute("end", end);
                if (!url.contains(end)) {
                    if (url.contains("?")) {
                        url = url + "&end=" + end;
                    } else url = url + "?end=" + end;
                }
            }

        }
        if (payType != null){
            if (url.contains("?")) {
                url = url + "&payType=" + payType;
            } else {
                url = url + "?payType=" + payType;
            }
        }

        ControllerUtils.initPageSize(model, size, pageable);
        model.addAttribute("url", url);
        Page<FuelTransaction> fuelTransactionPage = transactionService.findByCreatorIdAndCreateDateTimeBetween(creator.getId(), start, end, pageable, Boolean.parseBoolean(showAll), payType);
        model.addAttribute("page", fuelTransactionPage);
        model.addAttribute("payType",  payType == null? 100 : payType);
        model.addAttribute("azs", creator.getAzsName()== null? creator.getUsername() : creator.getAzsName());


        return "transactions";

    }


    @PostMapping("/transaction/accumulate")
    public String accumulateBonus(Model model,
                                  @AuthenticationPrincipal User creator,
                                  @RequestParam(required = false) Long id,
                                  @RequestParam @Length(max = 13, message = "Номер кары введен неверно, длинна не соответствует") String clientCard,
                                  @RequestParam @NotBlank String fuel,
                                  @RequestParam boolean nal,
                                  @RequestParam String exampleRadios,
                                  @RequestParam(required = false) String input

    ) {

        if (input != null && !input.equals("")) {

            input = ControllerUtils.checkByСomma(input);
            String[] fuelPlusPrice = fuel.split(" -> ");
            String fuelA = ControllerUtils.checkByСomma(fuelPlusPrice[0]);
            float price = Float.parseFloat(ControllerUtils.checkByСomma(fuelPlusPrice[1]));


            if (price == 0) {
                model.addAttribute("error", "Цена не может быть нулевой");
                return "er";
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

            transactionService.createOrUpdateTransaction(id, clientCard, fuelA, volume, price, nal, creator);

        } else model.addAttribute("error", "Вы забыли ввести объем");
        return "redirect:/main-operator";
    }

    @PostMapping("/transaction/use")
    public String useBonus(@AuthenticationPrincipal User creator,
                           Model model,
                           @RequestParam (required = false) Long id,
                           @RequestParam @NotBlank String fuel,
                           @RequestParam String clientCard,
                           @RequestParam (required = false) String pin,
                           @RequestParam String bonus,
                           @RequestParam Boolean nal,
                           @RequestHeader(required = false) String referer) {

        boolean checkPin;

        if (id != null){
            checkPin = true;
        } else {
            checkPin = transactionService.checkPin(clientCard,pin);
        }

        if (checkPin) {

            if (bonus != null && !bonus.equals("")) {
                bonus = ControllerUtils.checkByСomma(bonus);
                String[] input = fuel.split(" -> ");
                String fuelA = ControllerUtils.checkByСomma(input[0]);
                Float price = Float.valueOf(ControllerUtils.checkByСomma(input[1]));

                FuelTransaction transaction = transactionService.useBonus(id, fuelA, clientCard, Float.valueOf(bonus), price, nal, creator);

                if (transaction != null) {
                    model.addAttribute("transactionComplete", "Успешно!");
                } else {
                    model.addAttribute("error", "На карте недостаточно бонусов для списания!");
                    return "er";
                }
            }

            return "redirect:/main-operator";
        } else {
            model.addAttribute("error", "Неверный пин!");
            model.addAttribute("referer",referer);
            return "er";
        }

    }

    @GetMapping("transactions/edit/{id}")
    public String editTransaction(@AuthenticationPrincipal User creator,
                                  @PathVariable String id,
                                  Model model,
                                  RedirectAttributes redirectAttributes,
                                  @RequestHeader(required = false) String referer) {

        FuelTransaction fuelTransaction = transactionService.findFuelTransactionById(Long.valueOf(id)).orElse(null);
        model.addAttribute("editTransaction", fuelTransaction);
        assert fuelTransaction != null;
        ClientCard card = transactionService.getClientCardByCardNumber(fuelTransaction.getCardNumber());
        model.addAttribute("bonus", (fuelTransaction.getBonus() - card.getBonus())* -1);

        List<Price> priceList = transactionService.getPriceListByCreatorId(creator.getId());
        if (priceList != null) {
            model.addAttribute("priceList", priceList);
        }
        return "editTransactform";
    }



    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("transactions/delete/{id}")
    public String deleteTransaction(@AuthenticationPrincipal User user,
                                    @PathVariable Long id,
                                    Model model,
                                    @RequestHeader(required = false) String referer
    ){

          int result =  transactionService.deleteById(id);
          if (result == -1){
              model.addAttribute("error", "Удаление невозможно! Попробуйте снова, или обратитесь к Администратору системы.");

              return "er";
          } else
              return "redirect:"+ referer;

    }
}