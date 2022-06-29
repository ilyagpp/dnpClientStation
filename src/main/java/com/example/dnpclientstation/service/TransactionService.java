package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.*;
import com.example.dnpclientstation.repositories.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class TransactionService {

    private static LocalDateTime startDateTime = LocalDateTime.of(2001, 01, 01, 00, 00, 00);

    @Value("${bonusPercent}")
    private Float bonusPercent;

    @Autowired
    private TransactionsRepo transactionsRepo;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PriceService priceService;

    public Optional<FuelTransaction> findFuelTransactionById(Long id) {
        return transactionsRepo.findById(id);
    }

    public List<FuelTransaction> getAll() {
        return transactionsRepo.findAll();
    }

    public List<FuelTransaction> findByCreatorId(User user) {
        return transactionsRepo.findByCreatorId(user.getId());
    }

    public Page<FuelTransaction> findByCreatorIdAndCreateDateTimeBetween(User user, String start, String end, Pageable pageable) {

        LocalDateTime startTime;
        if (StringUtils.isEmpty(start)) {
            startTime = startDateTime;
        } else {
            startTime = LocalDateTime.parse(start);
        }


        LocalDateTime endTime;
        if (StringUtils.isEmpty(end)) {
            endTime = LocalDateTime.now();
        } else {
            endTime = LocalDateTime.parse(end);
        }


        return transactionsRepo.findByCreatorIdAndCreateDateTimeBetween(user.getId(), startTime, endTime, pageable);
    }


    @Transactional(readOnly = false)
    public boolean createOrUpdateTransaction(Long id, String cardNumber, String fuel, Float volume, Float price, User creator) {

        ClientCard clientCard = cardService.findByCardNumber(cardNumber);


        if (clientCard == null || volume == 0.0f) {
            return false;
        }


        FuelTransaction transaction = null;
        if (id != null) {

            transaction = transactionsRepo.getOne(id);

            clientCard.setBonus(clientCard.getBonus() - transaction.getBonus());

            if (cardNumber != null) {
                transaction.setClientCard(cardNumber);
            }
            if (fuel != null) {
                transaction.setFuel(FuelUtil.convert(fuel));
            }

            if (volume != null) {
                transaction.setVolume(volume);
            }

            if (price != null) {
                transaction.setPrice(price);
            }

            if (price != null || volume != null) {

                transaction.setTotal(getTotal(transaction.getVolume(), transaction.getPrice()));
            }

            transaction.setUpdateDateTime(LocalDateTime.now());

            transaction.setBonus(getTotal(transaction.getVolume(), transaction.getPrice()) * getBonusPercent());

        } else {
            transaction = new FuelTransaction();
            transaction.setFuel(FuelUtil.convert(fuel));
            transaction.setClientCard(cardNumber);
            transaction.setUpdateDateTime(LocalDateTime.now());
            transaction.setCreateDateTime(LocalDateTime.now());
            transaction.setPrice(price);
            transaction.setVolume(volume);
            transaction.setTotal(getTotal(volume, price));
            transaction.setBonus(getTotal(volume, price) * getBonusPercent());
            transaction.setCreator(creator);
        }
        clientCard.setBonus(clientCard.getBonus() + transaction.getBonus());
        transactionsRepo.save(transaction);
        cardService.save(clientCard);
        return true;
    }

    public Float getTotal(Float volume, Float price) {
        return (float) ServiceUtil.round(volume * price, 2);
    }

    public Float getVolume(Float total, Float price){
        return (float)  ServiceUtil.round(total/price, 2);
    }

    public Float getBonusPercent() {
        return bonusPercent / 100;
    }


    public Optional<Client> findClientById(Long id){
       return clientService.findById(id);
    }

    public ClientCard getClientCardByCardNumber(String cardNumber) {
        return cardService.findByCardNumber(cardNumber);
    }

    public Client searchClientByCardNameEmailPhone(String search) {

        Client client = clientService.search(search);
        if (client != null) return client;

        client = clientService.findByClientCard(cardService.findByCardNumber(search));
        return client;
    }


    public List<Price> getPriceListByCreatorId(Long creatorId){
        return priceService.findAllByCreatorId(creatorId);
    }

    @Transactional(readOnly = false)
    public int useBonus(String fuel, String clientCard, Float bonus, Float price, User creator) {
        ClientCard card = cardService.findByCardNumber(clientCard);
        if (card.getBonus()>= bonus){
            FuelTransaction useBonusTransaction = new FuelTransaction();
            useBonusTransaction.setCreator(creator);
            useBonusTransaction.setBonus(bonus * -1);
            useBonusTransaction.setClientCard(clientCard);
            useBonusTransaction.setCreateDateTime(LocalDateTime.now());
            useBonusTransaction.setUpdateDateTime(LocalDateTime.now());
            useBonusTransaction.setPrice(price);
            useBonusTransaction.setVolume(getVolume(bonus,price));
            useBonusTransaction.setTotal(bonus);
            useBonusTransaction.setFuel(FuelUtil.convert(fuel));

            card.setBonus(card.getBonus() - bonus);

            transactionsRepo.save(useBonusTransaction);
            cardService.save(card);
            return 1;//all operation completed;
        }
        return -1; //not enough bonuses
    }
}
