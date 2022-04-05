package com.example.dnpclientstation.domain;

import javax.persistence.*;

@Entity
@Table(
        name = "client_card"
        //, uniqueConstraints = {@UniqueConstraint(columnNames = {"card_number"})}
)
public class ClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "card_generator")
    @SequenceGenerator(name="card_generator", sequenceName = "card_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private String cardNumber;

    private Float bonus;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public ClientCard() {
    }

    public ClientCard(String cardNumber, Float bonus, Client client) {
        this.cardNumber = cardNumber;
        this.bonus = bonus;
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
