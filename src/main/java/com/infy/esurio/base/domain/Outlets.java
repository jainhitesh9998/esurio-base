package com.infy.esurio.base.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Outlets.
 */
@Entity
@Table(name = "outlets")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Outlets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @ManyToOne
    @JsonIgnoreProperties("outlets")
    private Foodcourts foodcourt;

    @ManyToOne
    @JsonIgnoreProperties("outlets")
    private Vendors vendor;

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

    public Outlets identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Foodcourts getFoodcourt() {
        return foodcourt;
    }

    public Outlets foodcourt(Foodcourts foodcourts) {
        this.foodcourt = foodcourts;
        return this;
    }

    public void setFoodcourt(Foodcourts foodcourts) {
        this.foodcourt = foodcourts;
    }

    public Vendors getVendor() {
        return vendor;
    }

    public Outlets vendor(Vendors vendors) {
        this.vendor = vendors;
        return this;
    }

    public void setVendor(Vendors vendors) {
        this.vendor = vendors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Outlets)) {
            return false;
        }
        return id != null && id.equals(((Outlets) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Outlets{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
