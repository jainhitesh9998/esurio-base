package com.infy.esurio.base.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Servings.
 */
@Entity
@Table(name = "servings")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Servings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "prepared")
    private Boolean prepared;

    @Column(name = "served")
    private Boolean served;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JsonIgnoreProperties("servings")
    private Orders order;

    @ManyToOne
    @JsonIgnoreProperties("servings")
    private Dishes dish;

    @ManyToOne
    @JsonIgnoreProperties("servings")
    private Attendants attendant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Servings identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean isPrepared() {
        return prepared;
    }

    public Servings prepared(Boolean prepared) {
        this.prepared = prepared;
        return this;
    }

    public void setPrepared(Boolean prepared) {
        this.prepared = prepared;
    }

    public Boolean isServed() {
        return served;
    }

    public Servings served(Boolean served) {
        this.served = served;
        return this;
    }

    public void setServed(Boolean served) {
        this.served = served;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Servings quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Orders getOrder() {
        return order;
    }

    public Servings order(Orders orders) {
        this.order = orders;
        return this;
    }

    public void setOrder(Orders orders) {
        this.order = orders;
    }

    public Dishes getDish() {
        return dish;
    }

    public Servings dish(Dishes dishes) {
        this.dish = dishes;
        return this;
    }

    public void setDish(Dishes dishes) {
        this.dish = dishes;
    }

    public Attendants getAttendant() {
        return attendant;
    }

    public Servings attendant(Attendants attendants) {
        this.attendant = attendants;
        return this;
    }

    public void setAttendant(Attendants attendants) {
        this.attendant = attendants;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Servings)) {
            return false;
        }
        return id != null && id.equals(((Servings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Servings{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", prepared='" + isPrepared() + "'" +
            ", served='" + isServed() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
