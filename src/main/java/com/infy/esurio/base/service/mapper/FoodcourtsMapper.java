package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.FoodcourtsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Foodcourts} and its DTO {@link FoodcourtsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CentersMapper.class})
public interface FoodcourtsMapper extends EntityMapper<FoodcourtsDTO, Foodcourts> {

    @Mapping(source = "center.id", target = "centerId")
    @Mapping(source = "center.identifier", target = "centerIdentifier")
    FoodcourtsDTO toDto(Foodcourts foodcourts);

    @Mapping(source = "centerId", target = "center")
    Foodcourts toEntity(FoodcourtsDTO foodcourtsDTO);

    default Foodcourts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Foodcourts foodcourts = new Foodcourts();
        foodcourts.setId(id);
        return foodcourts;
    }
}
