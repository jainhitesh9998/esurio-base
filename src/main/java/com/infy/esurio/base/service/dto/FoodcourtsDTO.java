package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Foodcourts} entity.
 */
public class FoodcourtsDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long centerId;

    private String centerIdentifier;

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centersId) {
        this.centerId = centersId;
    }

    public String getCenterIdentifier() {
        return centerIdentifier;
    }

    public void setCenterIdentifier(String centersIdentifier) {
        this.centerIdentifier = centersIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FoodcourtsDTO foodcourtsDTO = (FoodcourtsDTO) o;
        if (foodcourtsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), foodcourtsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FoodcourtsDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", image='" + getImage() + "'" +
            ", center=" + getCenterId() +
            ", center='" + getCenterIdentifier() + "'" +
            "}";
    }
}
