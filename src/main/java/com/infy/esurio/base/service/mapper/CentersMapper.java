package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.CentersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Centers} and its DTO {@link CentersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CentersMapper extends EntityMapper<CentersDTO, Centers> {



    default Centers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Centers centers = new Centers();
        centers.setId(id);
        return centers;
    }
}
