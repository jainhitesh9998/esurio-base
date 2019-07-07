package com.infy.esurio.base.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Orders} entity.
 */
public class OrdersDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;

    private ZonedDateTime time;

    private Boolean takeaway;

    private ZonedDateTime scheduled;

    private Boolean confirmed;

    private Boolean delivered;


    private Long esuriitId;

    private String esuriitIdentifier;

    private Long outletId;

    private String outletIdentifier;

    private Long attendantId;

    private String attendantIdentifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Boolean isTakeaway() {
        return takeaway;
    }

    public void setTakeaway(Boolean takeaway) {
        this.takeaway = takeaway;
    }

    public ZonedDateTime getScheduled() {
        return scheduled;
    }

    public void setScheduled(ZonedDateTime scheduled) {
        this.scheduled = scheduled;
    }

    public Boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Long getEsuriitId() {
        return esuriitId;
    }

    public void setEsuriitId(Long esuriitsId) {
        this.esuriitId = esuriitsId;
    }

    public String getEsuriitIdentifier() {
        return esuriitIdentifier;
    }

    public void setEsuriitIdentifier(String esuriitsIdentifier) {
        this.esuriitIdentifier = esuriitsIdentifier;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletsId) {
        this.outletId = outletsId;
    }

    public String getOutletIdentifier() {
        return outletIdentifier;
    }

    public void setOutletIdentifier(String outletsIdentifier) {
        this.outletIdentifier = outletsIdentifier;
    }

    public Long getAttendantId() {
        return attendantId;
    }

    public void setAttendantId(Long attendantsId) {
        this.attendantId = attendantsId;
    }

    public String getAttendantIdentifier() {
        return attendantIdentifier;
    }

    public void setAttendantIdentifier(String attendantsIdentifier) {
        this.attendantIdentifier = attendantsIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdersDTO ordersDTO = (OrdersDTO) o;
        if (ordersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ordersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrdersDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", time='" + getTime() + "'" +
            ", takeaway='" + isTakeaway() + "'" +
            ", scheduled='" + getScheduled() + "'" +
            ", confirmed='" + isConfirmed() + "'" +
            ", delivered='" + isDelivered() + "'" +
            ", esuriit=" + getEsuriitId() +
            ", esuriit='" + getEsuriitIdentifier() + "'" +
            ", outlet=" + getOutletId() +
            ", outlet='" + getOutletIdentifier() + "'" +
            ", attendant=" + getAttendantId() +
            ", attendant='" + getAttendantIdentifier() + "'" +
            "}";
    }
}
