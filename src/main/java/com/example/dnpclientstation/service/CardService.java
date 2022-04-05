package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.ClientCard;
import com.example.dnpclientstation.repositories.CardRepo;
import com.example.dnpclientstation.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepo cardRepo;

    @Autowired
    private UserRepo userRepo;


    public boolean save(ClientCard clientCard){
        if (clientCard != null) {
            cardRepo.save(clientCard);
            return true;
        }
        return false;
    }


    public boolean addNewCard(String cardNumber){

        if (cardRepo.findByCardNumber(cardNumber) != null){
            return false;
        }

        cardRepo.save(new ClientCard(cardNumber, (float) 0, null));
        return true;
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
}
