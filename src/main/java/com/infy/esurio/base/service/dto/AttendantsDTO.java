package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Attendants} entity.
 */
public class AttendantsDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;


    private Long outletId;

    private String outletIdentifier;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendantsDTO attendantsDTO = (AttendantsDTO) o;
        if (attendantsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendantsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendantsDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", outlet=" + getOutletId() +
            ", outlet='" + getOutletIdentifier() + "'" +
            "}";
    }
}
