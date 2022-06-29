package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.Fuel;
import com.example.dnpclientstation.domain.Price;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.repositories.PriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepo priceRepo;



    public List<Price> findAllByCreatorId(Long creatorId){
        return priceRepo.findAllByCreatorId(creatorId);
    }

    public boolean saveOrUpdate(Price price, Long creatorId) {

        try {
            Price dbPrice = findByCreatorIdAndFuel(creatorId, price.getFuel());
            if (!dbPrice.isNew()) {
                price.setId(dbPrice.getId());
            }
            priceRepo.save(price);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Price findByCreatorIdAndFuel(Long creatorId, String fuel) {
        return priceRepo.findByCreatorIdAndFuel(creatorId, fuel);
    }

    public List<Price> getSetupList(User creator){
        List<Price> priceList = new ArrayList<>();

            for (Fuel fuel: Fuel.values()){

                Price price = priceRepo.findByCreatorIdAndFuel(creator.getId(), fuel.getFuelType());
                if (price != null){
                    priceList.add(price);
                }else {
                    priceList.add(new Price(null, fuel.getFuelType(), 00.00f, creator));
                }
            }
        return priceList;
    }

    public boolean setUpPriceList(Long[] id, String[] fuel, String[] price, User creator) {
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
