package com.example.dnpclientstation.repositories;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.ClientCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepo extends JpaRepository<Client, Long> {

    public Client findByEmail(String email);

    public Client findByPhoneNumber(String phoneNumber);

    public List <Client> findBySurname(String surname);

    public List <Client> findByName(String name);

    public Client findByClientCard(ClientCard clientCard);

    public List<Client> findAllByClientCardIsNullOrderById();

    Page<Client> findAll(Pageable pageable);



}
