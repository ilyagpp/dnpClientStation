package com.example.dnpclientstation.controller;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.ClientCard;
import com.example.dnpclientstation.service.CardService;
import com.example.dnpclientstation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/cards")
    public String getCards(@RequestParam(required = false) Integer size,
                           @RequestParam(required = false) String free,
                           Model model){

        if (free == null) {
            List<ClientCard> cardsList = cardService.getAll()
                    .stream().sorted(Comparator.comparing(ClientCard::getId))
                    .collect(Collectors.toList());

            model.addAttribute("cardsList", cardsList);
            model.addAttribute("nameList", "Список карт");

        }else {
            List<ClientCard> freeCards = cardService.getFree(size)
                    .stream().sorted(Comparator.comparing(ClientCard::getId))
                    .collect(Collectors.toList());
            model.addAttribute("cardsList", freeCards);
            model.addAttribute("nameList", "Список свободных карт");

        }
        return "cards";
    }


    @PostMapping("/cards")
    public String addNewCard(@RequestParam String cardNumber,
                             Model model){


        if (cardNumber.isEmpty()) {
            model.addAttribute("error", "не верно введен номер карты");
        }

        cardService.addNewCard(cardNumber);


        return "redirect:/cards";
    }

    @PostMapping("/cards/auto")
    public String autoNewCard(Model model){

        cardService.automaitcCreateNewCard();

        return "redirect:/cards";
    }

    @PostMapping("/card/{card}")
    public String issueCard(@PathVariable ClientCard card,
                                    @RequestParam String clientId,
                                    Model model){

        if (card != null && clientId!= null){
            Client client = clientService.findById(Long.valueOf(clientId)).orElse(null);
            if (client != null){
                client.setClientCard(card);
                card.setClient(client);
                cardService.save(card);
                clientService.save(client);
                model.addAttribute("cardMessage", "Успешно!");
                model.addAttribute("card", card);
                model.addAttribute("client", client);
            }else model.addAttribute("error", "Выдача невозможна, произошла ошибка, попробуйте повторить процедуру заново!");
        }



       return "/resultIssue";

    }



    @GetMapping("/card/{card}")
    public String getCardForIssue(
                                  @PathVariable(required = false) ClientCard card,
                                  @RequestParam(required = false) String search,
                                  @RequestParam(required = false) String allFree,
                                  @RequestParam(required = false) Client client,
                                  Model model){

         if (card.getCardNumber()!=null) {
            model.addAttribute("card", card);
        } else {
            model.addAttribute("cardError", String.format("Карта с id номером %s не найдена в системе!", card.getId()));
        }
            List<Client> freeClientList = clientService.findAllByClientCardIsNullOrderById();
         model.addAttribute("clients", freeClientList);
        if (search != null){
           client = clientService.search(search);
            if (client == null){
                model.addAttribute("error", String.format("по запросу %s данные в системе отсутствуют, " +
                        "\nвозможно вы сможете найти данные в списке клиентов без карты.",search));
            } else {
                model.addAttribute("client", client);
                if (client.getClientCard()== null){
                    model.addAttribute("btnActivate", "true");
                } else model.addAttribute("danger", String.format("Данному клиенту уже выдана карта %s", client.getClientCard().getCardNumber()));
            }
        }


        return "cardIssue";
    }

    @GetMapping("/card/fr/{card}")
    public String withdrawCard(@PathVariable ClientCard card,
                                Model model){

        if (card != null && card.getClient() != null){

            Client client = clientService.findById(card.getClient().getId()).get();
            card.setClient(null);
            client.setClientCard(null);
            cardService.save(card);
            clientService.save(client);
            model.addAttribute("message", String.format("Карта %s, изъята у клиента %s %s %s",
                                                                    card.getCardNumber(),
                                                                    client.getSurname(),
                                                                    client.getName(),
                                                                    client.getPhoneNumber()));
        } else model.addAttribute("error", "Что-то пошло не так, попробуйте повторить операцию позже или сообщите администратору!");
        return "confirmCard";
    }



}
