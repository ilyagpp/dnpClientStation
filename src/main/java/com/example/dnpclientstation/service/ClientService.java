package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.Client;
import com.example.dnpclientstation.domain.ClientCard;
import com.example.dnpclientstation.repositories.ClientRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    static final Logger log =
            LoggerFactory.getLogger(ClientService.class);

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
        try {
            Client saveClient = clientRepo.save(client);
            if (client.getId()==null){
                log.info("Добавлен новый клиент: "+saveClient.toString());
            } else log.info("Обновление данных клиента:" +saveClient.toString());

        }catch (Exception e){
            log.info(e.getMessage());
        }

    }

    public Client search(String search) {
        Client client = null;

        client = clientRepo.findByPhoneNumber(search);
        if (client != null) return client;

        client = clientRepo.findByEmail(search);

        return client;
    }

    public List<Client> searchAll(String search){

        final List<Client> clients = new ArrayList<>();

        String[] input = null;
        if (search.contains(" ")){
           input = search.split(" ");
        } else {
            input = new String[1];
            input[0] = search;
        }


        for (String s: input) {
            clientRepo.findAll().forEach(client -> {
                if (client.getPhoneNumber().contains(s)
                        || client.getName().toUpperCase().contains(s.toUpperCase())
                        || client.getSurname().toUpperCase().contains(s.toUpperCase())
                        || client.getClientCard().getCardNumber().contains(s)
                        || client.getEmail().toUpperCase().contains(s.toUpperCase())) {
                    clients.add(client);
                }
            });
        }

        return clients;
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

                log.info("Удаляется клиент: "+ client);
                return true;

            }
        }
        return false;

    }

    public Page<Client> findAll(Pageable pageable) {
        return clientRepo.findAll(pageable);
    }

    public boolean setNewPin(String pin, Client client) {

        if (pin.length() == 4 && client != null && !client.getPin().equals("NO")) {
            client.setPin(pin);
            clientRepo.save(client);
            log.info("Клиенту :" + client +" установлен новый пин");
            return true;
        } else return false;
    }


    public String getPinCode(Long id) {
        assert id != null;
        Client bdClient = clientRepo.findById(id).orElse(new Client());
        return bdClient.getPin();
    }

    public String pinGenerator() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public boolean changePin(String pin, Long id) {

        Optional<Client> client = clientRepo.findById(id);

        if (client.isPresent()) {

            client.get().setPin(pin);
            clientRepo.save(client.get());
            log.info("Запрос на смену pin, для клиента: "+ client);
            return true;
        } else return false;
    }
}
