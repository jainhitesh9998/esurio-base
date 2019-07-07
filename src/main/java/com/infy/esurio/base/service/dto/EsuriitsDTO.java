package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Esuriits} entity.
 */
public class EsuriitsDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;


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

        EsuriitsDTO esuriitsDTO = (EsuriitsDTO) o;
        if (esuriitsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), esuriitsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EsuriitsDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", center=" + getCenterId() +
            ", center='" + getCenterIdentifier() + "'" +
            "}";
    }
}
