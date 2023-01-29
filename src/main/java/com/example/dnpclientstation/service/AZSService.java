package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.AZS;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.repositories.AZSRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AZSService {

    @Autowired
    private AZSRepo azsRepo;

    @Autowired
    private UserService userService;

    public List<AZS> getAll() {
        return azsRepo.findAll();
    }

    public AZS findById(Long id) {
        return azsRepo.findById(id).orElse(new AZS());
    }

    public void createOrUpdate(Long id, String name, String properties, boolean elevatedBonus, Float bonus, Set<String> userSet) {

        AZS azs = new AZS();

        if (id != null) {
            azs = azsRepo.findById(id).orElse(new AZS());
        }


        azs.setAzsName(name);
        azs.setProperties(properties);
        azs.setElevatedBonus(elevatedBonus);
        azs.setBonus(bonus);

        Set<User> users = azs.getUsers();

        if (users == null) {
            users = new HashSet<>();
        }

        for (String strId : userSet) {
            userService.findById(Long.valueOf(strId)).ifPresent(users::add);
        }

        azs.setUsers(users);

        try {
            azsRepo.save(azs);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void createOrUpdate(AZS azs, Set<String> azsIds) {

        createOrUpdate(azs.getAzs_id(), azs.getAzsName(), azs.getProperties(), azs.isElevatedBonus(), azs.getBonus(), azsIds);

    }
}
