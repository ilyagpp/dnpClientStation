package com.example.dnpclientstation.domain;


import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
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
    private String clientCard;

    private Float bonus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    public String getClientCard() {
        return clientCard;
    }


    public FuelTransaction() {
    }

    public FuelTransaction(Long id, String fuel, float price, float volume, float total, User creator) {
        this.id = id;
        this.createDateTime = LocalDateTime.now();
        this.updateDateTime = LocalDateTime.now();
        this.fuel = fuel;
        this.price = price;
        this.volume = volume;
        this.total = total;
        this.creator = creator;
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

    public void setClientCard(String clientCard) {
        this.clientCard = clientCard;
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

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FuelTransaction)) return false;

        FuelTransaction that = (FuelTransaction) o;

        if (Float.compare(that.getPrice(), getPrice()) != 0) return false;
        if (Float.compare(that.getVolume(), getVolume()) != 0) return false;
        if (Float.compare(that.getTotal(), getTotal()) != 0) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (!getCreateDateTime().equals(that.getCreateDateTime())) return false;
        if (!getUpdateDateTime().equals(that.getUpdateDateTime())) return false;
        if (!getFuel().equals(that.getFuel())) return false;
        if (!getClientCard().equals(that.getClientCard())) return false;
        if (!getBonus().equals(that.getBonus())) return false;
        return getCreator().equals(that.getCreator());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getCreateDateTime().hashCode();
        result = 31 * result + getUpdateDateTime().hashCode();
        result = 31 * result + getFuel().hashCode();
        result = 31 * result + (getPrice() != +0.0f ? Float.floatToIntBits(getPrice()) : 0);
        result = 31 * result + (getVolume() != +0.0f ? Float.floatToIntBits(getVolume()) : 0);
        result = 31 * result + (getTotal() != +0.0f ? Float.floatToIntBits(getTotal()) : 0);
        result = 31 * result + getClientCard().hashCode();
        result = 31 * result + getBonus().hashCode();
        result = 31 * result + getCreator().hashCode();
        return result;
    }
}
