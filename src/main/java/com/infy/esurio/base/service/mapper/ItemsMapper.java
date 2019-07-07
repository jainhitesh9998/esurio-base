package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.ItemsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Items} and its DTO {@link ItemsDTO}.
 */
@Mapper(componentModel = "spring", uses = {VendorsMapper.class})
public interface ItemsMapper extends EntityMapper<ItemsDTO, Items> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.identifier", target = "vendorIdentifier")
    ItemsDTO toDto(Items items);

    @Mapping(source = "vendorId", target = "vendor")
    Items toEntity(ItemsDTO itemsDTO);

    default Items fromId(Long id) {
        if (id == null) {
            return null;
        }
        Items items = new Items();
        items.setId(id);
        return items;
    }
}
