package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.ClientCard;
import com.example.dnpclientstation.repositories.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepo clientRepo;

    public Optional<Client> findById(Long id){
       return clientRepo.findById(id);
    }


    public Client findByPhoneNumber(String phoneNumber) {

        return clientRepo.findByPhoneNumber(phoneNumber);
    }

    public Client findByEmail(String email) {

        return clientRepo.findByEmail(email);
    }

    public Client findByClientCard(ClientCard clientCard){
        return clientRepo.findByClientCard(clientCard);
    }

    public List<Client> findAll(){
        return clientRepo.findAll();
    }

    public List<Client> findAllByClientCardIsNullOrderById() {
        return clientRepo.findAllByClientCardIsNullOrderById();
    }



    public void save(Client client) {
        clientRepo.save(client);
    }

    public Client search(String search) {

        Client client = null;

        client = clientRepo.findByPhoneNumber(search);
        if (client != null) return client;

        client = clientRepo.findByEmail(search);
        if (client != null) return client;

        client = clientRepo.findBySurname(search);
        if (client != null) return client;

        client = clientRepo.findByName(search);

        return client;
    }
}
