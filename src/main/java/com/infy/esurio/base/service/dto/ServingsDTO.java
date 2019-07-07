package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Servings} entity.
 */
public class ServingsDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;

    private Boolean prepared;

    private Boolean served;

    private Integer quantity;


    private Long orderId;

    private String orderIdentifier;

    private Long dishId;

    private String dishIdentifier;

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

    public Boolean isPrepared() {
        return prepared;
    }

    public void setPrepared(Boolean prepared) {
        this.prepared = prepared;
    }

    public Boolean isServed() {
        return served;
    }

    public void setServed(Boolean served) {
        this.served = served;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long ordersId) {
        this.orderId = ordersId;
    }

    public String getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(String ordersIdentifier) {
        this.orderIdentifier = ordersIdentifier;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishesId) {
        this.dishId = dishesId;
    }

    public String getDishIdentifier() {
        return dishIdentifier;
    }

    public void setDishIdentifier(String dishesIdentifier) {
        this.dishIdentifier = dishesIdentifier;
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

        ServingsDTO servingsDTO = (ServingsDTO) o;
        if (servingsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), servingsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServingsDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", prepared='" + isPrepared() + "'" +
            ", served='" + isServed() + "'" +
            ", quantity=" + getQuantity() +
            ", order=" + getOrderId() +
            ", order='" + getOrderIdentifier() + "'" +
            ", dish=" + getDishId() +
            ", dish='" + getDishIdentifier() + "'" +
            ", attendant=" + getAttendantId() +
            ", attendant='" + getAttendantIdentifier() + "'" +
            "}";
    }
}
