package com.example.dnpclientstation.repositories;

import com.example.dnpclientstation.domain.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepo extends CrudRepository<Price, Long> {


    List<Price> findAllByCreatorId(Long creatorId);

    Price findByCreatorIdAndFuel(Long id, String fuelType);

}