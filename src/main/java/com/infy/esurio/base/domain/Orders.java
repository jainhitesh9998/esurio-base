package com.infy.esurio.base.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Orders.
 */
@Entity
@Table(name = "orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "takeaway")
    private Boolean takeaway;

    @Column(name = "scheduled")
    private ZonedDateTime scheduled;

    @Column(name = "confirmed")
    private Boolean confirmed;

    @Column(name = "delivered")
    private Boolean delivered;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Esuriits esuriit;

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Outlets outlet;

    @ManyToOne
    @JsonIgnoreProperties("orders")
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

    public Orders identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Orders time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Boolean isTakeaway() {
        return takeaway;
    }

    public Orders takeaway(Boolean takeaway) {
        this.takeaway = takeaway;
        return this;
    }

    public void setTakeaway(Boolean takeaway) {
        this.takeaway = takeaway;
    }

    public ZonedDateTime getScheduled() {
        return scheduled;
    }

    public Orders scheduled(ZonedDateTime scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public void setScheduled(ZonedDateTime scheduled) {
        this.scheduled = scheduled;
    }

    public Boolean isConfirmed() {
        return confirmed;
    }

    public Orders confirmed(Boolean confirmed) {
        this.confirmed = confirmed;
        return this;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean isDelivered() {
        return delivered;
    }

    public Orders delivered(Boolean delivered) {
        this.delivered = delivered;
        return this;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Esuriits getEsuriit() {
        return esuriit;
    }

    public Orders esuriit(Esuriits esuriits) {
        this.esuriit = esuriits;
        return this;
    }

    public void setEsuriit(Esuriits esuriits) {
        this.esuriit = esuriits;
    }

    public Outlets getOutlet() {
        return outlet;
    }

    public Orders outlet(Outlets outlets) {
        this.outlet = outlets;
        return this;
    }

    public void setOutlet(Outlets outlets) {
        this.outlet = outlets;
    }

    public Attendants getAttendant() {
        return attendant;
    }

    public Orders attendant(Attendants attendants) {
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
        if (!(o instanceof Orders)) {
            return false;
        }
        return id != null && id.equals(((Orders) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Orders{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", time='" + getTime() + "'" +
            ", takeaway='" + isTakeaway() + "'" +
            ", scheduled='" + getScheduled() + "'" +
            ", confirmed='" + isConfirmed() + "'" +
            ", delivered='" + isDelivered() + "'" +
            "}";
    }
}
