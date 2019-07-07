package com.infy.esurio.base.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.esurio.base.domain.Dishes} entity.
 */
public class DishesDTO implements Serializable {

    private Long id;

    @NotNull
    private String identifier;

    private Boolean takeaway;

    private Integer servings;


    private Long menuId;

    private String menuIdentifier;

    private Long itemId;

    private String itemIdentifier;

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

    public Boolean isTakeaway() {
        return takeaway;
    }

    public void setTakeaway(Boolean takeaway) {
        this.takeaway = takeaway;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menusId) {
        this.menuId = menusId;
    }

    public String getMenuIdentifier() {
        return menuIdentifier;
    }

    public void setMenuIdentifier(String menusIdentifier) {
        this.menuIdentifier = menusIdentifier;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DishesDTO dishesDTO = (DishesDTO) o;
        if (dishesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dishesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DishesDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", takeaway='" + isTakeaway() + "'" +
            ", servings=" + getServings() +
            ", menu=" + getMenuId() +
            ", menu='" + getMenuIdentifier() + "'" +
            ", item=" + getItemId() +
            ", item='" + getItemIdentifier() + "'" +
            "}";
    }
}
