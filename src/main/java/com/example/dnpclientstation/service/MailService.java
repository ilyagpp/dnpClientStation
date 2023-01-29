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
                            "АЗС ООО «Дорисс-Нефтепродукт»\n"+
                    "для списания накопленных бонусов\n" +
                            "используйте пин-код:\n"+
                    "%s\n\n\n"+
                    "чтобы накопить бонусы просто назовите\n" +
                            "оператору или введите на экране терминала\n" +
                            "ваш номер телефона: %s\n"+
                    "Рады видеть Вас на наших азс!\n\n\n"+
                    "Наши АЗС:\n"+
                            "Чебоксары:️\n"+
                    "АЗС 1  Дорожный проезд,10\n"+
                    "АЗС 5  ул.Богдана Хмельницкого, Центральное ГИБДД\n"+
                    "АЗС 6  ш. Марпосадское,ТЦ«Метро»\n"+
                    "АЗС 7  ул.Калинина ,ТЦ«МегаМолл»\n"+
                    "АЗС 8  пр. Машиностроителей,38/Ленинского комсомола 29 «а»\n"+
                    "АЗС 9  ул.Чернышевского,11 «б»\n"+
                    "АЗС 10 пр. Машиностроителей,39/Ленинского комсомола 29 «б»\n"+
                    "АЗС 11  п.Кугеси, ул. Тепличная,2\n\n"+

                    "Трасса М7:️\n"+
                    "АЗС 182  с. Никольское Ядринский район, 575 км.трасса М7  «Волга»\n"+
                    "АЗС 183  д.Калайкасы Моргаушский район,625 км.трасса М7 «Волга»\n"+
                    "АЗС 185  д.Нюрши Цивильского района, 687 км.трасса М7 «Волга»",

                    client.getSurname() +" "+ client.getName(),
                    client.getPin(),
                    client.getPhoneNumber()
            );

                try {
                    mailSender.send(client.getEmail(), "АЗС «Дорисс-Нефтепродукт», Вы зарегистрированы!", message);
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
                            "чтобы накопить бонусы просто назовите\n" +
                            "оператору или введите на экране терминала\n" +
                            "ваш номер телефона: %s\n"+
                            "Рады видеть Вас на наших азс!",

                    client.getSurname() + " " + client.getName(),
                    client.getPin(),
                    client.getPhoneNumber()
            );

            mailSender.send(client.getEmail(), "АЗС «Дорисс-Нефтепродукт», Ваш Пин-Код", message);
        }
    }
}
