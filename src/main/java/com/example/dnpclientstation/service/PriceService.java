package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.Fuel;
import com.example.dnpclientstation.domain.Price;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.repositories.PriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceService {

    @Autowired
    private PriceRepo priceRepo;



    public List<Price> findAllByCreatorId(Long creatorId){
        return priceRepo.findAllByCreatorId(creatorId);
    }

    public boolean saveOrUpdate(Price price, User creator) {

        try {
            Price dbPrice = findByCreatorIdAndFuel(creator, price.getFuel());
            if (!dbPrice.isNew()) {
                price.setId(dbPrice.getId());
            }
            priceRepo.save(price);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Price findByCreatorIdAndFuel(User creator, String fuel) {
        return priceRepo.findByCreatorIdAndFuel(creator.getId(), fuel);
    }

    public List<Price> getPriceList(User creator) {

        List<Price> priceList = new ArrayList<>(priceRepo.findAllByCreatorId(creator.getId()));

        List<String> prices = priceList.stream().map(Price::getFuel).collect(Collectors.toList());

        if (priceList.size() != Fuel.values().length) {

            for (Fuel fuel : Fuel.values()) {

                if (!prices.contains(fuel.getFuelType())) {

                    priceList.add(new Price(null, fuel.getFuelType(), 00.00f, creator));

                }

            }
        }

        return priceList;
    }

    public boolean setUpPriceList(User[] id, String[] fuel, String[] price, User creator) {
        try {
            for (int i = 0; i<fuel.length; i++){
                if (price[i] != null && !price[i].equals("")){

                    Float setUpPrice = Float.valueOf(price[i]);
                    Price setPrice;
                    if (id[i]==null){
                        setPrice  = new Price(null, fuel[i], setUpPrice, creator);
                    } else {
                        setPrice = priceRepo.findByCreatorIdAndFuel(creator.getId(), fuel[i]);
                        setPrice.setPrice(setUpPrice);
                    }
                    priceRepo.save(setPrice);
                }

            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
