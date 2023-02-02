package com.example.dnpclientstation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class AZS {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "azs_generator")
    @SequenceGenerator(name = "azs_generator", sequenceName = "azs_sequence", allocationSize = 1)
    @Column(name = "azs_id", nullable = false)
    private Long azs_id;

    @Column(name = "name")
    @NotBlank(message = "поле не может быть пустым")
    @Length(max = 50, message = "длинна больше 50 символов")
    private String azsName;

    @Length(max = 255, message = "длинна больше 255 символов")
    private String properties;

    private boolean elevatedBonus;

    @NotNull
    private Float bonus;

    @OneToMany
    @JoinColumn(name = "azs_id")
    @JsonIgnore
    private Set<User> users;

    public Long getAzs_id() {
        return azs_id;
    }

    public void setAzs_id(Long azs_id) {
        this.azs_id = azs_id;
    }


    public String getProperties() {
        return properties != null? properties: "";
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public boolean isElevatedBonus() {
        return elevatedBonus;
    }

    public void setElevatedBonus(boolean elevatedBonus) {
        this.elevatedBonus = elevatedBonus;
    }

    public Float getBonus() {
        return bonus;
    }

    public void setBonus(Float bonus) {
        this.bonus = bonus;
    }

    public String getAzsName() {
        return azsName != null? azsName : "";
    }

    public void setAzsName(String azsName) {
        this.azsName = azsName;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public boolean isNew() {
        return azs_id == null;
    }
}
