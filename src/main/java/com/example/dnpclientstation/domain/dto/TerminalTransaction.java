package com.example.dnpclientstation.domain.dto;

import com.example.dnpclientstation.domain.FuelTransaction;
import com.example.dnpclientstation.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TerminalTransaction extends FuelTransaction {


    @JsonProperty("volume")
    private Integer volumeT;

    @JsonProperty("price")
    private Integer priceT;

    @JsonProperty("total")
    private Integer totalT;

    private Boolean pinStatus;


    public TerminalTransaction() {

    }

    public Integer getVolumeT() {
        return volumeT;
    }

    public void setVolumeT(Integer volumeT) {
        this.volumeT = volumeT;
    }

    public Integer getPriceT() {
        return priceT;
    }

    public void setPriceT(Integer priceT) {
        this.priceT = priceT;
    }

    public Integer getTotalT() {
        return totalT;
    }

    public void setTotalT(Integer totalT) {
        this.totalT = totalT;
    }

    public TerminalTransaction(Long id, String fuel, float price, float volume, float total, boolean nal, User creator, Boolean pinStatus) {
        super(id, fuel, price, volume, total, nal, creator);

        this.volumeT = (int)(volume * 1000);
        this.totalT = (int) (total * 100);
        this.priceT = (int) (price * 100);
        this.pinStatus = pinStatus;
    }


    public TerminalTransaction(FuelTransaction fuelTransaction, Boolean pinStatus){
        super(fuelTransaction.getId(), fuelTransaction.getCreateDateTime(), fuelTransaction.getUpdateDateTime() ,fuelTransaction.getFuel(), fuelTransaction.getPrice(),
                fuelTransaction.getVolume(), fuelTransaction.getTotal(), fuelTransaction.getCardNumber(),
                fuelTransaction.getBonus(), fuelTransaction.isNal(), fuelTransaction.getCreator());

        this.volumeT = (int) (fuelTransaction.getVolume() * 1000);
        this.totalT = (int) (fuelTransaction.getTotal() * 100);
        this.priceT = (int) (fuelTransaction.getPrice() * 100);

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
    public float getPrice() {
        return super.getPrice();
    }

    @Override
    @JsonIgnore
    public float getVolume() {
        return super.getVolume();
    }

    @Override
    @JsonIgnore
    public float getTotal() {
        return super.getTotal();
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return super.isNew();
    }
}
