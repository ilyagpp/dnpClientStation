package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.Role;
import com.example.dnpclientstation.domain.User;
import com.example.dnpclientstation.repositories.CardRepo;
import com.example.dnpclientstation.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Value("${rootURL}")
    private String rootUrl;

    @Value("${rootPort}")
    private String rootPort;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CardRepo cardRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }

        return user;
    }

    public int addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return -1;
        }

        userFromDb = userRepo.findByEmail(user.getEmail());

        if (userFromDb != null) {
            return -2;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendRegistrationMessage(user);

        return 0;
    }

    private void sendRegistrationMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Поздравлем, %s! \n" +
                            "Теперь вы часть Бонусной системы «АЗС ДОРИСС», для активации вашего профиля необходимо перейти по ссылке:" +
                            "http://%s/activate/%s",
                    user.getUsername(),
                    url(rootUrl, rootPort),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }


    static String url(String rootUrl, String rootPort) {
        if (rootPort == null) return rootUrl;

        return rootPort.isEmpty() ? rootUrl : rootUrl + ":" + rootPort;

    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }


    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }


    public List<User> findAllOrderById(){
        return userRepo.findAllByOrderById();
    }

    public void saveUser(User user, String username, String azsName,Map<String, String> form, Long azsId, Boolean clrAzs) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        if (azsName != null)
            user.setAzsName(azsName);

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        if (clrAzs) {

            clearAzs(user, azsId);
        }

        userRepo.save(user);
    }




    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

        userRepo.save(user);
        if (isEmailChanged) {
            sendRegistrationMessage(user);
        }
    }

    public void clearAzs(User user, Long azsId) {

       boolean low = user.getAzs().getAzs_id().equals(azsId);

       if (!low){

           throw new IllegalArgumentException("Отвязать невозможно! Обратитесь к администратору");

       } else {
           user.setAzs(null);
       }
    }
}
