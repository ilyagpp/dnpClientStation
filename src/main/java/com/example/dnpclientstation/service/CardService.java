package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.ClientCard;
import com.example.dnpclientstation.repositories.CardRepo;
import com.example.dnpclientstation.repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {

    static final Logger log =
            LoggerFactory.getLogger(CardService.class);

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private UserRepo userRepo;


    public boolean save(ClientCard clientCard){
        if (clientCard != null) {
            cardRepo.save(clientCard);

            log.info("Обновляем данные карты: "+ clientCard.toString());
            return true;
        }
        return false;
    }


    public void addNewCard(String cardNumber){

        if (cardRepo.findByCardNumber(cardNumber) != null){
            return;
        }

        ClientCard clientCard = cardRepo.save(new ClientCard(cardNumber, (float) 0, null));
        log.info("Добавлена новая карта: "+ clientCard);
    }

    public String  getLastCardNumber() {

        return cardRepo.findAll().stream()
                .sorted((o1, o2) -> o2.getCardNumber().compareTo(o1.getCardNumber()))
                .collect(Collectors.toList())
                .get(0).getCardNumber();
    }

    public List<ClientCard> getAll(){
        return cardRepo.findAll();
    }

    public List<ClientCard> getFree(Integer size){

       return size !=null? cardRepo.findByClientIsNull()
               .stream().sorted(Comparator.comparing(ClientCard::getId))
               .limit(size).collect(Collectors.toList())
               : cardRepo.findByClientIsNull();

    }

    public Optional<ClientCard> getById(Long id){
        return cardRepo.findById(id);
    }


    public ClientCard findByCardNumber(String cardNumber) {
        return cardRepo.findByCardNumber(cardNumber);
    }

    public void automaitcCreateNewCard(){

            Long number = Long.parseLong(getLastCardNumber()) +1;
            String newNumber = String.valueOf(number);
            addNewCard(newNumber);

    }

}
