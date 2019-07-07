package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Items} entity.
 */
public class ItemsDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;

    @Lob
    private byte[] image;

    private String imageContentType;

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

        ItemsDTO itemsDTO = (ItemsDTO) o;
        if (itemsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemsDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", image='" + getImage() + "'" +
            ", vendor=" + getVendorId() +
            ", vendor='" + getVendorIdentifier() + "'" +
            "}";
    }
}
