package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Categories} entity.
 */
public class CategoriesDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;


    private Long itemId;

    private String itemIdentifier;

    private Long tagId;

    private String tagIdentifier;

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemsId) {
        this.itemId = itemsId;
    }

    public String getItemIdentifier() {
        return itemIdentifier;
    }

    public void setItemIdentifier(String itemsIdentifier) {
        this.itemIdentifier = itemsIdentifier;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagsId) {
        this.tagId = tagsId;
    }

    public String getTagIdentifier() {
        return tagIdentifier;
    }

    public void setTagIdentifier(String tagsIdentifier) {
        this.tagIdentifier = tagsIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoriesDTO categoriesDTO = (CategoriesDTO) o;
        if (categoriesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoriesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoriesDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", item=" + getItemId() +
            ", item='" + getItemIdentifier() + "'" +
            ", tag=" + getTagId() +
            ", tag='" + getTagIdentifier() + "'" +
            "}";
    }
}
