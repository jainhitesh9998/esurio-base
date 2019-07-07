package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.OutletsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Outlets} and its DTO {@link OutletsDTO}.
 */
@Mapper(componentModel = "spring", uses = {FoodcourtsMapper.class, VendorsMapper.class})
public interface OutletsMapper extends EntityMapper<OutletsDTO, Outlets> {

    @Mapping(source = "foodcourt.id", target = "foodcourtId")
    @Mapping(source = "foodcourt.identifier", target = "foodcourtIdentifier")
    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.identifier", target = "vendorIdentifier")
    OutletsDTO toDto(Outlets outlets);

    @Mapping(source = "foodcourtId", target = "foodcourt")
    @Mapping(source = "vendorId", target = "vendor")
    Outlets toEntity(OutletsDTO outletsDTO);

    default Outlets fromId(Long id) {
        if (id == null) {
            return null;
        }
        Outlets outlets = new Outlets();
        outlets.setId(id);
        return outlets;
    }
}
