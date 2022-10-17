package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.ClientCard;
import com.example.dnpclientstation.repositories.ClientRepo;
import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    CardService cardService;

    public Optional<Client> findById(Long id) {
        return clientRepo.findById(id);
    }


    public Client findByPhoneNumber(String phoneNumber) {

        return clientRepo.findByPhoneNumber(phoneNumber);
    }

    public Client findByEmail(String email) {

        return clientRepo.findByEmail(email);
    }

    public Client findByClientCard(ClientCard clientCard) {

        return clientCard != null ? clientRepo.findByClientCard(clientCard) : null;

    }
    public Client searchClientByCardNameEmailPhone(String search) {

        Client client = search(search);
        if (client != null) return client;

        client = findByClientCard(cardService.findByCardNumber(search));
        return client;
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

        return client;
    }

    public boolean checkEmail(String email) {
        return clientRepo.findByEmail(email) == null;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return clientRepo.findByPhoneNumber(phoneNumber) == null;
    }

    public boolean deleteById(Long id) {

        Client client = findById(id).orElse(null);

        if (client != null) {

            if (client.getClientCard() != null) {

                return false;

            } else {

                clientRepo.deleteById(id);
                return true;

            }
        }

        return false;


    }

    public Page<Client> findAll(Pageable pageable) {
        return   clientRepo.findAll(pageable);
    }

    public boolean setNewPin(String pin, Client client){

        if (pin.length() == 4 && client != null && !client.getPin().equals("NO") ) {
            client.setPin(pin);
            clientRepo.save(client);
            return true;
        } else return false;
    }



    public String getPinCode(Long id){
        assert id != null;
        Client bdClient = clientRepo.findById(id).orElse(new Client());
        return bdClient.getPin();
    }

    public String pinGenerator(){
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public boolean changePin(String pin, Long id) {

        Optional<Client> client = clientRepo.findById(id);
        if (client.isPresent()){

            client.get().setPin(pin);
            clientRepo.save(client.get());
            return true;
        } else  return  false;

    }
}
