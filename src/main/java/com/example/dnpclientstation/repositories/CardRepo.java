package com.example.dnpclientstation.repositories;

import com.example.dnpclientstation.domain.ClientCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepo extends JpaRepository<ClientCard, Long> {
    ClientCard findByCardNumber(String cardNumber);

    List<ClientCard>findAllByOrderById();
    List<ClientCard>findAllByOrderByIdDesc();

    List<ClientCard> findByClientIsNull();
}
