package com.example.dnpclientstation.domain;


import javax.persistence.*;

@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_generator")
    @SequenceGenerator(name="price_generator", sequenceName = "price_sequence", allocationSize = 1, initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "price")
    private Float price;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private User creator;

    public Price() {
    }

    public Price(Long id, String fuel, Float price, User creator) {
        this.id = id;
        this.fuel = fuel;
        this.price = price;
        this.creator = creator;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean isNew(){
        return id == null;
    }

}
