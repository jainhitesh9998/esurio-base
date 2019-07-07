package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.DishesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dishes} and its DTO {@link DishesDTO}.
 */
@Mapper(componentModel = "spring", uses = {MenusMapper.class, ItemsMapper.class})
public interface DishesMapper extends EntityMapper<DishesDTO, Dishes> {

    @Mapping(source = "menu.id", target = "menuId")
    @Mapping(source = "menu.identifier", target = "menuIdentifier")
    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.identifier", target = "itemIdentifier")
    DishesDTO toDto(Dishes dishes);

    @Mapping(source = "menuId", target = "menu")
    @Mapping(source = "itemId", target = "item")
    Dishes toEntity(DishesDTO dishesDTO);

    default Dishes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dishes dishes = new Dishes();
        dishes.setId(id);
        return dishes;
    }
}
