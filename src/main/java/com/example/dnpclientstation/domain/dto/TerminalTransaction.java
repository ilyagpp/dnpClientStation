package com.example.dnpclientstation.domain.dto;

import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TerminalTransaction extends FuelTransaction {


    private Boolean pinStatus;


    public TerminalTransaction() {

    }

    public TerminalTransaction(Long id, String fuel, float price, float volume, float total, User creator, Boolean pinStatus) {
        super(id, fuel, price, volume, total, creator);
        this.pinStatus = pinStatus;
    }

    public TerminalTransaction(FuelTransaction fuelTransaction, Boolean pinStatus){
        super(fuelTransaction.getId(), fuelTransaction.getCreateDateTime(), fuelTransaction.getUpdateDateTime() ,fuelTransaction.getFuel(), fuelTransaction.getPrice(),
                fuelTransaction.getVolume(), fuelTransaction.getTotal(), fuelTransaction.getCardNumber(),
                fuelTransaction.getBonus(), fuelTransaction.getCreator());
        this.pinStatus = pinStatus;
    }

    public Boolean getPinStatus() {
        return pinStatus;
    }

    public void setPinStatus(Boolean pinStatus) {
        this.pinStatus = pinStatus;
    }


    @Override
    @JsonIgnore
    public boolean isNew() {
        return super.isNew();
    }
}