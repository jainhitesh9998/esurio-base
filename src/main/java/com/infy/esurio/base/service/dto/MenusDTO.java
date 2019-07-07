package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Menus} entity.
 */
public class MenusDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;

    private Boolean active;


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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

        MenusDTO menusDTO = (MenusDTO) o;
        if (menusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenusDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", active='" + isActive() + "'" +
            ", outlet=" + getOutletId() +
            ", outlet='" + getOutletIdentifier() + "'" +
            "}";
    }
}
