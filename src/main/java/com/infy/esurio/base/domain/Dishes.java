package com.infy.esurio.base.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Dishes.
 */
@Entity
@Table(name = "dishes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dishes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "takeaway")
    private Boolean takeaway;

    @Column(name = "servings")
    private Integer servings;

    @ManyToOne
    @JsonIgnoreProperties("dishes")
    private Menus menu;

    @ManyToOne
    @JsonIgnoreProperties("dishes")
    private Items item;

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

    public Dishes identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean isTakeaway() {
        return takeaway;
    }

    public Dishes takeaway(Boolean takeaway) {
        this.takeaway = takeaway;
        return this;
    }

    public void setTakeaway(Boolean takeaway) {
        this.takeaway = takeaway;
    }

    public Integer getServings() {
        return servings;
    }

    public Dishes servings(Integer servings) {
        this.servings = servings;
        return this;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public Menus getMenu() {
        return menu;
    }

    public Dishes menu(Menus menus) {
        this.menu = menus;
        return this;
    }

    public void setMenu(Menus menus) {
        this.menu = menus;
    }

    public Items getItem() {
        return item;
    }

    public Dishes item(Items items) {
        this.item = items;
        return this;
    }

    public void setItem(Items items) {
        this.item = items;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dishes)) {
            return false;
        }
        return id != null && id.equals(((Dishes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dishes{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", takeaway='" + isTakeaway() + "'" +
            ", servings=" + getServings() +
            "}";
    }
}
