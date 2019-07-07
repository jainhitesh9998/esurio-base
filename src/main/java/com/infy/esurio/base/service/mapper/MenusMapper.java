package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.MenusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menus} and its DTO {@link MenusDTO}.
 */
@Mapper(componentModel = "spring", uses = {OutletsMapper.class})
public interface MenusMapper extends EntityMapper<MenusDTO, Menus> {

    @Mapping(source = "outlet.id", target = "outletId")
    @Mapping(source = "outlet.identifier", target = "outletIdentifier")
    MenusDTO toDto(Menus menus);

    @Mapping(source = "outletId", target = "outlet")
    Menus toEntity(MenusDTO menusDTO);

    default Menus fromId(Long id) {
        if (id == null) {
            return null;
        }
        Menus menus = new Menus();
        menus.setId(id);
        return menus;
    }
}
