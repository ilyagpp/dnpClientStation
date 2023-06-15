package com.example.dnpclientstation.service;

import com.example.dnpclientstation.controller.ControllerUtils;
import com.example.dnpclientstation.domain.*;
import com.example.dnpclientstation.repositories.TransactionsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TransactionService {

    private static LocalDateTime startDateTime = LocalDateTime.of(2001, 01, 01, 00, 00, 00);

    static final Logger log =
            LoggerFactory.getLogger(TransactionService.class);
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

    @Autowired
    private UserService userService;

    @Autowired
    private AZSService azsService;

    public Optional<FuelTransaction> findFuelTransactionById(Long id) {
        return transactionsRepo.findById(id);
    }


    public List<FuelTransaction> findByCreatorId(Long id) {
        return transactionsRepo.findByCreatorId(id);
    }
    public List<FuelTransaction> findAll(){
        return transactionsRepo.findAll();
    }

    public List<FuelTransaction> findByCreatorIdLimited(Long id, int limit) {
        Page<FuelTransaction> page = transactionsRepo.findByCreatorId(id,
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "id")));
        List<FuelTransaction> result = page.getContent();
        return result;
    }

    public Page<FuelTransaction> findByCreatorIdAndCreateDateTimeBetween(Long id, String start, String end, Pageable pageable, boolean showAll, Integer payType, Integer operationType) {

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

        boolean payTypeNotAll = ((payType != null) && (payType != 100));
        boolean operationNotAll = ((operationType != null) && (operationType != 100));

        boolean isNalType = getType(payType);

        boolean isAccumType = getType(operationType);


        List<FuelTransaction> transactionList = new ArrayList<>();
        if (showAll) {
            transactionList = transactionsRepo.findByCreateDateTimeBetween(startTime, endTime);
        } else {
            transactionList = transactionsRepo.findByCreatorIdAndCreateDateTimeBetween(id, startTime, endTime);
        }


        if (payTypeNotAll) {

            List<FuelTransaction> payTypeSortList = transactionList.stream()
                    .filter(fuelTransaction -> {
                        return fuelTransaction.isNal() == isNalType;
                    })
                    .collect(Collectors.toList());
            transactionList = payTypeSortList;
        }
        if (operationNotAll) {

            List<FuelTransaction> opeationNotAllList = transactionList.stream()
                    .filter(fuelTransaction -> {
                        return fuelTransaction.isAccumulate() == isAccumType;
                    })
                    .collect(Collectors.toList());
            transactionList = opeationNotAllList;
        }


        transactionList.sort(comparator.reversed());

        Page<FuelTransaction> page = (Page<FuelTransaction>) ControllerUtils.listToPage(pageable, transactionList);
        return page;

    }

    Comparator<FuelTransaction> comparator = new Comparator<FuelTransaction>() {
        @Override
        public int compare(FuelTransaction o1, FuelTransaction o2) {
            if (o1.getId() == o2.getId()) {
                return 0;
            }
            return o1.getId() > o2.getId() ? 1 : -1;
        }
    };

    private Boolean getType(Integer action) {
        if (action == null) return false;
        return action == 0;
    }


    @Transactional(readOnly = false)
    public FuelTransaction createOrUpdateTransaction(Long id, String cardNumber, String fuel, Float volume, Float price, Boolean nal, User creator, Boolean accumulate) {

        String operation = "'накопление бонусов'";

        if (nal) {
            log.info(String.format("Запрос на обработку транзакции %s: id = %d, номер карты = %s, %s, объем = %f, цена = %f, наличный, %s", operation, id, cardNumber, fuel, volume, price, creator.toString()));
        } else
            log.info(String.format("Запрос на обработку транзакции %s: id = %d, номер карты = %s, %s, объем = %f, цена = %f, безналичный, %s", operation, id, cardNumber, fuel, volume, price, creator.toString()));


        ClientCard clientCard = cardService.findByCardNumber(cardNumber);


        if (clientCard == null || volume < 0.0f || creator == null) {

            String errorReason = "";
            if (clientCard == null) {
                errorReason = "clientCard = null";
            }
            if (volume < 0) {
                errorReason = "volume < 0";
            }
            if (creator == null) {
                errorReason = "creator = null";
            }
            log.warn("транзакция не обработана: " + errorReason);
            return null;
        }


        FuelTransaction transaction = null;
        if (id != null) {

            transaction = transactionsRepo.getOne(id);

            log.info("Транзакция найдена: " + transaction.toString());

            clientCard.setBonus(clientCard.getBonus() - transaction.getBonus());

            if (cardNumber != null) {
                transaction.setCardNumber(cardNumber);
            }
            if (fuel != null) {

                try {
                    transaction.setFuel(FuelUtil.convert(fuel));
                } catch (NumberFormatException e) {
                    log.error("Неизвестный или неверный вид топлива: " + fuel);
                    return null;
                }

            }
            transaction.setAccumulate(accumulate);
            transaction.setVolume(volume);

            if (price != null) {
                transaction.setPrice(price);
            }

            transaction.setTotal(getTotal(transaction.getVolume(), transaction.getPrice()));

            transaction.setUpdateDateTime(LocalDateTime.now());

            transaction.setBonus(getTotal(transaction.getVolume(), transaction.getPrice()) * getBonusPercent(creator));

            transaction.setCreator(creator);

            transaction.setNal(nal);

        } else {
            transaction = new FuelTransaction();

            try {
                transaction.setFuel(FuelUtil.convert(fuel));
            } catch (NumberFormatException e) {
                log.error("Неизвестный или неверный вид топлива: " + fuel);
                return null;
            }

            transaction.setCardNumber(cardNumber);
            transaction.setUpdateDateTime(LocalDateTime.now());
            transaction.setCreateDateTime(LocalDateTime.now());
            transaction.setPrice(price);
            transaction.setVolume(volume);
            transaction.setTotal(getTotal(volume, price));
            transaction.setBonus(getTotal(volume, price) * getBonusPercent(creator));
            transaction.setCreator(creator);
            transaction.setNal(nal);
            transaction.setAccumulate(accumulate);
        }
        clientCard.setBonus(clientCard.getBonus() + transaction.getBonus());
        cardService.save(clientCard);
        FuelTransaction resultTransaction = transactionsRepo.save(transaction);
        log.info("Транзакция " + operation + " успешно обработана :" + resultTransaction.toString());
        return resultTransaction;
    }

    public Float getTotal(Float volume, Float price) {
        return (float) ServiceUtil.round(volume * price, 2);
    }

    public Float getVolume(Float total, Float price) {
        return (float) ServiceUtil.round(total / price, 2);
    }

    public Float getBonusPercent(User creator) {
        try {
            return creator.getAzs().getBonus() / 100;
        }catch (NullPointerException e){
            return 0f;
        }
    }


    public Optional<Client> findClientById(Long id) {
        return clientService.findById(id);
    }

    public ClientCard getClientCardByCardNumber(String cardNumber) {
        return cardService.findByCardNumber(cardNumber);
    }

    public List<Price> getPriceListByCreatorId(Long creatorId) {
        return priceService.findAllByCreatorId(creatorId);
    }

    @Transactional(readOnly = false)
    public FuelTransaction useBonus(Long id, String fuel, String cardNumber, Float bonus, Float price, Boolean nal, User creator, Boolean accumulate) {

        String operation = "Списание бонусов";
        ClientCard card = cardService.findByCardNumber(cardNumber);

        if (nal) {
            log.info(String.format("Запрос на обработку транзакции %s: id = %d, номер карты = %s, %s, бонусов = %f, цена = %f, наличный, %s", operation, id, cardNumber, fuel, bonus, price, creator.toString()));
        } else
            log.info(String.format("Запрос на обработку транзакции %s: id = %d, номер карты = %s, %s, бонусов = %f, цена = %f, безналичный, %s", operation, id, cardNumber, fuel, bonus, price, creator.toString()));

        if (id != null) {
            FuelTransaction updateUseBonusTransaction = transactionsRepo.getOne(id);
            Float rollBackBonus = card.getBonus() + (updateUseBonusTransaction.getBonus() * -1);

            if (rollBackBonus >= bonus) {
                card.setBonus(rollBackBonus);
                updateUseBonusTransaction.setBonus(bonus * -1);


                try {
                    updateUseBonusTransaction.setFuel(FuelUtil.convert(fuel));
                } catch (NumberFormatException e) {
                    log.error("Неизвестный или неверный вид топлива: " + fuel);
                    return null;
                }

                updateUseBonusTransaction.setPrice(price);
                updateUseBonusTransaction.setUpdateDateTime(LocalDateTime.now());
                updateUseBonusTransaction.setVolume(getVolume(bonus, price));
                updateUseBonusTransaction.setTotal(bonus);
                updateUseBonusTransaction.setNal(nal);
                card.setBonus(card.getBonus() - bonus);
                updateUseBonusTransaction.setAccumulate(accumulate);
                cardService.save(card);

                FuelTransaction resultTransaction = transactionsRepo.save(updateUseBonusTransaction);
                log.info("Транзакция " + operation + " успешно обработана :" + resultTransaction.toString());

                return resultTransaction;

            }
            return null;


        } else {

            if (card.getBonus() >= bonus) {
                FuelTransaction useBonusTransaction = new FuelTransaction();
                useBonusTransaction.setCreator(creator);
                useBonusTransaction.setBonus(bonus * -1);
                useBonusTransaction.setCardNumber(cardNumber);
                useBonusTransaction.setCreateDateTime(LocalDateTime.now());
                useBonusTransaction.setUpdateDateTime(LocalDateTime.now());
                useBonusTransaction.setPrice(price);
                useBonusTransaction.setVolume(getVolume(bonus, price));
                useBonusTransaction.setTotal(bonus);
                useBonusTransaction.setAccumulate(accumulate);
                try {
                    useBonusTransaction.setFuel(FuelUtil.convert(fuel));
                } catch (NumberFormatException e) {
                    log.error("Неизвестный или неверный вид топлива: " + fuel);
                    return null;
                }

                useBonusTransaction.setNal(nal);

                card.setBonus(card.getBonus() - bonus);

                cardService.save(card);
                FuelTransaction resultTransaction = transactionsRepo.save(useBonusTransaction);
                log.info("Транзакция " + operation + " успешно обработана :" + resultTransaction.toString());
                return resultTransaction;
            }
            return null;
        }
    }

    @Transactional(readOnly = false)
    public int deleteById(Long id) {

        boolean result = restoreBeforeDelete(id);
        if (result) {
            transactionsRepo.deleteById(id);
            return 0;
        } else return -1;

    }

    @Transactional(readOnly = false)
    public boolean restoreBeforeDelete(Long id) {


        FuelTransaction transaction = transactionsRepo.findById(id).orElse(null);

        if (transaction == null) {
            return false;
        }

        ClientCard clientCard = cardService.findByCardNumber(transaction.getCardNumber());

        float bonus = 0.0f;
        if (transaction.isAccumulate()){
            bonus = clientCard.getBonus() - transaction.getBonus();
        } else {
            bonus = clientCard.getBonus() + (transaction.getBonus()* -1);
        }

        clientCard.setBonus(bonus);
        cardService.save(clientCard);
        log.info("Удаляется транзакция: " + transaction);
        return true;
    }

    public boolean checkPin(String clientCard, String pin) {

        Client client = clientService.findByClientCard(cardService.findByCardNumber(clientCard));

        if (client == null) {
            return false;
        }
        return client.getPin().equals(pin);
    }


    public static Float convertIntToFloat(Integer integer, Integer decimal) {

        float newDecimal = (float) Math.pow(10, decimal);

        return (integer / newDecimal);
    }

    public List<FuelTransaction> findBySettings(Long[] azs, Integer operation, Integer payType, LocalDateTime start, LocalDateTime end) {

        if (start == null){
            start = startDateTime;
        }
        if (end == null){
            end = LocalDateTime.now();
        }

        List<FuelTransaction> transactions = transactionsRepo.findByCreateDateTimeBetween(start, end);

        if (azs != null) {
            List<Long> userIds = azsService.getUsersByAzsIDs(azs);
            transactions = transactions.stream()
                    .filter(fuelTransaction -> {
                        return userIds.contains(fuelTransaction.getCreator().getId());})
                    .collect(Collectors.toList());
        }

        if (payType != null && payType != 100){
            transactions = transactions.stream().filter(fuelTransaction -> {
                return fuelTransaction.isNal() == getType(payType);
            }).collect(Collectors.toList());
        }

        if (operation != null && operation != 100){
            transactions = transactions.stream().filter(fuelTransaction -> {
                return fuelTransaction.isAccumulate() == getType(operation);
            }).collect(Collectors.toList());
        }


        return transactions;
    }

    public List<FuelTransaction> findByClient(String phoneNumber) {

        Client client = clientService.findByPhoneNumber(phoneNumber);

        List<FuelTransaction> result = new ArrayList<>();
        if (client != null) {
            return transactionsRepo.findByCardNumberOrderByIdDesc(client.getClientCard().getCardNumber());
        }
        return result;
    }
}
