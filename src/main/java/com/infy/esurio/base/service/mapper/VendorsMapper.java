package com.infy.esurio.base.service.mapper;

import com.infy.esurio.base.domain.*;
import com.infy.esurio.base.service.dto.VendorsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vendors} and its DTO {@link VendorsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VendorsMapper extends EntityMapper<VendorsDTO, Vendors> {



    default Vendors fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vendors vendors = new Vendors();
        vendors.setId(id);
        return vendors;
    }
}
