package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Outlets} entity.
 */
public class OutletsDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;


    private Long foodcourtId;

    private String foodcourtIdentifier;

    private Long vendorId;

    private String vendorIdentifier;

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

    public Long getFoodcourtId() {
        return foodcourtId;
    }

    public void setFoodcourtId(Long foodcourtsId) {
        this.foodcourtId = foodcourtsId;
    }

    public String getFoodcourtIdentifier() {
        return foodcourtIdentifier;
    }

    public void setFoodcourtIdentifier(String foodcourtsIdentifier) {
        this.foodcourtIdentifier = foodcourtsIdentifier;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorsId) {
        this.vendorId = vendorsId;
    }

    public String getVendorIdentifier() {
        return vendorIdentifier;
    }

    public void setVendorIdentifier(String vendorsIdentifier) {
        this.vendorIdentifier = vendorsIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OutletsDTO outletsDTO = (OutletsDTO) o;
        if (outletsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), outletsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OutletsDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", foodcourt=" + getFoodcourtId() +
            ", foodcourt='" + getFoodcourtIdentifier() + "'" +
            ", vendor=" + getVendorId() +
            ", vendor='" + getVendorIdentifier() + "'" +
            "}";
    }
}
