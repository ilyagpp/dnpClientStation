package com.example.dnpclientstation.repositories;

import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionsRepo extends JpaRepository<FuelTransaction, Long> {

    List<FuelTransaction> findByCreatorId(Long id);


    Page<FuelTransaction> findByCreatorIdAndCreateDateTimeBetween(Long id, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<FuelTransaction> findByCreateDateTimeBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<FuelTransaction> findByCreatorIdAndCreateDateTimeBetweenOrderByIdDesc(Long id, LocalDateTime start, LocalDateTime end);

    Page<FuelTransaction> findByCreatorIdAndCreateDateTimeBetweenOrderByIdDesc(Long id, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
