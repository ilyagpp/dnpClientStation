package com.example.dnpclientstation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")


public class Client implements Persistable<java.lang.Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_generator")
    @SequenceGenerator(name="client_generator", sequenceName = "client_sequence", allocationSize = 1, initialValue = 1)
    private java.lang.Long id;


    @NotBlank(message = "Поле не может быть пустым")
    private String name;

    @NotBlank(message = "Поле не может быть пустым")
    private String surname;

    private String patronymic;


    @Enumerated(EnumType.STRING)
    private Sex sex;


    private Date birthday;

    private LocalDateTime added;

    private boolean active;



    @Email(message = "поле должно соответствовать типу: \"aUser@usermail.com\"")
    @NotBlank(message = "Поле не может быть пустым")
    @Column(unique=true)
    private String email;

    @Length(min = 10, max = 10, message = "Длинна строго 10 цифр")
    @Column(unique=true)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "client_card_id")
    private ClientCard clientCard;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonIgnore
    private String pin;



    public User getUser() {
        return user;
    }

    public ClientCard getClientCard() {
        return clientCard;
    }


    @Override
    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return id == null;
    }

    public Client() {
    }

    public Client(java.lang.Long id, String name, String surname, String patronymic, Sex sex, Date birthday, LocalDateTime added, boolean active, String email, String phoneNumber, ClientCard clientCard, User user) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.sex = sex;
        this.birthday = birthday;
        this.added = added;
        this.active = active;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.clientCard = clientCard;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSex() {
        String getSex;
        try {
            getSex = sex.getSex();
        }catch (Exception e){
            getSex = "unknown";
        }

        return getSex;
    }

    public String getPin() {

        return pin != null? pin: "NO";
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setClientCard(ClientCard clientCard) {
        this.clientCard = clientCard;
    }

    public void setUser(User aUser) {
        this.user = aUser;
    }

    @Override
    public String toString() {
        if (user == null);

        StringBuilder sb = new StringBuilder();

        sb.append("Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", added=" + added +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' );

        if (clientCard != null){
            sb.append(", clientCard=" + clientCard.getCardNumber());
        } else sb.append(", clientCard=null");

        if (user!= null) {
            sb.append(", user=" + user.getId());
        }else sb.append(", user=null");

        sb.append(", pin='" + pin + '\'' +
                '}');

        return sb.toString();
    }
}
