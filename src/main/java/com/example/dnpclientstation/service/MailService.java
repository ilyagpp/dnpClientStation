package com.example.dnpclientstation.service;

import com.example.dnpclientstation.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class  MailService {
    @Autowired
    MailSender mailSender;

    public String sendClientRegistrationMessage(Client client) throws MailSendException{
        if(!StringUtils.isEmpty(client.getEmail())){

            String message = String.format(
                    "Уважаемый %s! \n" +
                            "вы были зарегистрированы в качестве\n" +
                            "VIP клиента на сети \n" +
                            "АСЗ ООО «Дорисс-Нефтепродукт»\n"+
                    "для списания накопленных балов\n" +
                            "используйте пин-код: %s\n\n\n"+
                    "чтобы накопить баллы просто назовите\n" +
                            "оператору номер телефона: %s\n"+
                    "Рады видеть Вас на наших азс!",

                    client.getSurname() +" "+ client.getName(),
                    client.getPin(),
                    client.getPhoneNumber()
            );

                try {
                    mailSender.send(client.getEmail(), "АЗС ДОРИСС, Вы зарегистрированы!", message);
                }catch (MailSendException e){
                 return "Fail";
                }
        }
        return "Ok";
    }

    public void recallPin (Client client) {
        if (!StringUtils.isEmpty(client.getEmail())) {

            String message = String.format(
                    "Уважаемый %s! \n" +

                            "напоминаем, Ваш пин-код: %s\n\n\n" +
                            "чтобы накопить баллы просто назовите \n" +
                            "оператору номер телефона: %s\n" +
                            "Рады видеть Вас на наших азс!",

                    client.getSurname() + " " + client.getName(),
                    client.getPin(),
                    client.getPhoneNumber()
            );

            mailSender.send(client.getEmail(), "АЗС ДОРИСС, Ваш Пин-Код", message);
        }
    }
}
