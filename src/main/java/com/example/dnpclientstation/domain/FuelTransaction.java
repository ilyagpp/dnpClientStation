package com.example.dnpclientstation.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table (name = "fuel_transaction")
public class FuelTransaction implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trans_generator")
    @SequenceGenerator(name="trans_generator", sequenceName = "trans_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @NotNull
    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

    @NotBlank
    private String fuel;

    @NotNull
    private float price;

    @NotNull
    private float volume;

    private float total;

    @NotBlank
    @Length(max = 13)
    @Column(name = "client_card")
    private String cardNumber;

    private Float bonus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    @JsonIgnoreProperties({"password", "active", "email", "activationCode", "roles", "enabled", "admin", "azsAdmin",
            "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
    private User creator;

    private Boolean accumulate;

    private Boolean nal;


    public String getCardNumber() {
        return cardNumber;
    }


    public FuelTransaction() {
    }

    public FuelTransaction(Long id, String fuel, float price, float volume, float total, boolean nal, User creator, boolean accumulate) {
        this.id = id;
        this.createDateTime = LocalDateTime.now();
        this.updateDateTime = LocalDateTime.now();
        this.fuel = fuel;
        this.price = price;
        this.volume = volume;
        this.total = total;
        this.nal = nal;
        this.creator = creator;
        this.accumulate = accumulate;
    }


    public FuelTransaction(Long id, LocalDateTime createDateTime, LocalDateTime updateDateTime, String fuel, float price, float volume, float total, String cardNumber, Float bonus, boolean nal, User creator, boolean accumulate) {
        this.id = id;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.fuel = fuel;
        this.price = price;
        this.volume = volume;
        this.total = total;
        this.cardNumber = cardNumber;
        this.bonus = bonus;
        this.nal = nal;
        this.creator = creator;
        this.accumulate = accumulate;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setCardNumber(String clientCard) {
        this.cardNumber = clientCard;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }


    public Boolean isNal() {
        if (nal == null){
            return false;
        }
        return nal;
    }

    public void setNal(boolean nal) {
        this.nal = nal;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
    public Boolean isAccumulate() {
        return accumulate;
    }

    public void setAccumulate(Boolean accumulate) {
        this.accumulate = accumulate;
    }

    public String operationType(){
        if (accumulate) {return "Накопление";}
        else return "списание";

    }

    @Override
    public String toString() {
        return "FuelTransaction{" +
                "id=" + id +
                ", createDateTime=" + createDateTime.toLocalDate() + " " + createDateTime.toLocalTime()+
                ", updateDateTime=" + updateDateTime.toLocalDate() + " " + updateDateTime.toLocalTime()+
                ", тип операции ="+ operationType() +
                ", fuel='" + fuel + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", total=" + total +
                ", cardNumber='" + cardNumber + '\'' +
                ", bonus=" + bonus +
                ", "+creator.toString() +
                ", nal=" + nal +
                '}';
    }
}
